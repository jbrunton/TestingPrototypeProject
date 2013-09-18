package com.zipcar.testingprototype.auth;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.zipcar.testingprototype.data.DataErrorEvent;
import com.zipcar.testingprototype.models.Session;

import javax.inject.Inject;

public class AuthProvider {

    @Inject
    protected Bus bus;

    @Subscribe
    public void onAuthenticate (AuthenticateEvent event) {
        new AuthTask(this, event.getUsername(), event.getPassword()).execute();
    }

    protected Session modelify(AuthResponse response) {
        return new Session(response.getSession().getToken());
    }

    public void postAuthResponse (AuthResponse response) {
        if (response.getSuccess()) {
            Session token = modelify(response);
            bus.post(token);
        } else {
            bus.post(new DataErrorEvent<Session>(response.getReason(), null));
        }
    }

    public void postAuthError (int responseCode) {
        bus.post(new DataErrorEvent<Session>(responseCode, null));
    }

}
