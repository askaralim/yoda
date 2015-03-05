package com.yoda.portal.content.data;

import java.util.Vector;

public class ItemInfo extends DataInfo {
	String infoType = "item";
	boolean feature;
	boolean outOfStock;
	String itemNaturalKey;
	long itemId;
	String itemNum;
	String itemUpcCd;
	String itemShortDesc;
	String itemShortDesc1;
	String itemDesc;
	String pageTitle;
	String itemPrice;
	String itemMultQty;
	String itemMultPrice;
	String itemPriceDisplay;
	String itemSpecPrice;
	String itemSpecMultQty;
	String itemSpecMultPrice;
	String itemSpecPriceDisplay;
	String itemSpecPublishOn;
	String itemSpecExpireOn;
	String itemHitCounter;
	String itemRating;
	String itemRatingCount;
	String itemQty;
	String itemBookedQty;
	String itemPublishOn;
	String itemExpireOn;
	String itemUrl;
	String itemDefaultImageUrl;
	String itemUpdateName;
	long recUpdateBy;
	String recUpdateDatetime;
	long recCreateBy;
	String recCreateDatetime;
	Vector itemImageUrls;
	String itemOrderedQty;

	public String getItemPriceDisplay() {
		return itemPriceDisplay;
	}

	public void setItemPriceDisplay(String itemPriceDisplay) {
		this.itemPriceDisplay = itemPriceDisplay;
	}

	public String getItemSpecPriceDisplay() {
		return itemSpecPriceDisplay;
	}

	public void setItemSpecPriceDisplay(String itemSpecPriceDisplay) {
		this.itemSpecPriceDisplay = itemSpecPriceDisplay;
	}

	public Vector getItemImageUrls() {
		return itemImageUrls;
	}

	public void setItemImageUrls(Vector itemImageUrls) {
		this.itemImageUrls = itemImageUrls;
	}

	public String getItemUrl() {
		return itemUrl;
	}

	public void setItemUrl(String itemUrl) {
		this.itemUrl = itemUrl;
	}

	public String getItemBookedQty() {
		return itemBookedQty;
	}

	public void setItemBookedQty(String itemBookedQty) {
		this.itemBookedQty = itemBookedQty;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public String getItemExpireOn() {
		return itemExpireOn;
	}

	public void setItemExpireOn(String itemExpireOn) {
		this.itemExpireOn = itemExpireOn;
	}

	public String getItemHitCounter() {
		return itemHitCounter;
	}

	public void setItemHitCounter(String itemHitCounter) {
		this.itemHitCounter = itemHitCounter;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getItemMultPrice() {
		return itemMultPrice;
	}

	public void setItemMultPrice(String itemMultPrice) {
		this.itemMultPrice = itemMultPrice;
	}

	public String getItemMultQty() {
		return itemMultQty;
	}

	public void setItemMultQty(String itemMultQty) {
		this.itemMultQty = itemMultQty;
	}

	public String getItemNum() {
		return itemNum;
	}

	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}

	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemPublishOn() {
		return itemPublishOn;
	}

	public void setItemPublishOn(String itemPublishOn) {
		this.itemPublishOn = itemPublishOn;
	}

	public String getItemQty() {
		return itemQty;
	}

	public void setItemQty(String itemQty) {
		this.itemQty = itemQty;
	}

	public String getItemRating() {
		return itemRating;
	}

	public void setItemRating(String itemRating) {
		this.itemRating = itemRating;
	}

	public String getItemRatingCount() {
		return itemRatingCount;
	}

	public void setItemRatingCount(String itemRatingCount) {
		this.itemRatingCount = itemRatingCount;
	}

	public String getItemShortDesc() {
		return itemShortDesc;
	}

	public void setItemShortDesc(String itemShortDesc) {
		this.itemShortDesc = itemShortDesc;
	}

	public String getItemShortDesc1() {
		return itemShortDesc1;
	}

	public void setItemShortDesc1(String itemShortDesc1) {
		this.itemShortDesc1 = itemShortDesc1;
	}

	public String getItemSpecExpireOn() {
		return itemSpecExpireOn;
	}

	public void setItemSpecExpireOn(String itemSpecExpireOn) {
		this.itemSpecExpireOn = itemSpecExpireOn;
	}

	public String getItemSpecMultPrice() {
		return itemSpecMultPrice;
	}

	public void setItemSpecMultPrice(String itemSpecMultPrice) {
		this.itemSpecMultPrice = itemSpecMultPrice;
	}

	public String getItemSpecMultQty() {
		return itemSpecMultQty;
	}

	public void setItemSpecMultQty(String itemSpecMultQty) {
		this.itemSpecMultQty = itemSpecMultQty;
	}

	public String getItemSpecPrice() {
		return itemSpecPrice;
	}

	public void setItemSpecPrice(String itemSpecPrice) {
		this.itemSpecPrice = itemSpecPrice;
	}

	public String getItemSpecPublishOn() {
		return itemSpecPublishOn;
	}

	public void setItemSpecPublishOn(String itemSpecPublishOn) {
		this.itemSpecPublishOn = itemSpecPublishOn;
	}

	public String getItemUpcCd() {
		return itemUpcCd;
	}

	public void setItemUpcCd(String itemUpcCd) {
		this.itemUpcCd = itemUpcCd;
	}

	public String getInfoType() {
		return infoType;
	}

	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}

	public String getItemDefaultImageUrl() {
		return itemDefaultImageUrl;
	}

	public void setItemDefaultImageUrl(String itemDefaultImageUrl) {
		this.itemDefaultImageUrl = itemDefaultImageUrl;
	}

	public boolean isFeature() {
		return feature;
	}

	public void setFeature(boolean feature) {
		this.feature = feature;
	}

	public boolean isOutOfStock() {
		return outOfStock;
	}

	public void setOutOfStock(boolean outOfStock) {
		this.outOfStock = outOfStock;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public String getItemNaturalKey() {
		return itemNaturalKey;
	}

	public void setItemNaturalKey(String itemNaturalKey) {
		this.itemNaturalKey = itemNaturalKey;
	}

	public String getItemOrderedQty() {
		return itemOrderedQty;
	}

	public void setItemOrderedQty(String itemOrderedQty) {
		this.itemOrderedQty = itemOrderedQty;
	}

	public long getRecCreateBy() {
		return recCreateBy;
	}

	public void setRecCreateBy(long recCreateBy) {
		this.recCreateBy = recCreateBy;
	}

	public String getRecCreateDatetime() {
		return recCreateDatetime;
	}

	public void setRecCreateDatetime(String recCreateDatetime) {
		this.recCreateDatetime = recCreateDatetime;
	}

	public long getRecUpdateBy() {
		return recUpdateBy;
	}

	public void setRecUpdateBy(long recUpdateBy) {
		this.recUpdateBy = recUpdateBy;
	}

	public String getRecUpdateDatetime() {
		return recUpdateDatetime;
	}

	public void setRecUpdateDatetime(String recUpdateDatetime) {
		this.recUpdateDatetime = recUpdateDatetime;
	}

	public String getItemUpdateName() {
		return itemUpdateName;
	}

	public void setItemUpdateName(String itemUpdateName) {
		this.itemUpdateName = itemUpdateName;
	}
}
