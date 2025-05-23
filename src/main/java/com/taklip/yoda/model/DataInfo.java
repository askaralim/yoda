package com.taklip.yoda.model;

import java.util.Hashtable;

public class DataInfo {
    Hashtable<String, String> attributes = new Hashtable<String, String>();

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
