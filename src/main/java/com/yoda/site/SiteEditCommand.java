package com.yoda.site;

public class SiteEditCommand {
	Long siteId;
	String siteName;
	String publicPort;
	String securePort;
	String domainName;
	String googleAnalyticsId;
	String logoContentType;
	Character active;
	String footer;
	boolean secureConnectionEnabled;
	String listingPageSize;
	String sectionPageSize;
	String tabIndex;
	Long themeId;

	public Character getActive() {
		return active;
	}

	public void setActive(Character active) {
		this.active = active;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getLogoContentType() {
		return logoContentType;
	}

	public void setLogoContentType(String logoContentType) {
		this.logoContentType = logoContentType;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String templateFooter) {
		this.footer = templateFooter;
	}

	public String getListingPageSize() {
		return listingPageSize;
	}

	public void setListingPageSize(String listingPageSize) {
		this.listingPageSize = listingPageSize;
	}

	public String getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(String tabIndex) {
		this.tabIndex = tabIndex;
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

	public void setSecureConnectionEnabled(boolean secureConnectionEnabled) {
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

	public Long getThemeId() {
		return themeId;
	}

	public void setThemeId(Long themeId) {
		this.themeId = themeId;
	}

	public boolean isNew() {
		return (siteId == null);
	}
}