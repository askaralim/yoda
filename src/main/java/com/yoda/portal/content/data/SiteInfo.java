package com.yoda.portal.content.data;

public class SiteInfo extends DataInfo {
	long siteId;
	String siteName;
	String siteDomainName;
	String googleAnalyticsId;
	String publicURLPrefix;
	String secureURLPrefix;
	String siteFooter;

	public String getPublicURLPrefix() {
		return publicURLPrefix;
	}

	public void setPublicURLPrefix(String publicURLPrefix) {
		this.publicURLPrefix = publicURLPrefix;
	}

	public String getSecureURLPrefix() {
		return secureURLPrefix;
	}

	public void setSecureURLPrefix(String secureURLPrefix) {
		this.secureURLPrefix = secureURLPrefix;
	}

	public String getSiteDomainName() {
		return siteDomainName;
	}

	public void setSiteDomainName(String siteDomainName) {
		this.siteDomainName = siteDomainName;
	}

	public String getGoogleAnalyticsId() {
		return googleAnalyticsId;
	}

	public void setGoogleAnalyticsId(String googleAnalyticsId) {
		this.googleAnalyticsId = googleAnalyticsId;
	}

	public String getSiteFooter() {
		return siteFooter;
	}

	public void setSiteFooter(String siteFooter) {
		this.siteFooter = siteFooter;
	}

	public long getSiteId() {
		return siteId;
	}

	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
}