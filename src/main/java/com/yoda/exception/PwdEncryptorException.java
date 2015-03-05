package com.yoda.exception;

@Deprecated
public class PwdEncryptorException extends PortalException {

	public PwdEncryptorException() {
		super();
	}

	public PwdEncryptorException(String msg) {
		super(msg);
	}

	public PwdEncryptorException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PwdEncryptorException(Throwable cause) {
		super(cause);
	}

}