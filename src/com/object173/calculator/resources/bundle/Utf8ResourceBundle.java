package com.object173.calculator.resources.bundle;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public abstract class Utf8ResourceBundle {

    public static ResourceBundle getBundle(final String baseName) {
        return createUtf8PropertyResourceBundle(
                ResourceBundle.getBundle(baseName));
    }

    private static ResourceBundle createUtf8PropertyResourceBundle(
            final ResourceBundle bundle) {
        if (!(bundle instanceof PropertyResourceBundle)) {
            return bundle;
        }
        return new Utf8PropertyResourceBundle((PropertyResourceBundle) bundle);
    }
}
