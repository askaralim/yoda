package com.yoda.section.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Vector;

import org.json.JSONObject;

import com.yoda.section.exception.SectionShortTitleException;
import com.yoda.section.model.DropDownMenu;
import com.yoda.section.model.Section;

public interface SectionService {
	public Section getBySiteId(long siteId);

	public List<Section> getSectionBySiteId_SectionParentId(long siteId, long sectionParentId);

	public List<Section> getSectionBySiteId_SectionParentId_Published(long siteId, long sectionParentId, char pulished);

	JSONObject makeJSONSectionTree(long siteId) throws Exception;

	JSONObject makeJSONSectionTreeNode(long siteId, long sectionId) throws Exception;

	DropDownMenu makeSectionTree(long siteId);

	DropDownMenu[] makeSectionTreeItem(long siteId, long sectionParentId) throws Exception;

	DropDownMenu[] makeDdmSection(long siteId, long sectionParentId) throws Exception;

	void getSectionIdTreeListWorker(long siteId, Vector<Long> list, long parentId) throws Exception;

	Long[] getSectionIdTreeList(long siteId, long parentId) throws Exception;

	String formatSectionName(long siteId, long sectionId) throws Exception;

	Section getSectionBySiteId_SectionId(long siteId, long sectionId);

	Section getSectionBySiteId_NaturalKey(long siteId, String naturalKey);

	void updateSection(Section section);

	public void addSection(long siteId, long userId, long sectionId, String sectionTitle, String sectionShortTitle, String sectionDesc, char published)
		throws UnsupportedEncodingException, SectionShortTitleException;

	public List<Section> getBySectionParentId(long sectionParentId);

	public void cascadeRemoveSection(long siteId, long sectionId);

	public int selectMaxSeqNumBySectionId_SiteId(long siteId, long sectionId);

	public void updateSeqNum(long siteId, long sectionParentId, int seqNum);

	public Section addSection(long siteId, long userId, long sectionParentId, int seqNum, String sectionShortTitle, String sectionTitle, String sectionDesc, char published);

	public List<Section> search(String sql, String sortBy);
}
