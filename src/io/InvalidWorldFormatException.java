package io;

import java.io.IOException;

public class InvalidWorldFormatException extends IOException {

	private static final long serialVersionUID = 1L;

	public InvalidWorldFormatException() {
		super();
	}

	public InvalidWorldFormatException(String message) {
		super(message);
	}
}
