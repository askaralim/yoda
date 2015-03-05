package com.yoda.portal.content.data;

import java.util.HashSet;
import java.util.Set;

import com.yoda.item.model.Item;

public class ContentInfo extends DataInfo {
	String infoType = "content";

	boolean feature;

	String naturalKey;

	Long contentId;

	String title;

	String shortDescription;

	String description;

	String pageTitle;

	String keywords;

	String contentUrl;

	String defaultImageUrl;

	String updateBy;

	String updateDate;

	String createBy;

	String createDate;

	Long hitCounter;

	long score;

	private Set<Item> items = new HashSet<Item>();

//	Vector contentImageUrls;

	public Long getHitCounter() {
		return hitCounter;
	}

	public void setHitCounter(Long hitCounter) {
		this.hitCounter = hitCounter;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public String getDefaultImageUrl() {
		return defaultImageUrl;
	}

	public void setDefaultImageUrl(String defaultImageUrl) {
		this.defaultImageUrl = defaultImageUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

//	public Vector getContentImageUrls() {
//		return contentImageUrls;
//	}
//
//	public void setContentImageUrls(Vector contentImageUrls) {
//		this.contentImageUrls = contentImageUrls;
//	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public boolean isFeature() {
		return feature;
	}

	public void setFeature(boolean feature) {
		this.feature = feature;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getNaturalKey() {
		return naturalKey;
	}

	public void setNaturalKey(String naturalKey) {
		this.naturalKey = naturalKey;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}
}