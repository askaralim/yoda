package com.yoda.item;

import com.yoda.section.model.DropDownMenu;

public class ItemListCommand {
	String srItemShortDesc;
	String srItemShortDesc1;
	String srItemNum;
	String srItemUpcCd;
	String srPublished;
	String srItemPublishOnStart;
	String srItemPublishOnEnd;
	String srItemExpireOnStart;
	String srItemExpireOnEnd;
	String srUpdateBy;
	String srCreateBy;
	String srSectionIds[];
	String srSelectUsers[];
	DropDownMenu srSectionTree;
	String srSelectedSections[];

	ItemDisplayCommand items[];
	String itemIds[];

	boolean empty;

	int pageCount;

	int startPage;

	int endPage;

	int pageNo;

	String srPageNo;

	public ItemDisplayCommand getItem(int index) {
		return items[index];
	}

//	public void reset(
//			ActionMapping mapping,
//			javax.servlet.http.HttpServletRequest request) {
//		srSelectedSections = null;
//		String ITEMDETAIL = "item.*itemId";
//		int count = 0;
//		Enumeration enumeration = request.getParameterNames();
//		while (enumeration.hasMoreElements()) {
//			String name = (String) enumeration.nextElement();
//			if (name.matches(ITEMDETAIL)) {
//				count++;
//			}
//		}
//		items = new ItemDisplayCommand[count];
//		for (int i = 0; i < items.length; i++) {
//			items[i] = new ItemDisplayCommand();
//		}
//	}

	public String getSrCreateBy() {
		return srCreateBy;
	}

	public void setSrCreateBy(String srCreateBy) {
		this.srCreateBy = srCreateBy;
	}

	public DropDownMenu getSrSectionTree() {
		return srSectionTree;
	}

	public void setSrSectionTree(DropDownMenu srSectionTree) {
		this.srSectionTree = srSectionTree;
	}

	public String getSrItemExpireOnEnd() {
		return srItemExpireOnEnd;
	}

	public void setSrItemExpireOnEnd(String srItemExpireOnEnd) {
		this.srItemExpireOnEnd = srItemExpireOnEnd;
	}

	public String getSrItemExpireOnStart() {
		return srItemExpireOnStart;
	}

	public void setSrItemExpireOnStart(String srItemExpireOnStart) {
		this.srItemExpireOnStart = srItemExpireOnStart;
	}

	public String getSrItemNum() {
		return srItemNum;
	}

	public void setSrItemNum(String srItemNum) {
		this.srItemNum = srItemNum;
	}

	public String getSrItemPublishOnEnd() {
		return srItemPublishOnEnd;
	}

	public void setSrItemPublishOnEnd(String srItemPublishOnEnd) {
		this.srItemPublishOnEnd = srItemPublishOnEnd;
	}

	public String getSrItemPublishOnStart() {
		return srItemPublishOnStart;
	}

	public void setSrItemPublishOnStart(String srItemPublishOnStart) {
		this.srItemPublishOnStart = srItemPublishOnStart;
	}

	public String getSrItemShortDesc() {
		return srItemShortDesc;
	}

	public void setSrItemShortDesc(String srItemShortDesc) {
		this.srItemShortDesc = srItemShortDesc;
	}

	public String getSrItemShortDesc1() {
		return srItemShortDesc1;
	}

	public void setSrItemShortDesc1(String srItemShortDesc1) {
		this.srItemShortDesc1 = srItemShortDesc1;
	}

	public String getSrItemUpcCd() {
		return srItemUpcCd;
	}

	public void setSrItemUpcCd(String srItemUpcCd) {
		this.srItemUpcCd = srItemUpcCd;
	}

	public String[] getSrSectionIds() {
		return srSectionIds;
	}

	public void setSrSectionIds(String[] srSectionIds) {
		this.srSectionIds = srSectionIds;
	}

	public String[] getSrSelectedSections() {
		return srSelectedSections;
	}

	public void setSrSelectedSections(String[] srSelectedSections) {
		this.srSelectedSections = srSelectedSections;
	}

	public String[] getSrSelectUsers() {
		return srSelectUsers;
	}

	public void setSrSelectUsers(String[] srSelectUsers) {
		this.srSelectUsers = srSelectUsers;
	}

	public String getSrUpdateBy() {
		return srUpdateBy;
	}

	public void setSrUpdateBy(String srUpdateBy) {
		this.srUpdateBy = srUpdateBy;
	}

	public String getSrPublished() {
		return srPublished;
	}

	public void setSrPublished(String srPublished) {
		this.srPublished = srPublished;
	}

	public ItemDisplayCommand[] getItems() {
		return items;
	}

	public void setItems(ItemDisplayCommand[] items) {
		this.items = items;
	}

	public String[] getItemIds() {
		return itemIds;
	}

	public void setItemIds(String[] itemIds) {
		this.itemIds = itemIds;
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
