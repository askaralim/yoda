package com.taklip.yoda.dialect;

public interface Dialect {
	public boolean supportsLimit();

	public boolean supportsLimitOffset();

	public String getLimitString(String sql, int offset, int limit);
}