package com.yoda.portal.content.data;

public class SectionInfo {
	String sectionNaturalKey;
	String topSectionNaturalKey;
	long sectionId;
	long topSectionId;
	String sectionShortTitle;
	String sectionTitle;
	String sectionDesc;
	String sectionUrl;
	int pageNum;
	int pageStart;
	int pageEnd;
	int pageTotal;
	String sortBy;
	Object sectionFeatureDatas[];
	Object sectionDatas[];
	SectionInfo titleSectionInfos[];
	SectionInfo childSectionInfos[];
	int childCount;

	public int getChildCount() {
		return childCount;
	}

	public void setChildCount(int childCount) {
		this.childCount = childCount;
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

	public Object[] getSectionDatas() {
		return sectionDatas;
	}

	public void setSectionDatas(Object[] sectionDatas) {
		this.sectionDatas = sectionDatas;
	}

	public long getSectionId() {
		return sectionId;
	}

	public void setSectionId(long sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionShortTitle() {
		return sectionShortTitle;
	}

	public void setSectionShortTitle(String sectionShortTitle) {
		this.sectionShortTitle = sectionShortTitle;
	}

	public String getSectionTitle() {
		return sectionTitle;
	}

	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}

	public Object getSectionDatas(int index) {
		return sectionDatas[index];
	}

	public String getSectionDesc() {
		return sectionDesc;
	}

	public void setSectionDesc(String sectionDesc) {
		this.sectionDesc = sectionDesc;
	}

	public Object[] getSectionFeatureDatas() {
		return sectionFeatureDatas;
	}

	public void setSectionFeatureDatas(Object[] sectionFeatureDatas) {
		this.sectionFeatureDatas = sectionFeatureDatas;
	}

	public SectionInfo[] getChildSectionInfos() {
		return childSectionInfos;
	}

	public void setChildSectionInfos(SectionInfo[] childSectionInfos) {
		this.childSectionInfos = childSectionInfos;
	}

	public String getSectionUrl() {
		return sectionUrl;
	}

	public void setSectionUrl(String sectionUrl) {
		this.sectionUrl = sectionUrl;
	}

	public SectionInfo[] getTitleSectionInfos() {
		return titleSectionInfos;
	}

	public void setTitleSectionInfos(SectionInfo[] titleSectionInfos) {
		this.titleSectionInfos = titleSectionInfos;
	}

	public long getTopSectionId() {
		return topSectionId;
	}

	public void setTopSectionId(long topSectionId) {
		this.topSectionId = topSectionId;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSectionNaturalKey() {
		return sectionNaturalKey;
	}

	public void setSectionNaturalKey(String sectionNaturalKey) {
		this.sectionNaturalKey = sectionNaturalKey;
	}

	public String getTopSectionNaturalKey() {
		return topSectionNaturalKey;
	}

	public void setTopSectionNaturalKey(String topSectionNaturalKey) {
		this.topSectionNaturalKey = topSectionNaturalKey;
	}
}
