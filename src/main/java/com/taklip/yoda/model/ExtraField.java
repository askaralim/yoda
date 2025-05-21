package com.taklip.yoda.model;

public class ExtraField implements Comparable<ExtraField> {
    private int index;

    private String key;

    private String value;

    public int compareTo(ExtraField extraField) {
        return (index < extraField.index ? -1 : (index == extraField.index ? 0 : 1));
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}