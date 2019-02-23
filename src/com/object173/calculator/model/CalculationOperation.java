package com.object173.calculator.model;

import com.object173.calculator.calc.node.Node;

import java.util.List;

public interface CalculationOperation {
    double calculate(List<Node> args, double... lastResult);
}
