package com.zipcar.testingprototype;

import android.app.Application;

import com.squareup.otto.Bus;
import com.zipcar.testingprototype.accounts.AccountProvider;
import com.zipcar.testingprototype.accounts.RefreshAccountsEvent;
import com.zipcar.testingprototype.auth.AuthProvider;
import com.zipcar.testingprototype.shared.AndroidModule;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import dagger.ObjectGraph;

public class TestingApp extends Application {

    private ObjectGraph applicationGraph;

    protected @Inject AuthProvider authProvider;
    protected @Inject AccountProvider accountProvider;
    protected @Inject Bus bus;

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        MessageBus.get().register(new AuthProvider());
//        MessageBus.get().register(new AccountProvider());
//    }

    @Override
    public void onCreate() {
        super.onCreate();

        applicationGraph = ObjectGraph.create(getModules().toArray());

        applicationGraph.inject(this);

        applicationGraph.inject(authProvider);
        bus.register(authProvider);

        applicationGraph.inject(accountProvider);
        bus.register(accountProvider);
    }

    /**
     * A list of modules to use for the application graph. Subclasses can override this method to
     * provide additional modules provided they call {@code super.getModules()}.
     */
    protected List<Object> getModules() {
        return Arrays.<Object>asList(
                new AndroidModule(this));
    }

    public ObjectGraph getApplicationGraph() {
        return applicationGraph;
    }
}
