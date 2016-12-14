package com.yoda.item.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.yoda.brand.model.Brand;
import com.yoda.item.model.Item;
import com.yoda.item.persistence.ItemMapper;
import com.yoda.kernal.util.ImageUploader;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;
//	private ItemDAO itemDAO;
	

	@Transactional(readOnly = true)
	public Item getItem(int itemId) {
		return itemMapper.getById(itemId);
	}

//	@Transactional(readOnly = true)
//	public Item getItem(int siteId, int itemId) {
//		return itemDAO.getItem(siteId, itemId);
//	}

//	@Deprecated
//	public Item getItem(int siteId, String itemNaturalKey) {
//		return itemDAO.getItemBySiteId_NaturalKey(siteId, itemNaturalKey);
//	}

	@Transactional(readOnly = true)
	public List<Item> getItems(int siteId) {
		return itemMapper.getItemsBySiteId(siteId);
	}

	@Transactional(readOnly = true)
	public List<Item> getItemsByContentId(long contentId) {
		return itemMapper.getItemsByContentId(contentId);
	}

	public List<Item> search(
			int siteId, String itemNum, String itemUpcCd,
			String itemShortDesc) {
		return null;
	}

	public void save(int siteId, Item item) {
		item.preInsert();

		item.setSiteId(siteId);

		itemMapper.insert(item);
	}

	public Item updateItemImage(int id, MultipartFile file) {
		Item item = getItem(id);

		ImageUploader imageUpload = new ImageUploader();

		imageUpload.deleteImage(item.getImagePath());

		try {
			String imagePath = imageUpload.uploadItemImage(file.getInputStream(), file.getOriginalFilename());

			item.setImagePath(imagePath);
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		item.preUpdate();

		itemMapper.update(item);

		return item;
	}

	@Deprecated
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
//		Item itemDB = this.getItem(item.getId());
//
//		itemDB.setBrand(item.getBrand());
//		itemDB.setCategoryId(item.getCategoryId());
//		itemDB.setContentId(item.getContentId());
//		itemDB.setDescription(item.getDescription());
//		itemDB.setLevel(item.getLevel());
//		itemDB.setName(item.getName());
//		itemDB.setPrice(item.getPrice());

		item.preUpdate();

		itemMapper.update(item);

		return item;
	}

	public void remove(int itemId) {
		Item item = this.getItem(itemId);

		itemMapper.delete(item);
	}
}