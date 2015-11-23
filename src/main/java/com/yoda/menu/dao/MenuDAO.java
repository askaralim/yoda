package com.yoda.menu.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.yoda.BaseDAO;
import com.yoda.menu.model.Menu;

@Repository
public class MenuDAO extends BaseDAO<Menu>{

	private static final String GET_MENU_BY_SITEID = "from Menu menu where siteId = ? and parentId = 0 order by seqNum";

	private static final String GET_MENU_BY_SITEID_MENUNAME = "from Menu menu where siteId = ? and name = ?";

//	
	private static final String GET_MENU_BY_SITEID_MENUSETNAME = "from Menu menu where siteId = ? and setName = ? and parentId = 0 order by seqNum";

	private static final String GET_MENU_BY_SITEID_MENUSETNAME_ORDERBY_MENUSETNAME = "from Menu menu where siteId = ? and menu.parentId = 0 order by setName";

	private static final String GET_MENU_BY_SITEID_AND_MENUPARENTID = "from Menu menu where siteId = ? and parentId = ? order by seqNum";

	private static final String GET_MENU_BY_SITEID_MENUSETNAME_MENUPARENTID = "from Menu menu where siteId = ? and setName = ? and parentId = ? order by seqNum";

	private static final String GET_MENU_BY_SITEID_MENUPARENTID = "from Menu menu where siteId = ? and menu.parentId = ? order by seqNum";

	private static final String GET_MENU_BY_MENUPARENTID  = "from Menu where parentId = ?";

	private static final String DELETE_MENU_BY_SITEID_MENUSETNAME = "delete from Menu where siteId = ? and setName = ?";

	private static final String DELETE_MENU_BY_SITEID_MENUSETID = "delete from Menu where siteId = ? and menuId = ?";

	private static final String SELECT_MAX_SEQNUM_BY_SITEID_AND_MENUPARENTID = "select max(seqNum) from Menu where  siteId = ? and parentId = ?";

	private static final String UPDATE_MENU_SEQNUM = "update Menu set seqNum = seqNum + 1 where siteId = ? and parentId = ? and seqNum >= ?";

	public int selectMaxSeqNumBySiteIdParentMenuId(int siteId, int parentMenuId) {
		List<Integer> seqNum = (List<Integer>)getHibernateTemplate().find(SELECT_MAX_SEQNUM_BY_SITEID_AND_MENUPARENTID, siteId, parentMenuId);

		if (seqNum.get(0) == null) {
			return 0;
		}
		else {
			return seqNum.get(0);
		}
	}

	public void updateSeqNum(int siteId, int parentId, int seqNum) {
		Query query = createQuery(UPDATE_MENU_SEQNUM);

		query.setInteger("siteId", siteId);
		query.setInteger("parentId", parentId);
		query.setInteger("seqNum", seqNum);

		query.executeUpdate();

		getSession().flush();
	}

	public void delete(int siteId, long menuId) {
		Query query = createQuery(DELETE_MENU_BY_SITEID_MENUSETID, siteId, menuId);

		query.executeUpdate();

		getSession().flush();
	}

	public void delete(int siteId, String setName) {
		Query query = createQuery(DELETE_MENU_BY_SITEID_MENUSETNAME, siteId, setName);

		query.executeUpdate();

		getSession().flush();
	}

	public Menu getByMenuId_SiteId(int siteId, long menuId)
		throws SecurityException, Exception {
		Menu menu = getById(menuId);

		if (menu.getSiteId () != siteId) {
			throw new SecurityException();
		}

		return menu;
	}

	public Menu getByMenuId(long menuId) {
		return getById(menuId);
	}

	public List<Menu> getByParentMenuId(int parentMenuId) {
		List<Menu> menus = (List<Menu>)getHibernateTemplate().find(GET_MENU_BY_MENUPARENTID, parentMenuId);

		if (menus.size() == 0) {
			return new ArrayList<Menu>();
		}
		else {
			return menus;
		}
	}

	public List<Menu> getBySiteId_ParentMenuId(int siteId, int parentMenuId) {
		List<Menu> menus = (List<Menu>)getHibernateTemplate().find(GET_MENU_BY_SITEID_MENUPARENTID, siteId, parentMenuId);

		if (menus.size() == 0) {
			return new ArrayList<Menu>();
		}
		else {
			return menus;
		}
	}

	public List<Menu> getBySiteId_MenuParentId_orderBy_MenuSetName(int siteId) {
		List<Menu> menus = (List<Menu>)getHibernateTemplate().find(GET_MENU_BY_SITEID_MENUSETNAME_ORDERBY_MENUSETNAME, siteId);

		if (menus.size() == 0) {
			return new ArrayList<Menu>();
		}
		else {
			return menus;
		}
	}

	public List<Menu> getBySiteId(int siteId) {
		List<Menu> menus = (List<Menu>)getHibernateTemplate().find(GET_MENU_BY_SITEID, siteId);

		if (menus.size() == 0) {
			return new ArrayList<Menu>();
		}
		else {
			return menus;
		}
	}

	public List<Menu> getBySiteId_MenuParentId(int siteId, int menuParentId) {
		List<Menu> menus = (List<Menu>)getHibernateTemplate().find(GET_MENU_BY_SITEID_AND_MENUPARENTID, siteId, menuParentId);

		if (menus.size() == 0) {
			return new ArrayList<Menu>();
		}
		else {
			return menus;
		}
	}

	public Menu getMenu(int siteId, String setName) {
		List<Menu> menus = (List<Menu>)getHibernateTemplate().find(GET_MENU_BY_SITEID_MENUSETNAME, siteId, setName);

		if (menus.size() == 0) {
			return new Menu();
		}
		else {
			return menus.get(0);
		}
	}

	public List<Menu> getMenu(int siteId, String setName, int menuParentId) {
		List<Menu> menus = (List<Menu>)getHibernateTemplate().find(GET_MENU_BY_SITEID_MENUSETNAME_MENUPARENTID, siteId, setName, menuParentId);

		if (menus.size() == 0) {
			return new ArrayList<Menu>();
		}
		else {
			return menus;
		}
	}

	public Menu getMenuBySiteIdMenuName(int siteId, String name) {
		List<Menu> menus = (List<Menu>)getHibernateTemplate().find(GET_MENU_BY_SITEID_MENUNAME, siteId, name);

		if (menus.size() == 0) {
			return null;
		}
		else {
			return menus.get(0);
		}
	}
}