package com.yoda.portal.account.order.model;

import java.util.Date;

public class OrderTax implements java.io.Serializable {

	// Fields

	private Long orderTaxId;

	private long siteId;

	private String taxName;

	private Float taxRate;

	private Float taxAmount;

	private long recUpdateBy;

	private Date recUpdateDatetime;

	private long recCreateBy;

	private Date recCreateDatetime;

//	private OrderHeader orderHeader;

	// Constructors

	/** default constructor */
	public OrderTax() {
	}

//	/** minimal constructor */
//	public OrderTax(String siteId, String taxName, Float taxRate,
//			Float taxAmount, String recUpdateBy, Date recUpdateDatetime,
//			String recCreateBy, Date recCreateDatetime) {
//		this.siteId = siteId;
//		this.taxName = taxName;
//		this.taxRate = taxRate;
//		this.taxAmount = taxAmount;
//		this.recUpdateBy = recUpdateBy;
//		this.recUpdateDatetime = recUpdateDatetime;
//		this.recCreateBy = recCreateBy;
//		this.recCreateDatetime = recCreateDatetime;
//	}
//
//	/** full constructor */
//	public OrderTax(String siteId, String taxName, Float taxRate,
//			Float taxAmount, String recUpdateBy, Date recUpdateDatetime,
//			String recCreateBy, Date recCreateDatetime, OrderHeader orderHeader) {
//		this.siteId = siteId;
//		this.taxName = taxName;
//		this.taxRate = taxRate;
//		this.taxAmount = taxAmount;
//		this.recUpdateBy = recUpdateBy;
//		this.recUpdateDatetime = recUpdateDatetime;
//		this.recCreateBy = recCreateBy;
//		this.recCreateDatetime = recCreateDatetime;
//		this.orderHeader = orderHeader;
//	}

	// Property accessors
	public Long getOrderTaxId() {
		return this.orderTaxId;
	}

	public void setOrderTaxId(Long orderTaxId) {
		this.orderTaxId = orderTaxId;
	}

	public long getSiteId() {
		return this.siteId;
	}

	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}

	public String getTaxName() {
		return this.taxName;
	}

	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}

	public Float getTaxRate() {
		return this.taxRate;
	}

	public void setTaxRate(Float taxRate) {
		this.taxRate = taxRate;
	}

	public Float getTaxAmount() {
		return this.taxAmount;
	}

	public void setTaxAmount(Float taxAmount) {
		this.taxAmount = taxAmount;
	}

	public long getRecUpdateBy() {
		return this.recUpdateBy;
	}

	public void setRecUpdateBy(long recUpdateBy) {
		this.recUpdateBy = recUpdateBy;
	}

	public Date getRecUpdateDatetime() {
		return this.recUpdateDatetime;
	}

	public void setRecUpdateDatetime(Date recUpdateDatetime) {
		this.recUpdateDatetime = recUpdateDatetime;
	}

	public long getRecCreateBy() {
		return this.recCreateBy;
	}

	public void setRecCreateBy(long recCreateBy) {
		this.recCreateBy = recCreateBy;
	}

	public Date getRecCreateDatetime() {
		return this.recCreateDatetime;
	}

	public void setRecCreateDatetime(Date recCreateDatetime) {
		this.recCreateDatetime = recCreateDatetime;
	}

//	public OrderHeader getOrderHeader() {
//		return this.orderHeader;
//	}
//
//	public void setOrderHeader(OrderHeader orderHeader) {
//		this.orderHeader = orderHeader;
//	}

}
