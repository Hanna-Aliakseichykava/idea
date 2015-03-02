package com.epam.idea.core.service.exception;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(Throwable cause) {
		super(cause);
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(Long userId) {
		super("Could not find user with id: " + userId + ".");
	}
}
