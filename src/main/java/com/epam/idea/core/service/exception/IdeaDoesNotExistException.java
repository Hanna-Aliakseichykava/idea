package com.epam.idea.core.service.exception;

public class IdeaDoesNotExistException extends RuntimeException {

	public IdeaDoesNotExistException() {
	}

	public IdeaDoesNotExistException(String message) {
		super(message);
	}

	public IdeaDoesNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public IdeaDoesNotExistException(Throwable cause) {
		super(cause);
	}
}
