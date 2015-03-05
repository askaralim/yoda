package com.yoda.portal.content.data;

public class PageInfo extends DataInfo {
	String pageTitle;
	String pageBody;
	String keywords;

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getPageBody() {
		return pageBody;
	}

	public void setPageBody(String pageBody) {
		this.pageBody = pageBody;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}
}
