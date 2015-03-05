package com.yoda.content;

import java.io.File;
import java.util.Set;

import com.yoda.item.model.Item;
import com.yoda.section.model.DropDownMenu;

public class ContentEditCommand {
	boolean homePage;

	boolean published;

	File image;

	String imagePath;

//	ContentImage image;

	ContentMenuDisplayCommand selectedMenus[];

	DropDownMenu menuList[];

	DropDownMenu sectionTree;

	int selectedMenusCount;

	Long contentId;

	Long createBy;

	Long updateBy;

//	List<ContentImage> images;

	String addMenus[];

	String addSection;

	String title;

	String shortDescription;

	String description;

	String hitCounter;

	String publishDate;

	String expireDate;

	String createDefaultImageId;

	String key;

	String menuUrl;

	String menuWindowTarget;

	String menuWindowMode;

	String pageTitle;

	String createDate;

	String updateDate;

	String removeImages[];

	String removeMenus[];

	String selectedSection;

	Set<Item> items;

	public File getImage() {
		return image;
	}
	
	public void setImage(File image) {
		this.image = image;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

//	public List<ContentImage> getImages() {
//		return images;
//	}
//	
//	public void setImages(List<ContentImage> images) {
//		this.images = images;
//	}

	public DropDownMenu[] getMenuList() {
		return menuList;
	}

	public void setMenuList(DropDownMenu[] menuList) {
		this.menuList = menuList;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getHitCounter() {
		return hitCounter;
	}

	public void setHitCounter(String hitCounter) {
		this.hitCounter = hitCounter;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuWindowMode() {
		return menuWindowMode;
	}

	public void setMenuWindowMode(String menuWindowMode) {
		this.menuWindowMode = menuWindowMode;
	}

	public String getMenuWindowTarget() {
		return menuWindowTarget;
	}

	public void setMenuWindowTarget(String menuWindowTarget) {
		this.menuWindowTarget = menuWindowTarget;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public String[] getRemoveMenus() {
		return removeMenus;
	}

	public void setRemoveMenus(String[] removeMenus) {
		this.removeMenus = removeMenus;
	}

	public String[] getAddMenus() {
		return addMenus;
	}

	public void setAddMenus(String[] addMenus) {
		this.addMenus = addMenus;
	}

//	public FormFile getFile() {
//		return file;
//	}
//
//	public void setFile(FormFile file) {
//		this.file = file;
//	}

	public String[] getRemoveImages() {
		return removeImages;
	}

	public void setRemoveImages(String[] removeImages) {
		this.removeImages = removeImages;
	}

//	public LabelValueBean[] getImageIds() {
//		return imageIds;
//	}
//
//	public void setImageIds(LabelValueBean[] imageIds) {
//		this.imageIds = imageIds;
//	}

	public String getAddSection() {
		return addSection;
	}

	public void setAddSection(String addSection) {
		this.addSection = addSection;
	}

	public String getSelectedSection() {
		return selectedSection;
	}

	public void setSelectedSection(String selectedSection) {
		this.selectedSection = selectedSection;
	}

	public String getCreateDefaultImageId() {
		return createDefaultImageId;
	}

	public void setCreateDefaultImageId(String createDefaultImageId) {
		this.createDefaultImageId = createDefaultImageId;
	}

//	public LabelValueBean getDefaultImageId() {
//		return defaultImageId;
//	}
//
//	public void setDefaultImageId(LabelValueBean defaultImageId) {
//		this.defaultImageId = defaultImageId;
//	}

	public int getSelectedMenusCount() {
		return selectedMenusCount;
	}

	public void setSelectedMenusCount(int selectedMenusCount) {
		this.selectedMenusCount = selectedMenusCount;
	}

	public boolean isHomePage() {
		return homePage;
	}

	public void setHomePage(boolean homePage) {
		this.homePage = homePage;
	}

	public DropDownMenu getSectionTree() {
		return sectionTree;
	}

	public void setSectionTree(DropDownMenu sectionTree) {
		this.sectionTree = sectionTree;
	}

	public ContentMenuDisplayCommand[] getSelectedMenus() {
		return selectedMenus;
	}

	public void setSelectedMenus(ContentMenuDisplayCommand[] selectedMenus) {
		this.selectedMenus = selectedMenus;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

//	public String getMode() {
//		return mode;
//	}
//
//	public void setMode(String mode) {
//		this.mode = mode;
//	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		this.items = items;
	}

	public boolean isNew() {
		return (contentId == null);
	}
}