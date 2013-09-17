package com.zipcar.testingprototype.auth;

import com.squareup.otto.Subscribe;
import com.zipcar.testingprototype.shared.MessageBus;

public class AuthProvider {

    @Subscribe
    public void onAuthenticate (AuthenticateEvent event) {

    }

    public void postAuthResponse (AuthResponse response) {
        AuthResponseEvent event = new AuthResponseEvent.Builder()
                .setSuccess(response.getSuccess())
                .getInstance();

        MessageBus.get().post(event);
    }
}
