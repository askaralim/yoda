package com.yoda.item.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.yoda.brand.model.Brand;
import com.yoda.item.dao.ItemDAO;
import com.yoda.item.model.Item;
import com.yoda.kernal.util.FileUploader;
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
	public Item getItem(int siteId, int itemId) {
		return itemDAO.getItem(siteId, itemId);
	}

	@Transactional(readOnly = true)
	public Item getItem(int siteId, String itemNaturalKey) {
		return itemDAO.getItemBySiteId_NaturalKey(siteId, itemNaturalKey);
	}

	@Transactional(readOnly = true)
	public List<Item> getItems(int siteId) {
		return itemDAO.getAll();
	}

	@Transactional(readOnly = true)
	public List<Item> getItemsByContentId(long contentId) {
		return itemDAO.getItemsByContentId(contentId);
	}

	public List<Item> search(
			int siteId, String itemNum, String itemUpcCd,
			String itemShortDesc) {
		return null;
	}

	public void save(int siteId, Item item) {
		User user = PortalUtil.getAuthenticatedUser();

		item.setCreateBy(user.getUserId().intValue());
		item.setCreateDate(new Date());
		item.setUpdateBy(user.getUserId().intValue());
		item.setUpdateDate(new Date());
		item.setSiteId(siteId);

		itemDAO.save(item);
	}

	public Item updateItemImage(int id, MultipartFile file) {
		Item item = getItem(id);

		FileUploader fileUpload = FileUploader.getInstance();

		fileUpload.deleteFile(item.getImagePath());

		String imagePath = fileUpload.saveFile(file);

		item.setImagePath(imagePath);

		update(item);

		return item;
	}

	public Item update(
			int id, Brand brand, Integer categoryId, Long contentId,
			String description, String level, String name, int price) {
		Item item = this.getItem(id);

		item.setBrand(brand);
		item.setCategoryId(categoryId);
		item.setContentId(contentId);
		item.setDescription(description);
		item.setLevel(level);
		item.setName(name);
		item.setPrice(price);

		update(item);

		return item;
	}

	public Item update(Item item) {
		item.setUpdateBy(PortalUtil.getAuthenticatedUser().getUserId().intValue());
		item.setUpdateDate(new Date());

		itemDAO.update(item);

		return item;
	}

	public void remove(int itemId) {
		Item item = itemDAO.getById(itemId);

		itemDAO.delete(item);
	}
}