package com.revature.exceptions;

public class NotAuthorizedException extends AuthorizationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6913652353088563179L;

	public NotAuthorizedException() {
		super();
	}

	public NotAuthorizedException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotAuthorizedException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotAuthorizedException(String message) {
		super(message);
	}

	public NotAuthorizedException(Throwable cause) {
		super(cause);
	}

}
