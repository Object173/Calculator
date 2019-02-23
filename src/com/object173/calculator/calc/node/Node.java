package com.object173.calculator.calc.node;

public interface Node {
    double calculate(double... lastResult) throws UnsupportedOperationException, ArithmeticException;
}
