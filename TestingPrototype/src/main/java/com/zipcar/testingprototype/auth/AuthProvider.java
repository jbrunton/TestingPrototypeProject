package com.zipcar.testingprototype.auth;

import com.squareup.otto.Subscribe;
import com.zipcar.testingprototype.data.DataErrorEvent;
import com.zipcar.testingprototype.models.Session;
import com.zipcar.testingprototype.shared.MessageBus;

public class AuthProvider {

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
            MessageBus.get().post(token);
        } else {
            MessageBus.get().post(new DataErrorEvent<Session>(response.getReason(), null));
        }
    }

    public void postAuthError (int responseCode) {
        MessageBus.get().post(new DataErrorEvent<Session>(responseCode, null));
    }

}
