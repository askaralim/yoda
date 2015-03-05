package com.yoda.item.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yoda.item.dao.ItemDAO;
import com.yoda.item.model.Item;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.user.model.User;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemDAO itemDAO;

	@Transactional(readOnly = true)
	public Item getItem(int itemId) {
		return itemDAO.getById(itemId);
	}

	@Transactional(readOnly = true)
	public Item getItem(long siteId, int itemId) {
		return itemDAO.getItem(siteId, itemId);
	}

	@Transactional(readOnly = true)
	public Item getItem(long siteId, String itemNaturalKey) {
		return itemDAO.getItemBySiteId_NaturalKey(siteId, itemNaturalKey);
	}

	@Transactional(readOnly = true)
	public List<Item> getItems(long siteId) {
		return itemDAO.getAll();
	}

	public List<Item> search(
			long siteId, String itemNum, String itemUpcCd,
			String itemShortDesc) {
		return null;
	}

	public void save(Long siteId, Item item) {
		User user = PortalUtil.getAuthenticatedUser();

		item.setCreateBy(user.getUserId().intValue());
		item.setCreateDate(new Date());
		item.setUpdateBy(user.getUserId().intValue());
		item.setUpdateDate(new Date());
		item.setSiteId(siteId.intValue());

		itemDAO.save(item);
	}

	public Item updateItemImage(int id, String imagePath) {
		Item item = getItem(id);

		item.setImagePath(imagePath);
		item.setUpdateBy(PortalUtil.getAuthenticatedUser().getUserId().intValue());
		item.setUpdateDate(new Date());

		update(item);

		return item;
	}

	public Item update(
			int id, String brand, String description, String level, String name,
			int price) {
		Item item = this.getItem(id);

		item.setBrand(brand);
		item.setDescription(description);
		item.setLevel(level);
		item.setName(name);
		item.setPrice(price);
		item.setUpdateBy(PortalUtil.getAuthenticatedUser().getUserId().intValue());
		item.setUpdateDate(new Date());

		update(item);

		return item;
	}

	public Item update(Item item) {
		itemDAO.update(item);

		return item;
	}

	public void remove(int itemId) {
		Item item = itemDAO.getById(itemId);

		itemDAO.delete(item);
	}
}