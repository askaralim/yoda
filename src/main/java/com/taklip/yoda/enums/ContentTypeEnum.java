package com.taklip.yoda.enums;

public enum ContentTypeEnum {
    CONTENT(1, "content"),
    BRAND(2, "brand"),
    ITEM(3, "item"),
    USER(4, "user"),
    HOME(5, "home"),
    TERM(6, "term"),
    SOLUTION(6, "solution");

    private int code;
    private String type;

    ContentTypeEnum(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public static ContentTypeEnum getType(Integer code) {
        if (code == null) {
            return CONTENT;
        }

        ContentTypeEnum[] statusEnums = ContentTypeEnum.values();

        for (ContentTypeEnum statusEnum : statusEnums) {
            if (statusEnum.getCode() == code) {
                return statusEnum;
            }
        }

        return CONTENT;
    }

    public static int getCode(String type) {
        if (type == null) {
            return CONTENT.code;
        }

        ContentTypeEnum[] statusEnums = ContentTypeEnum.values();

        for (ContentTypeEnum statusEnum : statusEnums) {
            if (statusEnum.getType().equals(type)) {
                return statusEnum.getCode();
            }
        }

        return CONTENT.code;
    }

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }
}