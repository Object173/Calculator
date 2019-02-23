package com.object173.calculator.resources.bundle;

import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class Utf8PropertyResourceBundle extends ResourceBundle {

    private final PropertyResourceBundle bundle;

    Utf8PropertyResourceBundle(final PropertyResourceBundle bundle) {
        this.bundle = bundle;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Enumeration getKeys() {
        return bundle.getKeys();
    }

    @Override
    protected Object handleGetObject(final String key) {
        final String value = bundle.getString(key);
        return new String(value.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
}
