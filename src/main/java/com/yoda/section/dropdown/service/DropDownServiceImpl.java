package com.yoda.section.dropdown.service;//package com.yoda.ui.dropdown.service;
//
//import java.util.Iterator;
//import java.util.List;
//import java.util.Vector;
//
//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.springframework.stereotype.Service;
//
//import com.yoda.admin.menu.model.Menu;
//import com.yoda.admin.section.model.Section;
//import com.yoda.admin.section.service.SectionServiceUtil;
//import com.yoda.hibernate.HibernateConnection;
//import com.yoda.ui.dropdown.DropDownMenu;
//import com.yoda.ui.dropdown.DropDownMenuContainer;
//import com.yoda.util.Format;
//
//@Service
//public class DropDownServiceImpl implements DropDownService {
//	public DropDownMenuContainer[] makeDdmSectionContainerList(long siteId)
//		throws Exception {
//		DropDownMenuContainer list[] = new DropDownMenuContainer[1];
//		DropDownMenuContainer container = makeDdmSectionContainer(siteId);
//
//		container.setMenuSetName("Home");
//
//		list[0] = container;
//
//		return list;
//	}
//
//	public DropDownMenuContainer makeDdmSectionContainer(long siteId)
//		throws Exception {
//		DropDownMenuContainer container = new DropDownMenuContainer();
//
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		Query query = session.createQuery("from section in class Section where siteId = :siteId and section.sectionParentId is null order by seqNum");
//
//		query.setLong("siteId", siteId);
//
//		Iterator iterator = query.iterate();
//
//		Section section = null;
//
//		if (iterator.hasNext()) {
//			section = (Section) iterator.next();
//		}
//
//		DropDownMenu sections[] = null;
//
//		sections = makeDdmSection(siteId, section.getSectionId());
//		container.setMenuSetKey(section.getSectionId().toString());
//		container.setMenuSetName(section.getSectionTitle());
//		container.setMenuItems(sections);
//
//		return container;
//	}
//
//	public DropDownMenu[] makeDdmSection(
//			long siteId, Long sectionParentId) throws Exception {
////		Session session = HibernateConnection.getInstance().getCurrentSession();
////
////		Query query = session.createQuery("from section in class Section where siteId = :siteId and section.sectionParentId = :sectionParentId order by seqNum");
////
////		query.setLong("siteId", siteId);
////		query.setLong("sectionParentId", sectionParentId.longValue());
//
////		Iterator iterator = query.iterate();
////
////		while (iterator.hasNext()) {
////			Section section = (Section) iterator.next();
////			DropDownMenu childMenus[] = makeDdmSection(siteId, section.getSectionId());
////			DropDownMenu ddm = new DropDownMenu();
////
////			ddm.setMenuKey(Format.getLong(section.getSectionId()));
////			ddm.setMenuName(section.getSectionShortTitle());
////			ddm.setMenuItems(childMenus);
////
////			sectionList.add(ddm);
////		}
//
//		DropDownMenu sections[] = null;
//		Vector<DropDownMenu> sectionList = new Vector<DropDownMenu>();
//
//		List<Section> sectionss = SectionServiceUtil.getSection(siteId, sectionParentId);
//
//		for (Section section : sectionss) {
//			DropDownMenu childMenus[] = makeDdmSection(siteId, section.getSectionId());
//			DropDownMenu ddm = new DropDownMenu();
//
//			ddm.setMenuKey(Format.getLong(section.getSectionId()));
//			ddm.setMenuName(section.getSectionShortTitle());
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
//	public DropDownMenu makeSectionTree(long siteId) throws Exception {
////		Session session = HibernateConnection.getInstance().getCurrentSession();
////
////		Query query = session.createQuery("from section in class Section where siteId = :siteId and section.sectionParentId is null order by seqNum");
////
////		query.setLong("siteId", siteId);
////
////		Iterator iterator = query.iterate();
////
//		Section section = null;
////
////		if (iterator.hasNext()) {
////			section = (Section)iterator.next();
////		}
//
//		section = SectionServiceUtil.getBySiteId(siteId);
//
//		DropDownMenu sections[] = null;
//
//		sections = makeSectionTreeItem(siteId, section.getSectionId());
//
//		DropDownMenu ddm = new DropDownMenu();
//
//		ddm.setMenuKey(section.getSectionId().toString());
//		ddm.setMenuName(section.getSectionShortTitle());
//		ddm.setMenuItems(sections);
//
//		return ddm;
//	}
//
//	public DropDownMenu[] makeSectionTreeItem(
//			long siteId, Long sectionParentId)
//		throws Exception {
//		DropDownMenu sections[] = null;
//		Vector<DropDownMenu> sectionList = new Vector<DropDownMenu>();
//
////		Session session = HibernateConnection.getInstance().getCurrentSession();
////
////		Query query = session.createQuery("from section in class Section where siteId = :siteId and section.sectionParentId = :sectionParentId order by seqNum");
////
////		query.setLong("siteId", siteId);
////		query.setLong("sectionParentId", sectionParentId.longValue());
////
////		Iterator iterator = query.iterate();
//
////		while (iterator.hasNext()) {
////			Section section = (Section) iterator.next();
////			DropDownMenu childMenus[] = makeDdmSection(
////				siteId, section.getSectionId());
////
////			DropDownMenu ddm = new DropDownMenu();
////
////			ddm.setMenuKey(Format.getLong(section.getSectionId()));
////			ddm.setMenuName(section.getSectionShortTitle());
////			ddm.setMenuItems(childMenus);
////
////			sectionList.add(ddm);
////		}
//
//		List<Section> sectionss = SectionServiceUtil.getSection(siteId, sectionParentId);
//
//		for (Section section : sectionss) {
//			DropDownMenu childMenus[] = makeDdmSection(
//				siteId, section.getSectionId());
//
//			DropDownMenu ddm = new DropDownMenu();
//
//			ddm.setMenuKey(Format.getLong(section.getSectionId()));
//			ddm.setMenuName(section.getSectionShortTitle());
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
//	public DropDownMenuContainer[] makeDdmMenuContainerList(String siteId)
//			throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		Query query = session.createQuery("from menu in class Menu where menu.siteId = :siteId and menuParentId is null order by seqNum");
//		query.setString("siteId", siteId);
//		Iterator iterator = query.iterate();
//		Vector<DropDownMenuContainer> menuVector = new Vector<DropDownMenuContainer>();
//		while (iterator.hasNext()) {
//			Menu menu = (Menu) iterator.next();
//			DropDownMenuContainer container = makeDdmMenuContainer(siteId,
//					menu.getMenuSetName());
//			container.setMenuSetName(menu.getMenuSetName());
//			container.setMenuSetKey(menu.getMenuId().toString());
//			menuVector.add(container);
//		}
//		DropDownMenuContainer ddmMenuContainerList[] = new DropDownMenuContainer[menuVector
//				.size()];
//		menuVector.copyInto(ddmMenuContainerList);
//		return ddmMenuContainerList;
//	}
//
//	public DropDownMenuContainer makeDdmMenuContainer(
//			String siteId, String menuSetName) throws Exception {
//		DropDownMenuContainer container = new DropDownMenuContainer();
//
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//
//		Query query = session.createQuery("from menu in class Menu where siteId = :siteId and menu.menuSetName = :menuSetName and menu.menuParentId is null order by seqNum ");
//
//		query.setString("siteId", siteId);
//		query.setString("menuSetName", menuSetName);
//
//		Iterator iterator = query.iterate();
//
//		Menu menu = null;
//
//		if (iterator.hasNext()) {
//			menu = (Menu) iterator.next();
//		}
//
//		DropDownMenu menus[] = makeDdmMenu(siteId, menuSetName, menu.getMenuId());
//
//		container.setMenuItems(menus);
//
//		container.setMenuItems(menus);
//
//		return container;
//	}
//
//	public DropDownMenu[] makeDdmMenu(
//			String siteId, String menuSetName, Long menuParentId)
//		throws Exception {
//		DropDownMenu menus[] = null;
//
//		Vector<DropDownMenu> menuList = new Vector<DropDownMenu>();
//
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//
//		Query query = session.createQuery("from menu in class Menu where siteId = :siteId and menu.menuSetName = :menuSetName and menu.menuParentId = :menuParentId order by seqNum");
//
//		query.setString("siteId", siteId);
//		query.setString("menuSetName", menuSetName);
//		query.setLong("menuParentId", menuParentId.longValue());
//
//		Iterator iterator = query.iterate();
//
//		while (iterator.hasNext()) {
//			Menu menu = (Menu) iterator.next();
//			DropDownMenu ddm = new DropDownMenu();
//
//			ddm.setMenuKey(Format.getLong(menu.getMenuId()));
//			ddm.setMenuName(menu.getMenuName());
//
//			if (menu.getMenuId() != null) {
//				DropDownMenu childMenus[] = makeDdmMenu(siteId, menuSetName, menu.getMenuId());
//
//				ddm.setMenuItems(childMenus);
//			}
//
//			menuList.add(ddm);
//		}
//
//		menus = new DropDownMenu[menuList.size()];
//		menuList.copyInto(menus);
//
//		return menus;
//	}
//}
