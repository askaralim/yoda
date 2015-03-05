package com.yoda.homepage;

public class HomePageDisplayCommand {
	long homePageId;
	String dataType;
	String description;
	String sectionName;
	long featureData;
	String seqNum;
	boolean published;
	String dataPublishOn;
	String dataExpireOn;

	public String getDataExpireOn() {
		return dataExpireOn;
	}

	public void setDataExpireOn(String dataExpireOn) {
		this.dataExpireOn = dataExpireOn;
	}

	public String getDataPublishOn() {
		return dataPublishOn;
	}

	public void setDataPublishOn(String dataPublishOn) {
		this.dataPublishOn = dataPublishOn;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}

	public long getHomePageId() {
		return homePageId;
	}

	public void setHomePageId(long homePageId) {
		this.homePageId = homePageId;
	}

	public long getFeatureData() {
		return featureData;
	}

	public void setFeatureData(long featureData) {
		this.featureData = featureData;
	}
}