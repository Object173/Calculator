package com.object173.calculator.calc;

import com.object173.calculator.calc.node.*;
import com.object173.calculator.model.Bracket;
import com.object173.calculator.model.Constant;
import com.object173.calculator.model.Operator;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingFormatArgumentException;
import java.util.Stack;

public class Calculator implements Calculable {

    private Node mLastHead;

    private final Map<String, Integer> mBracketMap;
    private final Map<String, Operator> mOperatorMap;
    private final Map<String, Double> mConstantMap;

    public Calculator(final Bracket[] brackets, final Operator[] operators, final Constant[] constants) {
        mBracketMap = new HashMap<>();
        for(Bracket bracket : brackets) {
            mBracketMap.put(bracket.startKey, bracket.priority);
            mBracketMap.put(bracket.endKey, -bracket.priority);
        }
        mOperatorMap = new HashMap<>();
        for (Operator operator : operators) {
            mOperatorMap.put(operator.key, operator);
        }
        mConstantMap = new HashMap<>();
        for (Constant constant : constants) {
            mConstantMap.put(constant.key, constant.value);
        }
    }

    private static class NodeEntry {
        final ExpressionNode node;
        final int priority;

        NodeEntry(final ExpressionNode node, final int priority) {
            this.node = node;
            this.priority = priority;
        }
    }

    @Override
    public double calculate() throws UnsupportedOperationException, IllegalStateException {
        if(mLastHead == null) {
            throw new IllegalStateException();
        }
        mLastHead = new ValueNode(mLastHead.calculate());
        return mLastHead.calculate();
    }

    @Override
    public Calculator parse(final String input) throws IOException, ParseException, MissingFormatArgumentException {
        Node lastCloseOperation = null;
        final Stack<NodeEntry> openOperations = new Stack<>();
        int nestingLevel = 0;

        final StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(input));
        tokenizer.ordinaryChar('-');
        tokenizer.ordinaryChar('/');

        while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {

            if(tokenizer.ttype == StreamTokenizer.TT_NUMBER) {
                lastCloseOperation = addValue(lastCloseOperation, tokenizer.nval);
                continue;
            }

            final boolean isChar = (tokenizer.ttype != StreamTokenizer.TT_WORD);
            final String token = isChar ? Character.toString((char)tokenizer.ttype) : tokenizer.sval;

            if(mBracketMap.containsKey(token)) {
                nestingLevel += mBracketMap.get(token);
                continue;
            }

            if(mConstantMap.containsKey(token)) {
                lastCloseOperation = addValue(lastCloseOperation, mConstantMap.get(token));
                continue;
            }

            if(!mOperatorMap.containsKey(token)) {
                throw new ParseException(token, tokenizer.lineno());
            }

            final ExpressionNode newNode = new ExpressionNode(token, mOperatorMap.get(token).procedure);
            final NodeEntry currentNode = new NodeEntry(newNode, nestingLevel + mOperatorMap.get(token).priority);
            while(!openOperations.isEmpty() && lastCloseOperation != null &&
                    currentNode.priority <= openOperations.peek().priority) {
                final NodeEntry lastNode = openOperations.pop();
                lastNode.node.addArg(lastCloseOperation);
                lastCloseOperation = lastNode.node;
            }
            if(lastCloseOperation != null) {
                currentNode.node.addArg(lastCloseOperation);
                lastCloseOperation = null;
            }
            openOperations.push(currentNode);
        }

        while(!openOperations.isEmpty() && lastCloseOperation != null) {
            final NodeEntry lastNode = openOperations.pop();
            lastNode.node.addArg(lastCloseOperation);
            lastCloseOperation = lastNode.node;
        }

        if(!openOperations.isEmpty() || lastCloseOperation == null) {
            throw new ParseException(null, input.length());
        }

        mLastHead = lastCloseOperation;
        return this;
    }

    private ValueNode addValue(final Node lastCloseOperation,final double value) throws MissingFormatArgumentException {
        if(lastCloseOperation != null) {
            throw new MissingFormatArgumentException(Double.toString(value));
        }
        return new ValueNode(value);
    }
}
