//package com.yoda.section.service.impl;
//
//import java.io.UnsupportedEncodingException;
//import java.util.Date;
//import java.util.List;
//import java.util.Vector;
//
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.yoda.content.model.Content;
//import com.yoda.content.service.ContentService;
//import com.yoda.item.model.Item;
//import com.yoda.item.service.ItemService;
//import com.yoda.kernal.util.PortalUtil;
//import com.yoda.menu.model.Menu;
//import com.yoda.menu.service.MenuService;
//import com.yoda.section.dao.SectionDAO;
//import com.yoda.section.exception.SectionShortTitleException;
//import com.yoda.section.model.DropDownMenu;
//import com.yoda.section.model.Section;
//import com.yoda.section.service.SectionService;
//import com.yoda.util.Utility;
//
//@Service
//public class SectionServiceImpl implements SectionService {
//	@Autowired
//	private SectionDAO sectionDAO;
//
//	@Autowired
//	private ContentService contentService;
//
//	@Autowired
//	private ItemService itemService;
//
//	@Autowired
//	private MenuService menuService;
//
//	public Section addSection(
//			int siteId, int parentId,
//			int seqNum, String shortTitle, String title,
//			String description, boolean published) {
//		Section section = new Section();
//
//		section.setSiteId(siteId);
//		section.setParentId(parentId);
//		section.setSeqNum(seqNum);
//		section.setShortTitle(shortTitle);
//		section.setTitle(title);
//		section.setDescription(description);
//		section.setPublished(published);
//		section.setUpdateBy(PortalUtil.getAuthenticatedUser());
//		section.setCreateBy(PortalUtil.getAuthenticatedUser());
//		section.setUpdateDate(new Date(System.currentTimeMillis()));
//		section.setCreateDate(new Date(System.currentTimeMillis()));
//		section.setNaturalKey(getFullSectionPath(section, siteId));
//
//		sectionDAO.save(section);
//
//		return section;
//	}
//
//	public void addSection(
//			int siteId, int sectionId, String title,
//			String shortTitle, String description, boolean published)
//		throws UnsupportedEncodingException, SectionShortTitleException {
//		boolean update = false;
//
//		Section section = sectionDAO.getSectionBySiteId_SectionId(siteId, sectionId);
//
//		section.setShortTitle(shortTitle);
//
//		String fullSectionPath = getFullSectionPath(section, siteId);
//
//		fullSectionPath = Utility.encode(fullSectionPath);
//
//		if (fullSectionPath.length() > 255) {
//			throw new SectionShortTitleException();
//		}
//
//		update = false;
//
//		int length = updateChildrenSectionPath(section, siteId, update);
//
//		if (length > 255) {
//			throw new SectionShortTitleException();
//		}
//
//		section.setTitle(title);
//		section.setDescription(description);
//		section.setPublished(published);
//		section.setUpdateBy(PortalUtil.getAuthenticatedUser());
//		section.setUpdateDate(new Date(System.currentTimeMillis()));
//
//		section.setNaturalKey(fullSectionPath);
//
//		update = true;
//
//		updateChildrenSectionPath(section, siteId, update);
//
//		sectionDAO.update(section);
//	}
//
//	public int selectMaxSeqNumBySectionId_SiteId(int siteId, int sectionId) {
//		return sectionDAO.selectMaxSeqNumBySiteIdParentMenuId(siteId, sectionId);
//	}
//
//	public List<Section> getBySectionParentId(int parentId) {
//		return sectionDAO.getSectionBySectionParentId(parentId);
//	}
//
//	public List<Section> getSectionBySiteId_SectionParentId_Published(
//			int siteId, int parentId, boolean pulished) {
//		return sectionDAO.getBySI_SPI_Published(siteId, parentId, pulished);
//	}
//
//	public Section getBySiteId(int siteId) {
//		return sectionDAO.getSectionBySiteId(siteId);
//	}
//
//	public List<Section> getSectionBySiteId_SectionParentId(int siteId, int parentId) {
//		return (List<Section>)sectionDAO.getBySI_SPI(siteId, parentId);
//	}
//
//	public Section getSectionBySiteId_SectionId(int siteId, int sectionId) {
//		return sectionDAO.getSectionBySiteId_SectionId(siteId, sectionId);
//	}
//
//	public void updateSection(Section section) {
//		sectionDAO.update(section);
//	}
//
//	public void updateSeqNum(int siteId, int parentId, int seqNum) {
//		sectionDAO.updateSeqNum(siteId, parentId, seqNum);
//	}
//
//	public void cascadeRemoveSection(int siteId, int sectionId) {
//		List<Section> sections = getBySectionParentId(sectionId);
//
//		for (Section childSection : sections) {
//			cascadeRemoveSection(childSection.getSectionId(), siteId);
//		}
//
//		Section section = getSectionBySiteId_SectionId(siteId, sectionId);
//
//		for (Content content : section.getContents()) {
////			content.setSection(null);
//
//			contentService.updateContent(content);
//		}
//
////		for (Item item : section.getItems()) {
////			item.setSection(null);
//
////			itemService.updateItem(item);
////		}
//
//		for (Menu menu : section.getMenus()) {
//			menu.setSection(null);
//
//			menuService.updateMenu(menu);
//		}
//
//		sectionDAO.delete(section);
//	}
//
//	public JSONObject makeJSONSectionTree(int siteId)
//			throws Exception {
////		Session session = HibernateConnection.getInstance().getCurrentSession();
//		JSONObject object = new JSONObject();
////		String sql = "from section in class Section where siteId = :siteId and section.sectionParentId = null";
////		Query query = session.createQuery(sql);
//
////		query.setString("siteId", siteId);
//
////		Iterator iterator = query.iterate();
//
//		Section section = sectionDAO.getSectionBySiteId(siteId);
////		if (iterator.hasNext()) {
////			Section section = (Section) iterator.next();
//		if (section != null) {
//			return makeJSONSectionTreeNode(siteId, section.getSectionId());
//		}
////		}
//
//		return object;
//	}
//
//	public JSONObject makeJSONSectionTreeNode(int siteId,int sectionId)
//			throws Exception {
//		JSONObject jsonObject = new JSONObject();
//
//		Section section = sectionDAO.load(siteId, sectionId);
//
//		jsonObject.put("sectionId", section.getSectionId());
//		jsonObject.put("sectionShortTitle", section.getShortTitle());
//
//		List<Section> sections = sectionDAO.getBySI_SPI(siteId, sectionId);
//
//		Vector<JSONObject> vector = new Vector<JSONObject>();
//
//		for (Section child : sections) {
//			JSONObject object = makeJSONSectionTreeNode(siteId, child.getSectionId());
//			vector.add(object);
//		}
//
//		jsonObject.put("sections", vector);
//
//		return jsonObject;
//	}
//
//	public DropDownMenu makeSectionTree(int siteId) {
//		Section section = null;
//
//		section = sectionDAO.getSectionBySiteId(siteId);
//
//		DropDownMenu sections[] = null;
//
//		sections = makeSectionTreeItem(siteId, section.getSectionId());
//
//		DropDownMenu ddm = new DropDownMenu();
//
//		ddm.setMenuKey(section.getSectionId());
//		ddm.setMenuName(section.getShortTitle());
//		ddm.setMenuItems(sections);
//
//		return ddm;
//	}
//
//	public DropDownMenu[] makeSectionTreeItem(int siteId, int parentId) {
//		DropDownMenu sections[] = null;
//		Vector<DropDownMenu> sectionList = new Vector<DropDownMenu>();
//
//		List<Section> sectionss = getSectionBySiteId_SectionParentId(siteId, parentId);
//
//		for (Section section : sectionss) {
//			DropDownMenu childMenus[] = makeDdmSection(
//				siteId, section.getSectionId());
//
//			DropDownMenu ddm = new DropDownMenu();
//
//			ddm.setMenuKey(section.getSectionId());
//			ddm.setMenuName(section.getShortTitle());
//			ddm.setMenuItems(childMenus);
//
//			sectionList.add(ddm);
//		}
//
//		sections = new DropDownMenu[sectionList.size()];
//		sectionList.copyInto(sections);
//		return sections;
//	}
//
//	public DropDownMenu[] makeDdmSection(int siteId, int parentId) {
//		DropDownMenu sections[] = null;
//		Vector<DropDownMenu> sectionList = new Vector<DropDownMenu>();
//
//		List<Section> sectionss = getSectionBySiteId_SectionParentId(siteId, parentId);
//
//		for (Section section : sectionss) {
//			DropDownMenu childMenus[] = makeDdmSection(
//				siteId, section.getSectionId());
//			DropDownMenu ddm = new DropDownMenu();
//
//			ddm.setMenuKey(section.getSectionId());
//			ddm.setMenuName(section.getShortTitle());
//			ddm.setMenuItems(childMenus);
//
//			sectionList.add(ddm);
//		}
//
//		sections = new DropDownMenu[sectionList.size()];
//		sectionList.copyInto(sections);
//
//		return sections;
//	}
//
//	public Integer[] getSectionIdTreeList(int siteId, int parentId)
//			throws Exception {
//		Vector<Integer> list = new Vector<Integer>();
//
//		getSectionIdTreeListWorker(siteId, list, parentId);
//
//		Integer sectionIdList[] = new Integer[list.size()];
//
//		list.copyInto(sectionIdList);
//
//		return sectionIdList;
//	}
//
//	public void getSectionIdTreeListWorker(
//			int siteId, Vector<Integer> list, int parentId) throws Exception {
////		Session session = HibernateConnection.getInstance().getCurrentSession();
////		Query query = session
////				.createQuery("from section in class Section where siteId = :siteId and section.sectionParentId = :sectionParentId order by seqNum");
////		query.setString("siteId", siteId);
////		query.setLong("sectionParentId", parentId.longValue());
//
//		List<Section> sections = getSectionBySiteId_SectionParentId(siteId, parentId);
//
//		list.add(parentId);
//
////		Iterator iterator = query.iterate();
////		while (iterator.hasNext()) {
////			Section section = (Section) iterator.next();
////			list.add(section.getSectionId());
////			getSectionIdTreeListWorker(siteId, list, section.getSectionId());
////		}
//
//		for(Section section : sections) {
//			list.add(section.getSectionId());
//			getSectionIdTreeListWorker(siteId, list, section.getSectionId());
//		}
//	}
//
//	public String formatSectionName(int siteId, int sectionId) throws SecurityException, Exception {
//		String sectionString = "";
//
//		while (true) {
//			Section section = new Section();
//
//			section = sectionDAO.load(siteId, sectionId);
//
//			sectionId = section.getParentId();
//
//			if (sectionId == 0) {
//				break;
//			}
//
//			if (sectionString.length() > 0) {
//				sectionString = " - " + sectionString;
//			}
//
//			sectionString = section.getShortTitle() + sectionString;
//		}
//
//		return sectionString;
//	}
//
//	public String getFullSectionPath(Section section, int siteId) {
//		String path = section.getShortTitle();
//
//		int sectionParentId = section.getParentId();
//
//		while (sectionParentId != 0) {
//			Section parent = getSectionBySiteId_SectionId(siteId, sectionParentId);
//
//			path = parent.getShortTitle() + " " + path;
//
//			sectionParentId = parent.getParentId();
//		}
//
//		return path;
//	}
//
//	public int updateChildrenSectionPath(
//			Section section, int siteId, boolean update) throws UnsupportedEncodingException {
//		List<Section> sections = getSectionBySiteId_SectionParentId(siteId, section.getSectionId());
//
//		int max = 0;
//
//		for (Section child : sections) {
//			String fullSectionPath = Utility.encode(getFullSectionPath(child, siteId));
//
//			if (fullSectionPath.length() > max) {
//				max = fullSectionPath.length();
//			}
//
//			if (update) {
//				child.setNaturalKey(fullSectionPath);
//				updateSection(child);
////				session.save(child);
//			}
//
//			int length = updateChildrenSectionPath(child, siteId, update);
//
//			if (length > max) {
//				max = length;
//			}
//		}
//
//		return max;
//	}
//
//	public Section getSectionBySiteId_NaturalKey(int siteId, String naturalKey) {
//		return sectionDAO.getSectionBySiteId_NaturalKey(siteId, naturalKey);
//	}
//
//	public List<Section> search(String sql, String sortBy) {
//		return sectionDAO.search(sql, sortBy);
//	}
//}