package com.yoda.exception;

public class SystemException extends NestableException {

	public SystemException() {
		super();
	}

	public SystemException(String msg) {
		super(msg);
	}

	public SystemException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public SystemException(Throwable cause) {
		super(cause);
	}

}