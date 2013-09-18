package com.zipcar.testingprototype.models;

public class AuthToken {
    private String token;
    // TODO: expiry date

    public String getToken() {
        return token;
    }

    public AuthToken(String token) {
        this.token = token;
    }
}
