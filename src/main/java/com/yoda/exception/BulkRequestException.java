package com.yoda.exception;

public class BulkRequestException extends PortalException {
	public BulkRequestException() {
		super();
	}

	public BulkRequestException(String msg) {
		super(msg);
	}

	public BulkRequestException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public BulkRequestException(Throwable cause) {
		super(cause);
	}
}
