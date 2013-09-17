package com.zipcar.testingprototype.auth;

public class AuthResponseEvent {
    private boolean success;
    private int responseCode;

    public boolean success () {
        return success;
    }

    public int getResponseCode() {
        return responseCode;
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

        public Builder setResponseCode (int responseCode) {
            event.responseCode = responseCode;
            return this;
        }
    }
}
