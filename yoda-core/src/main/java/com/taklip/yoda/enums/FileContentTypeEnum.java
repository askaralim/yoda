package com.taklip.yoda.enums;

public enum FileContentTypeEnum {
	CONTENT(1, "content"), BRAND(2, "brand"), ITEM(3, "item"), USER(4, "user");

	private int code;
	private String type;

	FileContentTypeEnum(int code, String type) {
		this.code = code;
		this.type = type;
	}

	public static FileContentTypeEnum getType(Integer code) {
		if (code == null) {
			return CONTENT;
		}

		FileContentTypeEnum[] statusEnums = FileContentTypeEnum.values();

		for (FileContentTypeEnum statusEnum : statusEnums) {
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

		FileContentTypeEnum[] statusEnums = FileContentTypeEnum.values();

		for (FileContentTypeEnum statusEnum : statusEnums) {
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