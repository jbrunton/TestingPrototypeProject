package com.zipcar.testingprototype;

import android.accounts.Account;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.zipcar.testingprototype.data.DataErrorEvent;
import com.zipcar.testingprototype.shared.MessageBus;

import java.util.Collection;

public class
        ListFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        Toast.makeText(getActivity(), "This is the list of accounts . . .", Toast.LENGTH_LONG).show();
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
    public void onAccountsAvailable(Collection<Account> accounts) {
        // Toast.makeText(getActivity(), event.success() ? "we got an accounts list" : "we didn't get an accounts list", Toast.LENGTH_LONG).show();
    }

    @Subscribe
    public void onAccountsError(DataErrorEvent<Collection<Account>> error) {
        if (error.getServerReason() != null) {
            Toast.makeText(getActivity(), error.getServerReason(), Toast.LENGTH_LONG).show();
        } else if (error.getHttpErrorCode() > 0) {
            Toast.makeText(getActivity(), "There was an error contacting the server: " + error.getHttpErrorCode(), Toast.LENGTH_LONG).show();
        }

        if (error.getCachedData() != null) {
            onAccountsAvailable(error.getCachedData());
        }
    }

}