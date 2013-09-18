package com.zipcar.testingprototype.models;

public class Account {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Account(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
