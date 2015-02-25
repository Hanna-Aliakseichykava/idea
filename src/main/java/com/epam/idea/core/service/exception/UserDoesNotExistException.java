package com.epam.idea.core.service.exception;

public class UserDoesNotExistException extends RuntimeException {

	public UserDoesNotExistException(Throwable cause) {
		super(cause);
	}

	public UserDoesNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserDoesNotExistException(String message) {
		super(message);
	}

	public UserDoesNotExistException() {
	}
}
