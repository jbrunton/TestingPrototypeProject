package com.zipcar.testingprototype;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.zipcar.testingprototype.data.DataAvailableEvent;
import com.zipcar.testingprototype.data.DataErrorEvent;
import com.zipcar.testingprototype.models.Account;
import com.zipcar.testingprototype.shared.BaseListFragment;

import java.util.Collection;

import javax.inject.Inject;

public class AccountsListFragment extends BaseListFragment {

    @Inject protected Bus bus;

    private ArrayAdapter<Account> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        Spinner spinner = (Spinner) view.findViewById(R.id.accounts_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.accounts_name_string_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        return view;
    }

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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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

    protected void updateAccountsList(Collection<Account> accounts) {
        // Just subscribed, but no data available yet
        if (accounts != null) {
            this.adapter.clear();
            for (Account acc : accounts) {
                this.adapter.add(acc);
            }
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
