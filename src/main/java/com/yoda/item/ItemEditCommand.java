//package com.yoda.item;
//
//import java.util.List;
//
//import com.yoda.section.model.DropDownMenu;
//
//public class ItemEditCommand {
//	String mode;
//	String key;
//	String itemId;
//	String siteId;
//	String itemNum;
//	String itemUpcCd;
//	String itemShortDesc;
//	String itemShortDesc1;
//	String itemDesc;
//	String pageTitle;
//	String itemCost;
//	String itemPrice;
//	String itemMultQty;
//	String itemMultPrice;
//	String itemSpecPrice;
//	String itemSpecMultQty;
//	String itemSpecMultPrice;
//	String itemSpecPublishOn;
//	String itemSpecExpireOn;
//	String itemHitCounter;
//	String itemRating;
//	String itemRatingCount;
//	String itemQty;
//	String itemBookedQty;
//	String itemPublishOn;
//	String itemExpireOn;
//	boolean homePage;
//	boolean published;
//	String recUpdateBy;
//	String recUpdateDatetime;
//	String recCreateBy;
//	String recCreateDatetime;
//	List<ItemImage> itemImages;
//
//	String itemAdjQty;
//	String itemAdjBookedQty;
//
//	String shippingTypeId;
////	LabelValueBean shippingTypes[];
//	DropDownMenu menuList[];
//	String menuUrl;
//	String menuWindowTarget;
//	String menuWindowMode;
//	ItemMenuDisplayCommand selectedMenus[];
//	int selectedMenusCount;
//	String removeMenus[];
//	String addMenus[];
//
//	DropDownMenu sectionTree;
//	String selectedSection;
//	String addSection;
////	LabelValueBean defaultImageId;
//	String createDefaultImageId;
////	LabelValueBean imageIds[];
//	String removeImages[];
////	FormFile file;
//
//	public List<ItemImage> getItemImages() {
//		return itemImages;
//	}
//
//	public void setItemImages(List<ItemImage> itemImages) {
//		this.itemImages = itemImages;
//	}
//
//	public ItemMenuDisplayCommand[] getSelectedMenus() {
//		return selectedMenus;
//	}
//
//	public void setSelectedMenus(ItemMenuDisplayCommand[] selectedMenus) {
//		this.selectedMenus = selectedMenus;
//	}
//
//	public String getAddSection() {
//		return addSection;
//	}
//
//	public void setAddSection(String addSection) {
//		this.addSection = addSection;
//	}
//
////	public FormFile getFile() {
////		return file;
////	}
////
////	public void setFile(FormFile file) {
////		this.file = file;
////	}
//
////	public LabelValueBean[] getImageIds() {
////		return imageIds;
////	}
////
////	public void setImageIds(LabelValueBean[] imageIds) {
////		this.imageIds = imageIds;
////	}
//
//	public String getItemBookedQty() {
//		return itemBookedQty;
//	}
//
//	public void setItemBookedQty(String itemBookedQty) {
//		this.itemBookedQty = itemBookedQty;
//	}
//
//	public String getItemDesc() {
//		return itemDesc;
//	}
//
//	public void setItemDesc(String itemDesc) {
//		this.itemDesc = itemDesc;
//	}
//
//	public String getItemExpireOn() {
//		return itemExpireOn;
//	}
//
//	public void setItemExpireOn(String itemExpireOn) {
//		this.itemExpireOn = itemExpireOn;
//	}
//
//	public String getItemHitCounter() {
//		return itemHitCounter;
//	}
//
//	public void setItemHitCounter(String itemHitCounter) {
//		this.itemHitCounter = itemHitCounter;
//	}
//
//	public String getItemId() {
//		return itemId;
//	}
//
//	public void setItemId(String itemId) {
//		this.itemId = itemId;
//	}
//
//	public String getItemMultPrice() {
//		return itemMultPrice;
//	}
//
//	public void setItemMultPrice(String itemMultPrice) {
//		this.itemMultPrice = itemMultPrice;
//	}
//
//	public String getItemMultQty() {
//		return itemMultQty;
//	}
//
//	public void setItemMultQty(String itemMultQty) {
//		this.itemMultQty = itemMultQty;
//	}
//
//	public String getItemNum() {
//		return itemNum;
//	}
//
//	public void setItemNum(String itemNum) {
//		this.itemNum = itemNum;
//	}
//
//	public String getItemPrice() {
//		return itemPrice;
//	}
//
//	public void setItemPrice(String itemPrice) {
//		this.itemPrice = itemPrice;
//	}
//
//	public String getItemPublishOn() {
//		return itemPublishOn;
//	}
//
//	public void setItemPublishOn(String itemPublishOn) {
//		this.itemPublishOn = itemPublishOn;
//	}
//
//	public String getItemQty() {
//		return itemQty;
//	}
//
//	public void setItemQty(String itemQty) {
//		this.itemQty = itemQty;
//	}
//
//	public String getItemRating() {
//		return itemRating;
//	}
//
//	public void setItemRating(String itemRating) {
//		this.itemRating = itemRating;
//	}
//
//	public String getItemRatingCount() {
//		return itemRatingCount;
//	}
//
//	public void setItemRatingCount(String itemRatingCount) {
//		this.itemRatingCount = itemRatingCount;
//	}
//
//	public String getItemShortDesc() {
//		return itemShortDesc;
//	}
//
//	public void setItemShortDesc(String itemShortDesc) {
//		this.itemShortDesc = itemShortDesc;
//	}
//
//	public String getItemShortDesc1() {
//		return itemShortDesc1;
//	}
//
//	public void setItemShortDesc1(String itemShortDesc1) {
//		this.itemShortDesc1 = itemShortDesc1;
//	}
//
//	public String getItemSpecExpireOn() {
//		return itemSpecExpireOn;
//	}
//
//	public void setItemSpecExpireOn(String itemSpecExpireOn) {
//		this.itemSpecExpireOn = itemSpecExpireOn;
//	}
//
//	public String getItemSpecMultPrice() {
//		return itemSpecMultPrice;
//	}
//
//	public void setItemSpecMultPrice(String itemSpecMultPrice) {
//		this.itemSpecMultPrice = itemSpecMultPrice;
//	}
//
//	public String getItemSpecMultQty() {
//		return itemSpecMultQty;
//	}
//
//	public void setItemSpecMultQty(String itemSpecMultQty) {
//		this.itemSpecMultQty = itemSpecMultQty;
//	}
//
//	public String getItemSpecPrice() {
//		return itemSpecPrice;
//	}
//
//	public void setItemSpecPrice(String itemSpecPrice) {
//		this.itemSpecPrice = itemSpecPrice;
//	}
//
//	public String getItemSpecPublishOn() {
//		return itemSpecPublishOn;
//	}
//
//	public void setItemSpecPublishOn(String itemSpecPublishOn) {
//		this.itemSpecPublishOn = itemSpecPublishOn;
//	}
//
//	public String getItemUpcCd() {
//		return itemUpcCd;
//	}
//
//	public void setItemUpcCd(String itemUpcCd) {
//		this.itemUpcCd = itemUpcCd;
//	}
//
//	public boolean isPublished() {
//		return published;
//	}
//
//	public void setPublished(boolean published) {
//		this.published = published;
//	}
//
//	public String getRecCreateBy() {
//		return recCreateBy;
//	}
//
//	public void setRecCreateBy(String recCreateBy) {
//		this.recCreateBy = recCreateBy;
//	}
//
//	public String getRecCreateDatetime() {
//		return recCreateDatetime;
//	}
//
//	public void setRecCreateDatetime(String recCreateDatetime) {
//		this.recCreateDatetime = recCreateDatetime;
//	}
//
//	public String getRecUpdateBy() {
//		return recUpdateBy;
//	}
//
//	public void setRecUpdateBy(String recUpdateBy) {
//		this.recUpdateBy = recUpdateBy;
//	}
//
//	public String getRecUpdateDatetime() {
//		return recUpdateDatetime;
//	}
//
//	public void setRecUpdateDatetime(String recUpdateDatetime) {
//		this.recUpdateDatetime = recUpdateDatetime;
//	}
//
//	public String[] getRemoveImages() {
//		return removeImages;
//	}
//
//	public void setRemoveImages(String[] removeImages) {
//		this.removeImages = removeImages;
//	}
//
//	public String getSelectedSection() {
//		return selectedSection;
//	}
//
//	public void setSelectedSection(String selectedSection) {
//		this.selectedSection = selectedSection;
//	}
//
//	public String getSiteId() {
//		return siteId;
//	}
//
//	public void setSiteId(String siteId) {
//		this.siteId = siteId;
//	}
//
////	public LabelValueBean getDefaultImageId() {
////		return defaultImageId;
////	}
////
////	public void setDefaultImageId(LabelValueBean defaultImageId) {
////		this.defaultImageId = defaultImageId;
////	}
//
//	public String getCreateDefaultImageId() {
//		return createDefaultImageId;
//	}
//
//	public void setCreateDefaultImageId(String createDefaultImageId) {
//		this.createDefaultImageId = createDefaultImageId;
//	}
//
//	public String[] getAddMenus() {
//		return addMenus;
//	}
//
//	public void setAddMenus(String[] addMenus) {
//		this.addMenus = addMenus;
//	}
//
//	public String getMenuUrl() {
//		return menuUrl;
//	}
//
//	public void setMenuUrl(String menuUrl) {
//		this.menuUrl = menuUrl;
//	}
//
//	public String getMenuWindowMode() {
//		return menuWindowMode;
//	}
//
//	public void setMenuWindowMode(String menuWindowMode) {
//		this.menuWindowMode = menuWindowMode;
//	}
//
//	public String getMenuWindowTarget() {
//		return menuWindowTarget;
//	}
//
//	public void setMenuWindowTarget(String menuWindowTarget) {
//		this.menuWindowTarget = menuWindowTarget;
//	}
//
//	public String[] getRemoveMenus() {
//		return removeMenus;
//	}
//
//	public void setRemoveMenus(String[] removeMenus) {
//		this.removeMenus = removeMenus;
//	}
//
//	public int getSelectedMenusCount() {
//		return selectedMenusCount;
//	}
//
//	public void setSelectedMenusCount(int selectedMenusCount) {
//		this.selectedMenusCount = selectedMenusCount;
//	}
//
//	public boolean isHomePage() {
//		return homePage;
//	}
//
//	public void setHomePage(boolean homePage) {
//		this.homePage = homePage;
//	}
//
//	public String getItemAdjBookedQty() {
//		return itemAdjBookedQty;
//	}
//
//	public void setItemAdjBookedQty(String itemAdjBookedQty) {
//		this.itemAdjBookedQty = itemAdjBookedQty;
//	}
//
//	public String getItemAdjQty() {
//		return itemAdjQty;
//	}
//
//	public void setItemAdjQty(String itemAdjQty) {
//		this.itemAdjQty = itemAdjQty;
//	}
//
//	public DropDownMenu[] getMenuList() {
//		return menuList;
//	}
//
//	public void setMenuList(DropDownMenu[] menuList) {
//		this.menuList = menuList;
//	}
//
//	public DropDownMenu getSectionTree() {
//		return sectionTree;
//	}
//
//	public void setSectionTree(DropDownMenu sectionTree) {
//		this.sectionTree = sectionTree;
//	}
//
////	public LabelValueBean[] getShippingTypes() {
////		return shippingTypes;
////	}
////
////	public void setShippingTypes(LabelValueBean[] shippingTypes) {
////		this.shippingTypes = shippingTypes;
////	}
//
//	public String getShippingTypeId() {
//		return shippingTypeId;
//	}
//
//	public void setShippingTypeId(String shippingTypeId) {
//		this.shippingTypeId = shippingTypeId;
//	}
//
//	public String getItemCost() {
//		return itemCost;
//	}
//
//	public void setItemCost(String itemCost) {
//		this.itemCost = itemCost;
//	}
//
//	public String getKey() {
//		return key;
//	}
//
//	public void setKey(String key) {
//		this.key = key;
//	}
//
//	public String getMode() {
//		return mode;
//	}
//
//	public void setMode(String mode) {
//		this.mode = mode;
//	}
//
//	public String getPageTitle() {
//		return pageTitle;
//	}
//
//	public void setPageTitle(String pageTitle) {
//		this.pageTitle = pageTitle;
//	}
//}
