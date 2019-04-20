package com.taklip.yoda.dialect;

public class MySQLDialect implements Dialect {

	public String getLimitString(String sql, int offset, int limit) {
		return getLimitString(
			sql, offset, Integer.toString(offset), limit, Integer.toString(limit));
	}

	public boolean supportsLimitOffset() {
		return true;
	}

	public boolean supportsLimit() {
		return true;
	}

	public String getLimitString(
			String sql, int offset, String offsetPlaceholder, int limit,
			String limitPlaceholder) {
		StringBuilder stringBuilder = new StringBuilder(sql);

		stringBuilder.append(" limit ");

		if (offset > 0) {
			stringBuilder.append(offsetPlaceholder).append(",").append(limitPlaceholder);
		}
		else {
			stringBuilder.append(limitPlaceholder);
		}

		return stringBuilder.toString();
	}
}