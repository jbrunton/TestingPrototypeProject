package com.zipcar.testingprototype;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.zipcar.testingprototype.accounts.RefreshAccountsEvent;
import com.zipcar.testingprototype.data.DataAvailableEvent;
import com.zipcar.testingprototype.data.DataErrorEvent;
import com.zipcar.testingprototype.models.Account;
import com.zipcar.testingprototype.shared.MessageBus;

import java.util.Collection;

public class AccountsListFragment extends android.support.v4.app.ListFragment{

    private ArrayAdapter<Account> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.adapter = new ArrayAdapter<Account>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1);
        setListAdapter(this.adapter);
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
        MessageBus.get().post(new RefreshAccountsEvent());
    }

    protected void updateAccountsList(Collection<Account> accounts) {
        this.adapter.clear();
        for (Account acc : accounts) {
            this.adapter.add(acc);
        }
    }

    @Subscribe
    public void onAccountsAvailable(DataAvailableEvent<Collection<Account>> event) {
        Toast.makeText(getActivity(), "This is the list of accounts . . .", Toast.LENGTH_LONG).show();
        updateAccountsList(event.getData());
    }

    @Subscribe
    public void onAccountsError(DataErrorEvent<Collection<Account>> error) {
        if (error.getServerReason() != null) {
            Toast.makeText(getActivity(), error.getServerReason(), Toast.LENGTH_LONG).show();
        } else if (error.getHttpErrorCode() > 0) {
            Toast.makeText(getActivity(), "There was an error contacting the server: " + error.getHttpErrorCode(), Toast.LENGTH_LONG).show();
        }

        if (error.getCachedData() != null) {
            updateAccountsList(error.getCachedData());
        }
    }

}
