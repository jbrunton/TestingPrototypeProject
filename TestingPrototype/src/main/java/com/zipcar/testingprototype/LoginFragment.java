package com.zipcar.testingprototype;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.zipcar.testingprototype.auth.AuthenticateEvent;
import com.zipcar.testingprototype.data.DataErrorEvent;
import com.zipcar.testingprototype.models.Session;
import com.zipcar.testingprototype.shared.BaseFragment;
import com.zipcar.testingprototype.shared.BaseListFragment;

import javax.inject.Inject;

public class LoginFragment extends BaseFragment {

    private EditText username;
    private EditText password;

    @Inject public Bus bus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        Button loginButton = (Button) view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
        return view;
    }

    public void submit () {
        AuthenticateEvent event = new AuthenticateEvent.Builder()
                .setUserName(username.getText().toString())
                .setPassword(password.getText().toString())
                .getInstance();
        bus.post(event);
    }

    @Override
    public void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Subscribe
    public void onSessionAvailable (Session session) {
        startActivity(new Intent(getActivity(), ListActivity.class));
        getActivity().finish();
    }

    @Subscribe
    public void onSessionError(DataErrorEvent<Session> error) {
        if (error.getServerReason() != null) {
            Toast.makeText(getActivity(), error.getServerReason(), Toast.LENGTH_LONG).show();
        } else if (error.getHttpErrorCode() > 0) {
            Toast.makeText(getActivity(), "There was an error contacting the server: " + error.getHttpErrorCode(), Toast.LENGTH_LONG).show();
        } else {
            throw new RuntimeException("Invalid HTTP error code");
        }
    }

}
