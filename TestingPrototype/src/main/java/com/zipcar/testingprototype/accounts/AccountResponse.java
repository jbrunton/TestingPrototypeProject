package com.zipcar.testingprototype.accounts;

import java.util.Collection;
import java.util.Date;

public class AccountResponse {
    private boolean success;
    private String reason;

    public static class ZapiAccount {
        private int accountId;
        private String accountName;

        public int getAccountId() {
            return accountId;
        }

        public String getAccountName() {
            return accountName;
        }
    }

    private Collection<ZapiAccount> accounts;

    public boolean getSuccess(){
        return success;
    }

    public String getReason() {
        return reason;
    }

    public Collection<ZapiAccount> getAccounts() {
        return accounts;
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