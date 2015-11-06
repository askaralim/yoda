package com.yoda.site.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.yoda.BaseEntity;

@Entity
@Table(name = "site")
public class Site extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "site_id")
	private Integer siteId;

	@Column(name = "site_name")
	private String siteName;

	private String title;

	@Column(name = "logo_path")
	private String logoPath;

	@Column(name = "logo_content_type")
	private String logoContentType;

	@Column(name = "active")
	private boolean active;

//	@Column(name = "update_by")
//	private long updateBy;
//
//	@Column(name = "update_date")
//	private Date updateDate;
//
//	@Column(name = "create_by")
//	private long createBy;
//
//	@Column(name = "create_date")
//	private Date createDate;

	@Column(name = "public_port")
	private String publicPort;

	@Column(name = "secure_port")
	private String securePort;

	@Column(name = "domain_name")
	private String domainName;

	@Column(name = "google_analytics_id")
	private String googleAnalyticsId;

	@Column(name = "secure_connection_enabled")
	private boolean secureConnectionEnabled;

	@Column(name = "footer")
	private String footer;

	@Column(name = "listing_page_size")
	private String listingPageSize;

	@Column(name = "section_page_size")
	private String sectionPageSize;

	@Column(name = "theme_id")
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