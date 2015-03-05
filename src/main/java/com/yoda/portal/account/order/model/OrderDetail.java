package com.yoda.portal.account.order.model;//package com.yoda.portal.account.order.model;
//
//import java.util.Date;
//
//import com.yoda.controlpanel.item.model.Item;
//import com.yoda.util.BaseEntity;
//
//public class OrderDetail extends BaseEntity {
//
//	// Fields
//
//	private Long orderDetailId;
//
//	private long siteId;
//
//	private String itemNum;
//
//	private String itemUpcCd;
//
//	private Integer seqNum;
//
//	private String itemShortDesc;
//
//	private String itemShortDesc1;
//
//	private Float itemPrice;
//
//	private Integer itemMultQty;
//
//	private Float itemMultPrice;
//
//	private Integer itemOrderQty;
//
//	private Float itemPriceTotal;
//
//	private Float itemTaxTotal;
//
//	private String recUpdateBy;
//
//	private Date recUpdateDatetime;
//
//	private String recCreateBy;
//
//	private Date recCreateDatetime;
//
//	private Item item;
//
//	private OrderHeader orderHeader;
//
//	// Constructors
//
//	/** default constructor */
//	public OrderDetail() {
//	}
//
////	/** minimal constructor */
////	public OrderDetail(String siteId, String itemNum, String itemUpcCd,
////			Integer seqNum, String itemShortDesc, String itemShortDesc1,
////			Float itemPrice, Integer itemOrderQty, Float itemPriceTotal,
////			Float itemTaxTotal, String recUpdateBy, Date recUpdateDatetime,
////			String recCreateBy, Date recCreateDatetime) {
////		this.siteId = siteId;
////		this.itemNum = itemNum;
////		this.itemUpcCd = itemUpcCd;
////		this.seqNum = seqNum;
////		this.itemShortDesc = itemShortDesc;
////		this.itemShortDesc1 = itemShortDesc1;
////		this.itemPrice = itemPrice;
////		this.itemOrderQty = itemOrderQty;
////		this.itemPriceTotal = itemPriceTotal;
////		this.itemTaxTotal = itemTaxTotal;
////		this.recUpdateBy = recUpdateBy;
////		this.recUpdateDatetime = recUpdateDatetime;
////		this.recCreateBy = recCreateBy;
////		this.recCreateDatetime = recCreateDatetime;
////	}
////
////	/** full constructor */
////	public OrderDetail(String siteId, String itemNum, String itemUpcCd,
////			Integer seqNum, String itemShortDesc, String itemShortDesc1,
////			Float itemPrice, Integer itemMultQty, Float itemMultPrice,
////			Integer itemOrderQty, Float itemPriceTotal, Float itemTaxTotal,
////			String recUpdateBy, Date recUpdateDatetime, String recCreateBy,
////			Date recCreateDatetime, Item item, OrderHeader orderHeader) {
////		this.siteId = siteId;
////		this.itemNum = itemNum;
////		this.itemUpcCd = itemUpcCd;
////		this.seqNum = seqNum;
////		this.itemShortDesc = itemShortDesc;
////		this.itemShortDesc1 = itemShortDesc1;
////		this.itemPrice = itemPrice;
////		this.itemMultQty = itemMultQty;
////		this.itemMultPrice = itemMultPrice;
////		this.itemOrderQty = itemOrderQty;
////		this.itemPriceTotal = itemPriceTotal;
////		this.itemTaxTotal = itemTaxTotal;
////		this.recUpdateBy = recUpdateBy;
////		this.recUpdateDatetime = recUpdateDatetime;
////		this.recCreateBy = recCreateBy;
////		this.recCreateDatetime = recCreateDatetime;
////		this.item = item;
////		this.orderHeader = orderHeader;
////	}
//
//	// Property accessors
//	public Long getOrderDetailId() {
//		return this.orderDetailId;
//	}
//
//	public void setOrderDetailId(Long orderDetailId) {
//		this.orderDetailId = orderDetailId;
//	}
//
//	public long getSiteId() {
//		return this.siteId;
//	}
//
//	public void setSiteId(long siteId) {
//		this.siteId = siteId;
//	}
//
//	public String getItemNum() {
//		return this.itemNum;
//	}
//
//	public void setItemNum(String itemNum) {
//		this.itemNum = itemNum;
//	}
//
//	public String getItemUpcCd() {
//		return this.itemUpcCd;
//	}
//
//	public void setItemUpcCd(String itemUpcCd) {
//		this.itemUpcCd = itemUpcCd;
//	}
//
//	public Integer getSeqNum() {
//		return this.seqNum;
//	}
//
//	public void setSeqNum(Integer seqNum) {
//		this.seqNum = seqNum;
//	}
//
//	public String getItemShortDesc() {
//		return this.itemShortDesc;
//	}
//
//	public void setItemShortDesc(String itemShortDesc) {
//		this.itemShortDesc = itemShortDesc;
//	}
//
//	public String getItemShortDesc1() {
//		return this.itemShortDesc1;
//	}
//
//	public void setItemShortDesc1(String itemShortDesc1) {
//		this.itemShortDesc1 = itemShortDesc1;
//	}
//
//	public Float getItemPrice() {
//		return this.itemPrice;
//	}
//
//	public void setItemPrice(Float itemPrice) {
//		this.itemPrice = itemPrice;
//	}
//
//	public Integer getItemMultQty() {
//		return this.itemMultQty;
//	}
//
//	public void setItemMultQty(Integer itemMultQty) {
//		this.itemMultQty = itemMultQty;
//	}
//
//	public Float getItemMultPrice() {
//		return this.itemMultPrice;
//	}
//
//	public void setItemMultPrice(Float itemMultPrice) {
//		this.itemMultPrice = itemMultPrice;
//	}
//
//	public Integer getItemOrderQty() {
//		return this.itemOrderQty;
//	}
//
//	public void setItemOrderQty(Integer itemOrderQty) {
//		this.itemOrderQty = itemOrderQty;
//	}
//
//	public Float getItemPriceTotal() {
//		return this.itemPriceTotal;
//	}
//
//	public void setItemPriceTotal(Float itemPriceTotal) {
//		this.itemPriceTotal = itemPriceTotal;
//	}
//
//	public Float getItemTaxTotal() {
//		return this.itemTaxTotal;
//	}
//
//	public void setItemTaxTotal(Float itemTaxTotal) {
//		this.itemTaxTotal = itemTaxTotal;
//	}
//
//	public String getRecUpdateBy() {
//		return this.recUpdateBy;
//	}
//
//	public void setRecUpdateBy(String recUpdateBy) {
//		this.recUpdateBy = recUpdateBy;
//	}
//
//	public Date getRecUpdateDatetime() {
//		return this.recUpdateDatetime;
//	}
//
//	public void setRecUpdateDatetime(Date recUpdateDatetime) {
//		this.recUpdateDatetime = recUpdateDatetime;
//	}
//
//	public String getRecCreateBy() {
//		return this.recCreateBy;
//	}
//
//	public void setRecCreateBy(String recCreateBy) {
//		this.recCreateBy = recCreateBy;
//	}
//
//	public Date getRecCreateDatetime() {
//		return this.recCreateDatetime;
//	}
//
//	public void setRecCreateDatetime(Date recCreateDatetime) {
//		this.recCreateDatetime = recCreateDatetime;
//	}
//
//	public Item getItem() {
//		return this.item;
//	}
//
//	public void setItem(Item item) {
//		this.item = item;
//	}
//
//	public OrderHeader getOrderHeader() {
//		return this.orderHeader;
//	}
//
//	public void setOrderHeader(OrderHeader orderHeader) {
//		this.orderHeader = orderHeader;
//	}
//
//}
