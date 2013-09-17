package com.zipcar.testingprototype.auth;

public class AuthenticateEvent {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static class Builder {
        private AuthenticateEvent event = new AuthenticateEvent();

        public Builder setUserName (String userName) {
            event.username = userName;
            return this;
        }

        public Builder setPassword (String password) {
            event.password = password;
            return this;
        }

        public AuthenticateEvent getInstance () {
            return event;
        }
    }
}
