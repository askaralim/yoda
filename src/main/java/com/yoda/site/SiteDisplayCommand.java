package com.yoda.site;

public class SiteDisplayCommand {
	Long siteId;
	String siteName;
	String publicDomainNamePort;
	String secureDomainNamePort;
	char active;

	public char getActive() {
		return active;
	}

	public void setActive(char active) {
		this.active = active;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getPublicDomainNamePort() {
		return publicDomainNamePort;
	}

	public void setPublicDomainNamePort(String publicDomainNamePort) {
		this.publicDomainNamePort = publicDomainNamePort;
	}

	public String getSecureDomainNamePort() {
		return secureDomainNamePort;
	}

	public void setSecureDomainNamePort(String secureDomainNamePort) {
		this.secureDomainNamePort = secureDomainNamePort;
	}
}
