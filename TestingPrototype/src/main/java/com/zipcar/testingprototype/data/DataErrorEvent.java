package com.zipcar.testingprototype.data;

public class DataErrorEvent<D> {
    private D cachedData;
    private String serverReason;
    private int httpErrorCode;

    public D getCachedData() {
        return cachedData;
    }

    public String getServerReason() {
        return serverReason;
    }

    public int getHttpErrorCode() {
        return httpErrorCode;
    }

    public DataErrorEvent(int httpErrorCode, D cachedData) {
        this.httpErrorCode = httpErrorCode;
        this.cachedData = cachedData;
    }

    public DataErrorEvent(String serverReason, D cachedData) {
        this.serverReason = serverReason;
        this.cachedData = cachedData;
    }
}
