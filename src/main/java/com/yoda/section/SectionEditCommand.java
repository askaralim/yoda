package com.yoda.section;

import com.yoda.section.model.DropDownMenu;

public class SectionEditCommand {
	DropDownMenu sectionTree;
	long sectionId;
	long sectionParentId;
	String sectionTitle;
	String sectionShortTitle;
	String sectionDesc;
	boolean published;
	String mode;
	long createSectionId;
	String createMode;
	SectionDisplayCommand childrenSections[];
	boolean sequence;

	public boolean isSequence() {
		return sequence;
	}

	public void setSequence(boolean sequence) {
		this.sequence = sequence;
	}

	public SectionDisplayCommand[] getChildrenSections() {
		return childrenSections;
	}

	public void setChildrenSections(SectionDisplayCommand[] childrenSections) {
		this.childrenSections = childrenSections;
	}

//	public void reset(ActionMapping mapping, HttpServletRequest request) {
//		String CHILDSECTIONS = "childrenSection.*sectionId";
//		int count = 0;
//		Enumeration enumeration = request.getParameterNames();
//		while (enumeration.hasMoreElements()) {
//			String name = (String) enumeration.nextElement();
//			if (name.matches(CHILDSECTIONS)) {
//				count++;
//			}
//		}
//		childrenSections = new SectionDisplayCommand[count];
//		for (int i = 0; i < childrenSections.length; i++) {
//			childrenSections[i] = new SectionDisplayCommand();
//		}
//	}

	public SectionDisplayCommand getChildrenSection(int index) {
		return childrenSections[index];
	}

	public long getCreateSectionId() {
		return createSectionId;
	}

	public void setCreateSectionId(long createSectionId) {
		this.createSectionId = createSectionId;
	}

	public String getCreateMode() {
		return createMode;
	}

	public void setCreateMode(String createMode) {
		this.createMode = createMode;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public String getSectionDesc() {
		return sectionDesc;
	}

	public void setSectionDesc(String sectionDesc) {
		this.sectionDesc = sectionDesc;
	}

	public long getSectionId() {
		return sectionId;
	}

	public void setSectionId(long sectionId) {
		this.sectionId = sectionId;
	}

	public long getSectionParentId() {
		return sectionParentId;
	}

	public void setSectionParentId(long sectionParentId) {
		this.sectionParentId = sectionParentId;
	}

	public String getSectionTitle() {
		return sectionTitle;
	}

	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}

	public DropDownMenu getSectionTree() {
		return sectionTree;
	}

	public void setSectionTree(DropDownMenu sectionTree) {
		this.sectionTree = sectionTree;
	}

	public String getSectionShortTitle() {
		return sectionShortTitle;
	}

	public void setSectionShortTitle(String sectionShortTitle) {
		this.sectionShortTitle = sectionShortTitle;
	}
}
