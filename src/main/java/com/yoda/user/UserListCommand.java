package com.yoda.user;

public class UserListCommand {
	long userId;
	String userName;
	String type;
	String active;
	UserDisplayCommand users[];
	int pageCount;
	int startPage;
	int endPage;
	int pageNo;
	String srPageNo;
	boolean empty;

	public UserDisplayCommand getUser(int index) {
		return users[index];
	}

//	public void reset(ActionMapping mapping, HttpServletRequest request) {
//		String USER = "user.*userId";
//		int count = 0;
//		Enumeration enumeration = request.getParameterNames();
//		while (enumeration.hasMoreElements()) {
//			String name = (String) enumeration.nextElement();
//			if (name.matches(USER)) {
//				count++;
//			}
//		}
//		users = new UserDisplayForm[count];
//		for (int i = 0; i < users.length; i++) {
//			users[i] = new UserDisplayForm();
//		}
//	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public UserDisplayCommand[] getUsers() {
		return users;
	}

	public void setUsers(UserDisplayCommand[] users) {
		this.users = users;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	public String getSrPageNo() {
		return srPageNo;
	}

	public void setSrPageNo(String srPageNo) {
		this.srPageNo = srPageNo;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
}
