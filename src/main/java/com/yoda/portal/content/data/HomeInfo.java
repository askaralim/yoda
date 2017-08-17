package com.yoda.portal.content.data;

import java.util.List;

import com.yoda.homepage.model.HomePage;
import com.yoda.kernal.model.Pagination;

public class HomeInfo extends DataInfo {
	List<DataInfo> homePageDatas;
	DataInfo homePageFeatureData;
	String pageTitle;
	Pagination<HomePage> page;

	public Pagination<HomePage> getPage() {
		return page;
	}

	public void setPage(Pagination<HomePage> page) {
		this.page = page;
	}

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
