package com.revature.exceptions;

public class UnsuccessfulLoginException extends AuthorizationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4255499437915821669L;

	public UnsuccessfulLoginException() {
		super();
	}

	public UnsuccessfulLoginException(String message) {
		super(message);
	}

	public UnsuccessfulLoginException(Throwable cause) {
		super(cause);
	}

	public UnsuccessfulLoginException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnsuccessfulLoginException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
