package com.yoda.menu.persistence;

import java.util.List;

import com.yoda.kernal.annotation.YodaMyBatisMapper;
import com.yoda.kernal.persistence.BaseMapper;
import com.yoda.menu.model.Menu;

@YodaMyBatisMapper
public interface MenuMapper extends BaseMapper<Menu> {
	int selectMaxSeqNumBySiteIdParentMenuId(int siteId, int parentMenuId);

	void delete(long menuId);

	void updateSeqNum(int siteId, int parentId, int seqNum);

	List<Menu> getMenusBySiteId(int siteId);

	List<Menu> getMenusBySiteIdAndParentId(int siteId, int parentMenuId);

	List<Menu> getMenusBySiteIdAndSetNameAndParentId(int siteId, String setName, int menuParentId);

	List<Menu> getMenusRootBySiteIdOrderBySetName(int siteId);

	Menu getMenuBySiteIdAndName(int siteId, String name);

//	Menu getMenuBySiteIdAndSetName(int siteId, String setName);

	Menu getMenuRootBySiteIdAndSetNameOrderBySetName(int siteId, String setName);
}