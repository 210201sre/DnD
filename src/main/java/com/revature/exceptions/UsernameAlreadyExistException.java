package com.revature.exceptions;

public class UsernameAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = -7211076362649921353L;

	public UsernameAlreadyExistException() {
		super();
	}

	public UsernameAlreadyExistException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UsernameAlreadyExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public UsernameAlreadyExistException(String message) {
		super(message);
	}

	public UsernameAlreadyExistException(Throwable cause) {
		super(cause);
	}

}
