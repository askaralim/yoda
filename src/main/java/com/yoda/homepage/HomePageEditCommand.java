package com.yoda.homepage;

import java.util.Vector;

public class HomePageEditCommand {
	Vector homePages;
	long ids[];
	String seqNums[];
	String featureData;
	String pageTitle;

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public Vector getHomePages() {
		return homePages;
	}

	public String[] getSeqNums() {
		return seqNums;
	}

	public void setSeqNums(String[] seqNums) {
		this.seqNums = seqNums;
	}

	public void setHomePages(Vector homePages) {
		this.homePages = homePages;
	}

	public long[] getIds() {
		return ids;
	}

	public void setIds(long[] ids) {
		this.ids = ids;
	}

	public String getFeatureData() {
		return featureData;
	}

	public void setFeatureData(String featureData) {
		this.featureData = featureData;
	}
}