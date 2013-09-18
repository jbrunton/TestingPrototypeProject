package com.zipcar.testingprototype.models;

public class Session {
    private String token;
    // TODO: expiry date

    public String getToken() {
        return token;
    }

    public Session(String token) {
        this.token = token;
    }
}
