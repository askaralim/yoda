package com.yoda.portal.content.data;

import java.util.Hashtable;

public class DataInfo {
	Hashtable<String, String> attributes;

	public Hashtable getAttributes() {
		return attributes;
	}

	public void setAttributes(Hashtable<String, String> attributes) {
		this.attributes = attributes;
	}

	public String getAttribute(String key) {
		return (String) attributes.get(key);
	}

	public void setAttribute(String key, String value) {
		attributes.put(key, value);
	}
}
