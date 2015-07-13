package com.yoda.item.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.yoda.BaseDAO;
import com.yoda.item.model.Item;

@Repository
public class ItemDAO extends BaseDAO<Item> {
	private static final String GET_ITEM_BY_SITEID_AND_CONTENTNATURALKEY = "from Item where siteId = ? and itemNaturalKey = ?";

	private static final String GET_ITEM_BY_SITEID = "from Item item where item.siteId = ?";

	private static final String GET_ITEM_BY_CONTENTID = "from Item item where item.contentId = ?";

	public Item getItem(long siteId, int itemId) {
		Item item = getById(itemId);

		if (item.getSiteId() != siteId) {
			throw new SecurityException();
		}
		return item;
	}

	public List<Item> getItems(int siteId) {
		List<Item> items = (List<Item>) getHibernateTemplate().find(GET_ITEM_BY_SITEID, siteId);

		return items;
	}

	public List<Item> getItemsByContentId(long contentId) {
		List<Item> items = (List<Item>) getHibernateTemplate().find(GET_ITEM_BY_CONTENTID, contentId);

		return items;
	}

	public Item getItemBySiteId_NaturalKey(long siteId, String itemNaturalKey) {
		List<Item> items = (List<Item>) getHibernateTemplate().find(GET_ITEM_BY_SITEID_AND_CONTENTNATURALKEY, siteId, itemNaturalKey);

		return items.get(0);
	}

	public List<Item> search(
			long siteId, String itemNum, String itemUpcCd,
			String itemShortDesc) {
		String sql = "select item from Item item where siteId = :siteId ";

		if (itemNum != null && itemNum.length() > 0) {
			sql += "and itemNum like :itemNum ";
		}

		if (itemUpcCd != null && itemUpcCd.length() > 0) {
			sql += "and itemUpcCd like :itemUpcCd ";
		}

		if (itemShortDesc != null && itemShortDesc.length() > 0) {
			sql += "and itemShortDesc like :itemShortDesc ";
		}

		Query query = createQuery(sql);

		query.setLong("siteId", siteId);

		if (itemNum != null && itemNum.length() > 0) {
			query.setString("itemNum", itemNum);
		}

		if (itemUpcCd != null && itemUpcCd.length() > 0) {
			query.setString("itemUpcCd", itemUpcCd);
		}

		if (itemShortDesc != null && itemShortDesc.length() > 0) {
			query.setString("itemShortDesc", "%" + itemShortDesc + "%");
		}

		List<Item> items = (List<Item>)query.list();

		return items;
	}
}