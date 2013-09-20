package com.zipcar.testingprototype;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.squareup.otto.Bus;
import com.zipcar.testingprototype.shared.AndroidModule;
import com.zipcar.testingprototype.shared.BaseActivity;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import dagger.Module;

public class TestListActivity extends BaseActivity {
    @Inject
    Bus bus;

    private AccountsListFragment accountsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_fragment_host);
        accountsListFragment = new AccountsListFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_holder, accountsListFragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    public Bus getBus() {
        return bus;
    }

    @Override
    protected List<Object> getModules() {
        List<Object> modules = new LinkedList<Object>(super.getModules());
        modules.add(new TestListActivityModule());
        return modules;
    }
}
