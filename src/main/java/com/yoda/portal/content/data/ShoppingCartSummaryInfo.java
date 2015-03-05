package com.yoda.portal.content.data;

public class ShoppingCartSummaryInfo {
	ItemInfo cartItems[];
	String itemCount;
	String priceSubTotal;

	public String getItemCount() {
		return itemCount;
	}

	public void setItemCount(String itemCount) {
		this.itemCount = itemCount;
	}

	public String getPriceSubTotal() {
		return priceSubTotal;
	}

	public void setPriceSubTotal(String priceSubTotal) {
		this.priceSubTotal = priceSubTotal;
	}

	public ItemInfo[] getCartItems() {
		return cartItems;
	}

	public void setCartItems(ItemInfo[] cartItems) {
		this.cartItems = cartItems;
	}
}
