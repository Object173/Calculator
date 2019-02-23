package com.object173.calculator.calc;

import java.io.IOException;
import java.text.ParseException;
import java.util.MissingFormatArgumentException;

public interface Calculable {
    double calculate() throws UnsupportedOperationException, IllegalStateException;
    Calculator parse(final String input) throws IOException, ParseException, MissingFormatArgumentException;
}
