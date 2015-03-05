package com.yoda.site.model;

import java.util.Date;

import com.yoda.BaseEntity;

public class Site extends BaseEntity {

	private Long siteId;

	private String siteName;

	private byte[] logoValue;

	private String logoContentType;

	private Character active;

	private Long updateBy;

	private Date updateDate;

	private Long createBy;

	private Date createDate;

	private String publicPort;

	private String securePort;

	private String domainName;

	private String googleAnalyticsId;

	private boolean secureConnectionEnabled;

	private String footer;

	private String listingPageSize;

	private String sectionPageSize;

	private Long themeId;

	public Site() {
	}

//	public Site(
//			long siteId, String siteName, Character active,
//			String recUpdateBy, Date recUpdateDatetime, String recCreateBy,
//			Date recCreateDatetime) {
//		this.siteId = siteId;
//		this.siteName = siteName;
//		this.active = active;
//		this.recUpdateBy = recUpdateBy;
//		this.recUpdateDatetime = recUpdateDatetime;
//		this.recCreateBy = recCreateBy;
//		this.recCreateDatetime = recCreateDatetime;
//	}
//
//	public Site(
//			long siteId, String siteName, byte[] siteLogoValue,
//			String siteLogoContentType, Character active, String recUpdateBy,
//			Date recUpdateDatetime, String recCreateBy, Date recCreateDatetime,
//			Set<SiteParam> siteParams, Set<User> users,
//			Set<SiteDomain> domains) {
//		this.siteId = siteId;
//		this.siteName = siteName;
//		this.siteLogoValue = siteLogoValue;
//		this.siteLogoContentType = siteLogoContentType;
//		this.active = active;
//		this.recUpdateBy = recUpdateBy;
//		this.recUpdateDatetime = recUpdateDatetime;
//		this.recCreateBy = recCreateBy;
//		this.recCreateDatetime = recCreateDatetime;
//		this.siteParams = siteParams;
//		this.users = users;
//		this.domains = domains;
//	}

	public Long getSiteId() {
		return this.siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return this.siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public byte[] getLogoValue() {
		return this.logoValue;
	}

	public void setLogoValue(byte[] logoValue) {
		this.logoValue = logoValue;
	}

	public String getLogoContentType() {
		return this.logoContentType;
	}

	public void setLogoContentType(String siteContentType) {
		this.logoContentType = siteContentType;
	}

	public Character getActive() {
		return this.active;
	}

	public void setActive(Character active) {
		this.active = active;
	}

	public long getUpdateBy() {
		return this.updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public long getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(long createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

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

	public Long getThemeId() {
		return themeId;
	}

	public void setThemeId(Long themeId) {
		this.themeId = themeId;
	}
}