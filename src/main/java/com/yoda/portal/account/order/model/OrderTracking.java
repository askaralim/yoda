package com.yoda.portal.account.order.model;

import java.util.Date;

import com.yoda.BaseEntity;

public class OrderTracking extends BaseEntity {

	// Fields

	private Long orderTrackingId;

	private long siteId;

	private String orderTrackingCode;

	private String orderTrackingMessage;

	private String orderTrackingInternal;

	private String recUpdateBy;

	private Date recUpdateDatetime;

	private String recCreateBy;

	private Date recCreateDatetime;

//	private OrderHeader orderHeader;

	// Constructors

	/** default constructor */
	public OrderTracking() {
	}

	/** minimal constructor */
//	public OrderTracking(String siteId, String orderTrackingCode,
//			String orderTrackingMessage, String orderTrackingInternal,
//			String recUpdateBy, Date recUpdateDatetime, String recCreateBy,
//			Date recCreateDatetime) {
//		this.siteId = siteId;
//		this.orderTrackingCode = orderTrackingCode;
//		this.orderTrackingMessage = orderTrackingMessage;
//		this.orderTrackingInternal = orderTrackingInternal;
//		this.recUpdateBy = recUpdateBy;
//		this.recUpdateDatetime = recUpdateDatetime;
//		this.recCreateBy = recCreateBy;
//		this.recCreateDatetime = recCreateDatetime;
//	}
//
//	/** full constructor */
//	public OrderTracking(String siteId, String orderTrackingCode,
//			String orderTrackingMessage, String orderTrackingInternal,
//			String recUpdateBy, Date recUpdateDatetime, String recCreateBy,
//			Date recCreateDatetime, OrderHeader orderHeader) {
//		this.siteId = siteId;
//		this.orderTrackingCode = orderTrackingCode;
//		this.orderTrackingMessage = orderTrackingMessage;
//		this.orderTrackingInternal = orderTrackingInternal;
//		this.recUpdateBy = recUpdateBy;
//		this.recUpdateDatetime = recUpdateDatetime;
//		this.recCreateBy = recCreateBy;
//		this.recCreateDatetime = recCreateDatetime;
//		this.orderHeader = orderHeader;
//	}

	// Property accessors
	public Long getOrderTrackingId() {
		return this.orderTrackingId;
	}

	public void setOrderTrackingId(Long orderTrackingId) {
		this.orderTrackingId = orderTrackingId;
	}

	public long getSiteId() {
		return this.siteId;
	}

	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}

	public String getOrderTrackingCode() {
		return this.orderTrackingCode;
	}

	public void setOrderTrackingCode(String orderTrackingCode) {
		this.orderTrackingCode = orderTrackingCode;
	}

	public String getOrderTrackingMessage() {
		return this.orderTrackingMessage;
	}

	public void setOrderTrackingMessage(String orderTrackingMessage) {
		this.orderTrackingMessage = orderTrackingMessage;
	}

	public String getOrderTrackingInternal() {
		return this.orderTrackingInternal;
	}

	public void setOrderTrackingInternal(String orderTrackingInternal) {
		this.orderTrackingInternal = orderTrackingInternal;
	}

	public String getRecUpdateBy() {
		return this.recUpdateBy;
	}

	public void setRecUpdateBy(String recUpdateBy) {
		this.recUpdateBy = recUpdateBy;
	}

	public Date getRecUpdateDatetime() {
		return this.recUpdateDatetime;
	}

	public void setRecUpdateDatetime(Date recUpdateDatetime) {
		this.recUpdateDatetime = recUpdateDatetime;
	}

	public String getRecCreateBy() {
		return this.recCreateBy;
	}

	public void setRecCreateBy(String recCreateBy) {
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
