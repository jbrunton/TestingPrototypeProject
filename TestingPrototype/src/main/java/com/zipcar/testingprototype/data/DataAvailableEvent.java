package com.zipcar.testingprototype.data;

public class DataAvailableEvent<D> {
    private D data;

    public D getData() {
        return data;
    }

    public DataAvailableEvent(D data) {
        this.data = data;
    }
}
