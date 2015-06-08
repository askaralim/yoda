//package com.yoda.content;
//
//import com.yoda.section.model.DropDownMenu;
//
//public class ContentListCommand {
//	String title;
//
//	String published;
//
//	String publishDateStart;
//
//	String publishDateEnd;
//
//	String expireDateStart;
//
//	String expireDateEnd;
//
//	String updateBy;
//
//	String createBy;
//
//	String sectionIds[];
//
//	String selectUsers[];
//
//	DropDownMenu sectionTree;
//
//	String selectedSections[];
//
//	ContentDisplayCommand contents[];
//
//	String contentIds[];
//
//	boolean empty;
//
//	int pageCount;
//
//	int startPage;
//
//	int endPage;
//
//	int pageNo;
//
//	String srPageNo;
//
//	public ContentDisplayCommand getContent(int index) {
//		return contents[index];
//	}
//
////	public void reset(ActionMapping mapping,
////			javax.servlet.http.HttpServletRequest request) {
////		srSelectedSections = null;
////		String CONTENTDETAIL = "content.*contentId";
////		int count = 0;
////		Enumeration enumeration = request.getParameterNames();
////		while (enumeration.hasMoreElements()) {
////			String name = (String) enumeration.nextElement();
////			if (name.matches(CONTENTDETAIL)) {
////				count++;
////			}
////		}
////		contents = new ContentDisplayCommand[count];
////		for (int i = 0; i < contents.length; i++) {
////			contents[i] = new ContentDisplayCommand();
////		}
////	}
//
//	public String[] getSelectUsers() {
//		return selectUsers;
//	}
//
//	public void setSelectUsers(String[] selectUsers) {
//		this.selectUsers = selectUsers;
//	}
//
//	public String getExpireDateEnd() {
//		return expireDateEnd;
//	}
//
//	public void setExpireDateEnd(String expireDateEnd) {
//		this.expireDateEnd = expireDateEnd;
//	}
//
//	public String getExpireDateStart() {
//		return expireDateStart;
//	}
//
//	public void setExpireDateStart(String expireDateStart) {
//		this.expireDateStart = expireDateStart;
//	}
//
//	public String getPublishDateEnd() {
//		return publishDateEnd;
//	}
//
//	public void setPublishDateEnd(String publishDateEnd) {
//		this.publishDateEnd = publishDateEnd;
//	}
//
//	public String getPublishDateStart() {
//		return publishDateStart;
//	}
//
//	public void setPublishDateStart(String publishDateStart) {
//		this.publishDateStart = publishDateStart;
//	}
//
//	public String getTitle() {
//		return title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}
//
//	public String getCreateBy() {
//		return createBy;
//	}
//
//	public void setCreateBy(String title) {
//		this.createBy = title;
//	}
//
//	public String[] getSectionIds() {
//		return sectionIds;
//	}
//
//	public void setSectionIds(String[] sectionIds) {
//		this.sectionIds = sectionIds;
//	}
//
//	public String getUpdateBy() {
//		return updateBy;
//	}
//
//	public void setUpdateBy(String updateBy) {
//		this.updateBy = updateBy;
//	}
//
//	public ContentDisplayCommand[] getContents() {
//		return contents;
//	}
//
//	public void setContents(ContentDisplayCommand[] contents) {
//		this.contents = contents;
//	}
//
//	public String[] getContentIds() {
//		return contentIds;
//	}
//
//	public void setContentIds(String[] contentIds) {
//		this.contentIds = contentIds;
//	}
//
//	public String getPublished() {
//		return published;
//	}
//
//	public void setPublished(String published) {
//		this.published = published;
//	}
//
//	public String[] getSelectedSections() {
//		return selectedSections;
//	}
//
//	public void setSelectedSections(String[] selectedSections) {
//		this.selectedSections = selectedSections;
//	}
//
//	public DropDownMenu getSectionTree() {
//		return sectionTree;
//	}
//
//	public void setSectionTree(DropDownMenu sectionTree) {
//		this.sectionTree = sectionTree;
//	}
//
//	public boolean isEmpty() {
//		return empty;
//	}
//
//	public void setEmpty(boolean empty) {
//		this.empty = empty;
//	}
//
//	public String getSrPageNo() {
//		return srPageNo;
//	}
//
//	public void setSrPageNo(String srPageNo) {
//		this.srPageNo = srPageNo;
//	}
//
//	public int getEndPage() {
//		return endPage;
//	}
//
//	public void setEndPage(int endPage) {
//		this.endPage = endPage;
//	}
//
//	public int getPageCount() {
//		return pageCount;
//	}
//
//	public void setPageCount(int pageCount) {
//		this.pageCount = pageCount;
//	}
//
//	public int getPageNo() {
//		return pageNo;
//	}
//
//	public void setPageNo(int pageNo) {
//		this.pageNo = pageNo;
//	}
//
//	public int getStartPage() {
//		return startPage;
//	}
//
//	public void setStartPage(int startPage) {
//		this.startPage = startPage;
//	}
//}