package com.zipcar.testingprototype;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.zipcar.testingprototype.auth.AuthResponseEvent;
import com.zipcar.testingprototype.auth.AuthenticateEvent;
import com.zipcar.testingprototype.shared.MessageBus;

public class LoginFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        final EditText username = (EditText) view.findViewById(R.id.username);
        final EditText password = (EditText) view.findViewById(R.id.password);
        Button loginButton = (Button) view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthenticateEvent event = new AuthenticateEvent.Builder()
                        .setUserName(username.getText().toString())
                        .setPassword(password.getText().toString())
                        .getInstance();
                MessageBus.get().post(event);
            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onPause() {
        super.onPause();
        MessageBus.get().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MessageBus.get().register(this);
    }

    @Subscribe
    public void onAuthResponse (AuthResponseEvent event) {
        Toast.makeText(getActivity(), event.success() ? "successful login" : "unsuccessful login attempt", Toast.LENGTH_LONG).show();
    }

}
