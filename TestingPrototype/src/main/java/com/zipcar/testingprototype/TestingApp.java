package com.zipcar.testingprototype;

import android.app.Application;

import com.zipcar.testingprototype.accounts.AccountProvider;
import com.zipcar.testingprototype.auth.AuthProvider;
import com.zipcar.testingprototype.shared.MessageBus;

public class TestingApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MessageBus.get().register(new AuthProvider());
        MessageBus.get().register(new AccountProvider());
    }
}
