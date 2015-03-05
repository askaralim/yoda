package com.yoda.section;

public class SectionDisplayCommand {
	boolean remove;
	long sectionId;
	String sectionShortTitle;
	String sectionTitle;
	String sectionDesc;
	String seqNum;
	String published;

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public String getSectionDesc() {
		return sectionDesc;
	}

	public void setSectionDesc(String sectionDesc) {
		this.sectionDesc = sectionDesc;
	}

	public String getSectionTitle() {
		return sectionTitle;
	}

	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}

	public String getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}

	public long getSectionId() {
		return sectionId;
	}

	public void setSectionId(long sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionShortTitle() {
		return sectionShortTitle;
	}

	public void setSectionShortTitle(String sectionShortTitle) {
		this.sectionShortTitle = sectionShortTitle;
	}

	public boolean isRemove() {
		return remove;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}
}
