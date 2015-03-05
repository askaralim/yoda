package com.yoda.menu.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.yoda.BaseDAO;
import com.yoda.menu.model.Menu;

@Repository
public class MenuDAO extends BaseDAO<Menu>{

	private static final String GET_MENU_BY_SITEID = "from Menu menu where siteId = ? and menuParentId is NULL order by seqNum";

	private static final String GET_MENU_BY_SITEID_MENUNAME = "from Menu menu where siteId = ? and menuName = ?";

	private static final String GET_MENU_BY_SITEID_MENUSETNAME = "from Menu menu where siteId = ? and menuSetName = ? and menuParentId is NULL order by seqNum";

	private static final String GET_MENU_BY_SITEID_MENUSETNAME_ORDERBY_MENUSETNAME = "from Menu menu where siteId = ? and menu.menuParentId is NULL order by menuSetName";

	private static final String GET_MENU_BY_SITEID_AND_MENUPARENTID = "from Menu menu where siteId = ? and menuParentId = ? order by seqNum";

	private static final String GET_MENU_BY_SITEID_MENUSETNAME_MENUPARENTID = "from Menu menu where siteId = ? and menuSetName = ? and menuParentId = ? order by seqNum";

	private static final String GET_MENU_BY_SITEID_MENUPARENTID = "from Menu menu where siteId = ? and menu.menuParentId = ? order by seqNum";

	private static final String GET_MENU_BY_MENUPARENTID  = "from Menu where menuParentId = ?";

	private static final String DELETE_MENU_BY_SITEID_MENUSETNAME = "delete from Menu where siteId = ? and menuSetName = ?";

	private static final String DELETE_MENU_BY_SITEID_MENUSETID = "delete from Menu where siteId = ? and menuId = ?";

	private static final String SELECT_MAX_SEQNUM_BY_SITEID_AND_MENUPARENTID = "select max(seqNum) from Menu where  siteId = ? and menuParentId = ?";

	private static final String UPDATE_MENU_SEQNUM = "update Menu set seqNum = seqNum + 1 where siteId = ? and menuParentId = ? and seqNum >= ?";

	public int selectMaxSeqNumBySiteIdParentMenuId(long siteId, long parentMenuId) {
		List<Integer> seqNum = (List<Integer>)getHibernateTemplate().find(SELECT_MAX_SEQNUM_BY_SITEID_AND_MENUPARENTID, siteId, parentMenuId);

		if (seqNum.get(0) == null) {
			return 0;
		}
		else {
			return seqNum.get(0);
		}
	}

	public void updateSeqNum(long siteId, long menuParentId, int seqNum) {
		Query query = createQuery(UPDATE_MENU_SEQNUM);

		query.setLong("siteId", siteId);
		query.setLong("menuParentId", menuParentId);
		query.setInteger("seqNum", seqNum);

		query.executeUpdate();

		getSession().flush();
	}

	public void delete(long siteId, long menuId) {
		Query query = createQuery(DELETE_MENU_BY_SITEID_MENUSETID, siteId, menuId);

		query.executeUpdate();

		getSession().flush();
	}

	public void delete(long siteId, String menuSetName) {
		Query query = createQuery(DELETE_MENU_BY_SITEID_MENUSETNAME, siteId, menuSetName);

		query.executeUpdate();

		getSession().flush();
	}

	public Menu getByMenuId_SiteId(long siteId, long menuId)
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

	public List<Menu> getByParentMenuId(long parentMenuId) {
		List<Menu> menus = (List<Menu>)getHibernateTemplate().find(GET_MENU_BY_MENUPARENTID, parentMenuId);

		if (menus.size() == 0) {
			return new ArrayList<Menu>();
		}
		else {
			return menus;
		}
	}

	public List<Menu> getBySiteId_ParentMenuId(long siteId, long parentMenuId) {
		List<Menu> menus = (List<Menu>)getHibernateTemplate().find(GET_MENU_BY_SITEID_MENUPARENTID, siteId, parentMenuId);

		if (menus.size() == 0) {
			return new ArrayList<Menu>();
		}
		else {
			return menus;
		}
	}

	public List<Menu> getBySiteId_MenuParentId_orderBy_MenuSetName(long siteId) {
		List<Menu> menus = (List<Menu>)getHibernateTemplate().find(GET_MENU_BY_SITEID_MENUSETNAME_ORDERBY_MENUSETNAME, siteId);

		if (menus.size() == 0) {
			return new ArrayList<Menu>();
		}
		else {
			return menus;
		}
	}

	public List<Menu> getBySiteId(long siteId) {
		List<Menu> menus = (List<Menu>)getHibernateTemplate().find(GET_MENU_BY_SITEID, siteId);

		if (menus.size() == 0) {
			return new ArrayList<Menu>();
		}
		else {
			return menus;
		}
	}

	public List<Menu> getBySiteId_MenuParentId(long siteId, long menuParentId) {
		List<Menu> menus = (List<Menu>)getHibernateTemplate().find(GET_MENU_BY_SITEID_AND_MENUPARENTID, siteId, menuParentId);

		if (menus.size() == 0) {
			return new ArrayList<Menu>();
		}
		else {
			return menus;
		}
	}

	public Menu getMenu(long siteId, String menuSetName) {
		List<Menu> menus = (List<Menu>)getHibernateTemplate().find(GET_MENU_BY_SITEID_MENUSETNAME, siteId, menuSetName);

		if (menus.size() == 0) {
			return new Menu();
		}
		else {
			return menus.get(0);
		}
	}

	public List<Menu> getMenu(long siteId, String menuSetName, long menuParentId) {
		List<Menu> menus = (List<Menu>)getHibernateTemplate().find(GET_MENU_BY_SITEID_MENUSETNAME_MENUPARENTID, siteId, menuSetName, menuParentId);

		if (menus.size() == 0) {
			return new ArrayList<Menu>();
		}
		else {
			return menus;
		}
	}

	public Menu getMenuBySiteIdMenuName(long siteId, String menuName) {
		List<Menu> menus = (List<Menu>)getHibernateTemplate().find(GET_MENU_BY_SITEID_MENUNAME, siteId, menuName);

		if (menus.size() == 0) {
			return null;
		}
		else {
			return menus.get(0);
		}
	}
}