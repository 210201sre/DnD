package com.revature.exceptions;

public class PasswordMismatchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6042240468587514052L;

	public PasswordMismatchException() {
		super();
	}

	public PasswordMismatchException(String message) {
		super(message);
	}

	public PasswordMismatchException(Throwable cause) {
		super(cause);
	}

	public PasswordMismatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public PasswordMismatchException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
