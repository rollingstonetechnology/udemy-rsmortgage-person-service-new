package com.rollingstone.exception;

/**
 * for HTTP 400 Bad Request errors
 */
public final class HTTP400Exception extends RuntimeException {
	public HTTP400Exception() {
		super();
	}

	public HTTP400Exception(String message, Throwable cause) {
		super(message, cause);
	}

	public HTTP400Exception(String message) {
		super(message);
	}

	public HTTP400Exception(Throwable cause) {
		super(cause);
	}
}
