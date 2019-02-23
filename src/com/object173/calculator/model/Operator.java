package com.object173.calculator.model;

public class Operator {
    public final int priority;
    public final String key;
    public final CalculationOperation procedure;

    Operator(int priority, String key, CalculationOperation procedure) {
        this.priority = priority;
        this.procedure = procedure;
        this.key = key;
    }
}
