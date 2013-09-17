package com.zipcar.testingprototype.auth;

public class AuthResponseEvent {
    private boolean success;

    public boolean success () {
        return success;
    }

    public static class Builder {
        private AuthResponseEvent event = new AuthResponseEvent();

        public Builder setSuccess (boolean success) {
            event.success = success;
            return this;
        }

        public AuthResponseEvent getInstance () {
            return event;
        }
    }
}
