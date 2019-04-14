package com.yoda.portal.content.data;

import java.util.List;

import com.yoda.content.model.Content;
import com.yoda.kernal.model.Pagination;

public class HomeInfo extends DataInfo {
	List<Content> homePageDatas;
	Content homePageFeatureData;
	String pageTitle;
	Pagination<Content> page;

	public Pagination<Content> getPage() {
		return page;
	}

	public void setPage(Pagination<Content> page) {
		this.page = page;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public List<Content> getHomePageDatas() {
		return homePageDatas;
	}

	public void setHomePageDatas(List<Content> homePageDatas) {
		this.homePageDatas = homePageDatas;
	}

	public Content getHomePageFeatureData() {
		return homePageFeatureData;
	}

	public void setHomePageFeatureData(Content homePageFeatureData) {
		this.homePageFeatureData = homePageFeatureData;
	}
}
