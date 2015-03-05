package com.yoda.portal.content.data;

import java.util.List;

public class HomeInfo extends DataInfo {
	List<DataInfo> homePageDatas;
	DataInfo homePageFeatureData;
	String pageTitle;

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public List<DataInfo> getHomePageDatas() {
		return homePageDatas;
	}

	public void setHomePageDatas(List<DataInfo> homePageDatas) {
		this.homePageDatas = homePageDatas;
	}

	public DataInfo getHomePageFeatureData() {
		return homePageFeatureData;
	}

	public void setHomePageFeatureData(DataInfo homePageFeatureData) {
		this.homePageFeatureData = homePageFeatureData;
	}
}
