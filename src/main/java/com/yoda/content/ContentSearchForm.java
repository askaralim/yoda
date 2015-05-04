package com.yoda.content;

import com.yoda.util.StringPool;

public class ContentSearchForm {
	private String title;

	private Boolean published;

	private String publishDateStart;

	private String publishDateEnd;

	private String expireDateStart;

	private String expireDateEnd;

	public ContentSearchForm() {
		super();

		this.title = StringPool.BLANK;
		this.published = null;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getPublished() {
		return published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}

	public String getPublishDateStart() {
		return publishDateStart;
	}

	public void setPublishDateStart(String publishDateStart) {
		this.publishDateStart = publishDateStart;
	}

	public String getPublishDateEnd() {
		return publishDateEnd;
	}

	public void setPublishDateEnd(String publishDateEnd) {
		this.publishDateEnd = publishDateEnd;
	}

	public String getExpireDateStart() {
		return expireDateStart;
	}

	public void setExpireDateStart(String expireDateStart) {
		this.expireDateStart = expireDateStart;
	}

	public String getExpireDateEnd() {
		return expireDateEnd;
	}

	public void setExpireDateEnd(String expireDateEnd) {
		this.expireDateEnd = expireDateEnd;
	}
}