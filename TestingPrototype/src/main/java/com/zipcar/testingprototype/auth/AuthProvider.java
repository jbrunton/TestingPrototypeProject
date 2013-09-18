package com.zipcar.testingprototype.auth;

import com.squareup.otto.Subscribe;
import com.zipcar.testingprototype.data.DataErrorEvent;
import com.zipcar.testingprototype.models.AuthToken;
import com.zipcar.testingprototype.shared.MessageBus;

public class AuthProvider {

    @Subscribe
    public void onAuthenticate (AuthenticateEvent event) {
        new AuthTask(this, event.getUsername(), event.getPassword()).execute();
    }

    protected AuthToken modelify(AuthResponse response) {
        return new AuthToken(response.getSession().getToken());
    }

    public void postAuthResponse (AuthResponse response) {
        if (response.getSuccess()) {
            AuthToken token = modelify(response);
            MessageBus.get().post(token);
        } else {
            MessageBus.get().post(new DataErrorEvent<AuthToken>(response.getReason(), null));
        }
    }

    public void postAuthError (int responseCode) {
        MessageBus.get().post(new DataErrorEvent<AuthToken>(responseCode, null));
    }

}
