package com.object173.calculator.model;

public class OperationRepository {
    private static final OperationRepository ourInstance = new OperationRepository();

    private final Operator[] fOperators = new Operator[] {
            new Operator(1,"+", (args, lastResult) -> args.get(0).calculate() + args.get(1).calculate()),
            new Operator(1,"-", (args, lastResult) ->
                    args.size() > 1 ? args.get(0).calculate() - args.get(1).calculate() : -args.get(0).calculate()),

            new Operator(2,"*", (args, lastResult) -> args.get(0).calculate() * args.get(1).calculate()),
            new Operator(2,"/", (args, lastResult) -> args.get(0).calculate() / args.get(1).calculate()),
            new Operator(2,"%", (args, lastResult) -> args.get(0).calculate() % args.get(1).calculate()),
            new Operator(2,"div", (args, lastResult) -> Math.floor(args.get(0).calculate()/args.get(1).calculate())),
            new Operator(2,"^", (args, lastResult) -> Math.pow(args.get(0).calculate(),args.get(1).calculate())),

            new Operator(2,"sin", (args, lastResult) -> Math.sin(args.get(0).calculate())),
            new Operator(2,"cos", (args, lastResult) -> Math.cos(args.get(0).calculate())),
            new Operator(2,"tan", (args, lastResult) -> Math.tan(args.get(0).calculate())),

            new Operator(2,">", (args, lastResult) ->
                    args.get(0).calculate() > args.get(1).calculate() ? 1d : 0d),
            new Operator(2,"<", (args, lastResult) ->
                    args.get(0).calculate() < args.get(1).calculate() ? 1d : 0d),
            new Operator(2,"=", (args, lastResult) ->
                    args.get(0).calculate() == args.get(1).calculate() ? 1d : 0d),
            new Operator(2,"!", (args, lastResult) ->
                    args.get(0).calculate() != args.get(1).calculate() ? 1d : 0d),

            new Operator(0,"?", (args, lastResult) -> args.get(1).calculate(args.get(0).calculate())),
            new Operator(1,":", (args, lastResult) ->
                    lastResult[0] != 0d ? args.get(0).calculate() : args.get(1).calculate())
    };

    private final Bracket[] fBrackets = new Bracket[] {
            new Bracket("(",")", 10)
    };

    private final Constant[] fConstants = new Constant[] {
            new Constant("pi", Math.PI),
            new Constant("e", Math.E)
    };

    public static OperationRepository getInstance() {
        return ourInstance;
    }

    private OperationRepository() {
    }

    public Operator[] getOperators() {
        return fOperators;
    }

    public Bracket[] getBrackets() {
        return fBrackets;
    }

    public Constant[] getConstants() {
        return fConstants;
    }
}
