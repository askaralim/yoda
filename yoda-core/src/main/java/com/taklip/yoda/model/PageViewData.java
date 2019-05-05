package com.taklip.yoda.model;

public class PageViewData extends BaseEntity {
	private static final long serialVersionUID = -6503671451587448655L;

	private Long id;

	private Long userId;

	private String username;

	private String userIPAddress;

	private Integer pageType;

	private Long pageId;

	private String pageName;

	private String pageUrl;

	private String operate;

	private String createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserIPAddress() {
		return userIPAddress;
	}

	public void setUserIPAddress(String userIPAddress) {
		this.userIPAddress = userIPAddress;
	}

	public Integer getPageType() {
		return pageType;
	}

	public void setPageType(Integer pageType) {
		this.pageType = pageType;
	}

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "PageViewData{"
					+ "userId:'" + userId + '\'' 
					+ ", username:'" + username + '\''
					+ ", userIPAddress:'" + userIPAddress + '\''
					+ ", pageType:'" + pageType + '\''
					+ ", pageId:'" + pageId + '\''
					+ ", pageName:'" + pageName + '\''
					+ ", pageUrl:'" + pageUrl + '\''
					+ ", createTime:'" + createTime + '\''
					+ ", operate:'" + operate + "'}";
	}
}