package com.object173.calculator.calc.node;

public class ValueNode implements Node {

    private final double mValue;

    public ValueNode(double value) {
        this.mValue = value;
    }

    @Override
    public double calculate(final double... lastResult) {
        return mValue;
    }
}
