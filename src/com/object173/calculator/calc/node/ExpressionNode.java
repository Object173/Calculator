package com.object173.calculator.calc.node;

import com.object173.calculator.model.CalculationOperation;

import java.util.ArrayList;
import java.util.List;

public class ExpressionNode implements Node {

    private final String mKey;
    private final CalculationOperation mProcedure;
    private List<Node> mArgs = new ArrayList<>();

    public ExpressionNode(final String key, final CalculationOperation procedure) {
        this.mProcedure = procedure;
        this.mKey = key;
    }

    public void addArg(final Node arg) {
        mArgs.add(arg);
    }

    @Override
    public double calculate(final double... lastResult) {
        try {
            return mProcedure.calculate(mArgs, lastResult);
        }
        catch (IndexOutOfBoundsException ex) {
            throw new UnsupportedOperationException(mKey);
        }
    }
}
