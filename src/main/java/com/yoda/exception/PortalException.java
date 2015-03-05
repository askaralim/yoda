package com.yoda.exception;

public class PortalException extends NestableException {

	public PortalException() {
		super();
	}

	public PortalException(String msg) {
		super(msg);
	}

	public PortalException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PortalException(Throwable cause) {
		super(cause);
	}

}