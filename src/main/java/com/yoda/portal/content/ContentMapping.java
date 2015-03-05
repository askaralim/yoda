package com.yoda.portal.content;

public class ContentMapping {
	boolean enforceProtocol = true;
	boolean ssl;
	boolean authentication;

	public boolean isAuthentication() {
		return authentication;
	}

	public void setAuthentication(boolean authentication) {
		this.authentication = authentication;
	}

	public boolean isSsl() {
		return ssl;
	}

	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

	public boolean isEnforceProtocol() {
		return enforceProtocol;
	}

	public void setEnforceProtocol(boolean enforceProtocol) {
		this.enforceProtocol = enforceProtocol;
	}
}
