package com.yoda.section.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Vector;

import org.json.JSONObject;

import com.yoda.section.exception.SectionShortTitleException;
import com.yoda.section.model.DropDownMenu;
import com.yoda.section.model.Section;

public interface SectionService {
	public Section getBySiteId(int siteId);

	public List<Section> getSectionBySiteId_SectionParentId(int siteId, int parentId);

	public List<Section> getSectionBySiteId_SectionParentId_Published(int siteId, int parentId, boolean published);

	JSONObject makeJSONSectionTree(int siteId) throws Exception;

	JSONObject makeJSONSectionTreeNode(int siteId,  int sectionId) throws Exception;

	DropDownMenu makeSectionTree(int siteId);

	DropDownMenu[] makeSectionTreeItem(int siteId, int parentId) throws Exception;

	DropDownMenu[] makeDdmSection(int siteId, int parentId) throws Exception;

	void getSectionIdTreeListWorker(int siteId, Vector<Integer> list, int parentId) throws Exception;

	Integer[] getSectionIdTreeList(int siteId, int parentId) throws Exception;

	String formatSectionName(int siteId,  int sectionId) throws Exception;

	Section getSectionBySiteId_SectionId(int siteId,  int sectionId);

	Section getSectionBySiteId_NaturalKey(int siteId, String naturalKey);

	void updateSection(Section section);

	public void addSection(int siteId, long userId,  int sectionId, String title, String shortTitle, String description, boolean published)
		throws UnsupportedEncodingException, SectionShortTitleException;

	public List<Section> getBySectionParentId(int parentId);

	public void cascadeRemoveSection(int siteId,  int sectionId);

	public int selectMaxSeqNumBySectionId_SiteId(int siteId,  int sectionId);

	public void updateSeqNum(int siteId, int parentId, int seqNum);

	public Section addSection(int siteId, long userId, int sectionParentId, int seqNum, String shortTitle, String title, String description, boolean published);

	public List<Section> search(String sql, String sortBy);
}
