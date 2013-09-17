package com.zipcar.testingprototype.auth;

import java.util.Date;

public class AuthResponse {
    private boolean success;
    private String reason;
//    private Head head;
    private Session session;
    private String action;
    private String userId;

    public boolean getSuccess(){
        return success;
    }

    public String getReason() {
        return reason;
    }

    public Session getSession() {
        return session;
    }

    public static class Session {
        private String token;
        private long expires;

        public Session(String token, long expires) {
            this.token = token;
            this.expires = expires;
        }

        public String getToken() {
            return token;
        }

        public Date getExpires() {
            return new Date(expires * 1000);
        }

        public boolean isValid() {
            return ( token != null && (new Date(expires)).after(new Date()) );
        }


    }

}