package com.taklip.yoda.model;

public class SearchInfo {
	int hitsCount;
	String query;
	Object searchDatas[];
	int pageNum;
	int pageStart;
	int pageEnd;
	int pageTotal;

	public Object[] getSearchDatas() {
		return searchDatas;
	}

	public void setSearchDatas(Object[] searchDatas) {
		this.searchDatas = searchDatas;
	}

	public int getHitsCount() {
		return hitsCount;
	}

	public void setHitsCount(int hitsCount) {
		this.hitsCount = hitsCount;
	}

	public int getPageEnd() {
		return pageEnd;
	}

	public void setPageEnd(int pageEnd) {
		this.pageEnd = pageEnd;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageStart() {
		return pageStart;
	}

	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
}
