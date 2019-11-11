package com.mpt.databasehandler;

public enum JoinType {
    LEFT ("LEFT"),
    INNER ("INNER");

    private String title;

    JoinType(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
