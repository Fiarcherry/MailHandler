package com.mpt.databasehandler;

public enum Index {
    PRIMARY ("PRIMARY"),
    UNIQUE ("UNIQUE");

    private String title;

    Index(String title){
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
