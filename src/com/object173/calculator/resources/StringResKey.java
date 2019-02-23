package com.object173.calculator.resources;

public enum StringResKey {
	CALCULATOR_WINDOW_TITLE("calc_window_title"),
	ERROR_READ_EXPRESSION("error_read_expression"),
	ERROR_UNSUPPORTED_OPERATION("error_unsupported_operation"),
	ERROR_UNKNOWN_OPERATOR("error_unknown_operator"),
	ERROR_INVALID_EXPRESSION("error_invalid_expression"),
	ERROR_MISSING_OPERATOR("error_missing_operator");
	
	private final String mKey;
	
	StringResKey(final String key) {
		mKey = key;
	}
	public String getKey() {
		return mKey;
	}
}
