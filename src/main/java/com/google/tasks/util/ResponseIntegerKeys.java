package com.google.tasks.util;

public class ResponseIntegerKeys {

	public static final int EXC = 1000;
	public static final int WRONG_EMAIL = 1005;
	public static final int SQL_EXC = 1062;
	public static final int CONSTRAINT_EXC = 1007;
	public static final int VERIFY_CODE_ERROR = 1016;
	public static final int EMPTY_LIST = 111;
	public static final int OK = 200;
	public static final int NOT_FOUND = 404;
	public static final int CREATED = 201;
	public static final int ERROR = 435;
	public static final int MAX_SIZE_EXCEPTION = 898;
	public static final int DATA_INTEGERITY_VIOLATION = 1223;
	public static final int NOT_VALID_EXCEPTION = 2309;

	private ResponseIntegerKeys() {
		throw new IllegalStateException("Utility class");
	}
}
