package com.mpt.databasehandler;

public class Variable {
    private String name;
    private String type;
    private int length;
    private boolean canBeNull;
    private Index index;

    public Variable(String name, String type) {
        this.name = name;
        this.type = type;
        this.canBeNull = true;
    }

    public Variable(String name, String type, int length) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.canBeNull = true;
    }

    public Variable(String name, String type, boolean canBeNull) {
        this.name = name;
        this.type = type;
        this.canBeNull = canBeNull;
    }

    public Variable(String name, String type, int length, boolean canBeNull) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.canBeNull = canBeNull;
    }

    public Variable(String name, String type, Index index) {
        this.name = name;
        this.type = type;
        this.index = index;
    }

    public Variable(String name, String type, int length, Index index) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.index = index;
    }

    public Variable(String name, String type, boolean canBeNull, Index index) {
        this.name = name;
        this.type = type;
        this.canBeNull = canBeNull;
        this.index = index;
    }

    public Variable(String name, String type, int length, boolean canBeNull, Index index) {
        this.name = name;
        this.type = type;
        this.length = length;
        this.canBeNull = canBeNull;
        this.index = index;
    }
}
