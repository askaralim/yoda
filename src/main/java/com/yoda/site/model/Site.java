package com.yoda.site.model;

import com.yoda.BaseEntity;

public class Site extends BaseEntity {
	private Integer siteId;

	private String siteName;

	private String title;

	private String logoPath;

	private String logoContentType;

	private boolean active;

	private String publicPort;

	private String securePort;

	private String domainName;

	private String googleAnalyticsId;

	private boolean secureConnectionEnabled;

	private String footer;

	private String listingPageSize;

	private String sectionPageSize;

	private Integer themeId;

	public Site() {
		this.active = true;
	}

	public Integer getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return this.siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLogoPath() {
		return this.logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getLogoContentType() {
		return this.logoContentType;
	}

	public void setLogoContentType(String siteContentType) {
		this.logoContentType = siteContentType;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

//	public long getUpdateBy() {
//		return this.updateBy;
//	}
//
//	public void setUpdateBy(Long updateBy) {
//		this.updateBy = updateBy;
//	}
//
//	public Date getUpdateDate() {
//		return this.updateDate;
//	}
//
//	public void setUpdateDate(Date updateDate) {
//		this.updateDate = updateDate;
//	}
//
//	public long getCreateBy() {
//		return this.createBy;
//	}
//
//	public void setCreateBy(long createBy) {
//		this.createBy = createBy;
//	}
//
//	public Date getCreateDate() {
//		return this.createDate;
//	}
//
//	public void setCreateDate(Date createDate) {
//		this.createDate = createDate;
//	}

	public String getListingPageSize() {
		return listingPageSize;
	}

	public void setListingPageSize(String listingPageSize) {
		this.listingPageSize = listingPageSize;
	}

	public String getPublicPort() {
		return publicPort;
	}

	public void setPublicPort(String publicPort) {
		this.publicPort = publicPort;
	}

	public String getSecurePort() {
		return securePort;
	}

	public void setSecurePort(String securePort) {
		this.securePort = securePort;
	}

	public boolean isSecureConnectionEnabled() {
		return secureConnectionEnabled;
	}

	public void setSecureConnectionEnabled(
			boolean secureConnectionEnabled) {
		this.secureConnectionEnabled = secureConnectionEnabled;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getGoogleAnalyticsId() {
		return googleAnalyticsId;
	}

	public void setGoogleAnalyticsId(String googleAnalyticsId) {
		this.googleAnalyticsId = googleAnalyticsId;
	}

	public String getSectionPageSize() {
		return sectionPageSize;
	}

	public void setSectionPageSize(String sectionPageSize) {
		this.sectionPageSize = sectionPageSize;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String Footer) {
		this.footer = Footer;
	}

	public Integer getThemeId() {
		return themeId;
	}

	public void setThemeId(Integer themeId) {
		this.themeId = themeId;
	}

	public boolean isNew() {
		return (this.siteId == null);
	}
}