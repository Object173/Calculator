package com.object173.calculator.model;

public class Bracket {
    public final String startKey;
    public final String  endKey;
    public final int priority;

    Bracket(String startKey, String endKey, int priority) {
        this.startKey = startKey;
        this.endKey = endKey;
        this.priority = priority;
    }
}
