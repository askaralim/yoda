package com.yoda.site;

import java.util.List;

public class SiteListCommand {
//	String mode;

	long siteId;

	String siteName;

	String active;

	List<SiteDisplayCommand> sites;

	long siteIds[];

//	public String getMode() {
//		return mode;
//	}
//
//	public void setMode(String mode) {
//		this.mode = mode;
//	}

	public long[] getSiteIds() {
		return siteIds;
	}

	public void setSiteIds(long[] siteIds) {
		this.siteIds = siteIds;
	}

	public List<SiteDisplayCommand> getSites() {
		return sites;
	}

	public void setSites(List<SiteDisplayCommand> sites) {
		this.sites = sites;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
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
