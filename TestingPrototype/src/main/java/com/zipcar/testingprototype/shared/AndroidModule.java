package com.zipcar.testingprototype.shared;

import android.content.Context;

import com.squareup.otto.Bus;
import com.zipcar.testingprototype.AccountsListFragment;
import com.zipcar.testingprototype.ListActivity;
import com.zipcar.testingprototype.LoginFragment;
import com.zipcar.testingprototype.MainActivity;
import com.zipcar.testingprototype.TestingApp;
import com.zipcar.testingprototype.accounts.AccountProvider;
import com.zipcar.testingprototype.auth.AuthProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        library = true,
        injects = {
                TestingApp.class,

                // fragments
                LoginFragment.class,
                AccountsListFragment.class,

                // activities
                MainActivity.class,
                ListActivity.class,

                // providers
                AuthProvider.class,
                AccountProvider.class
        }
)
public class AndroidModule {
    private final TestingApp application;

    public AndroidModule() {
        this.application = null;
    }

    public AndroidModule(TestingApp application) {
        this.application = application;
    }

    /**
     * Allow the application context to be injected but require that it be annotated with
     * {@link ForApplication @ForApplication} to explicitly differentiate it from an activity context.
     */
    @Provides @Singleton @ForApplication
    Context provideApplicationContext() {
        return application;
    }

    @Provides @Singleton
    Bus provideBus() {
        return new Bus();
    }

    @Provides @Singleton
    AuthProvider provideAuthProvider() {
        return new AuthProvider();
    }

    @Provides @Singleton
    AccountProvider provideAccountProvider() {
        return new AccountProvider();
    }
}