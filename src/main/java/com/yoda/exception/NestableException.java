package com.yoda.exception;

public class NestableException extends Exception {

	public NestableException() {
		super();
	}

	public NestableException(String msg) {
		super(msg);
	}

	public NestableException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NestableException(Throwable cause) {
		super(cause);
	}

}