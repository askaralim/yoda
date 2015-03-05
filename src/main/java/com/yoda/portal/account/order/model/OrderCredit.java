package com.yoda.portal.account.order.model;//package com.yoda.portal.account.order.model;
//
//import java.util.Date;
//
//public class OrderCredit implements java.io.Serializable {
//
//	// Fields
//
//	private Long orderCreditId;
//
//	private String siteId;
//
//	private String creditType;
//
//	private Float creditTotal;
//
//	private String creditReference1;
//
//	private String creditReference2;
//
//	private String creditReference3;
//
//	private String creditReference4;
//
//	private String creditReference5;
//
//	private Date creditDatetime;
//
//	private String recUpdateBy;
//
//	private Date recUpdateDatetime;
//
//	private String recCreateBy;
//
//	private Date recCreateDatetime;
//
//	private OrderHeader orderHeader;
//
//	// Constructors
//
//	/** default constructor */
//	public OrderCredit() {
//	}
//
//	/** minimal constructor */
//	public OrderCredit(String siteId, String creditType, Float creditTotal,
//			Date creditDatetime, String recUpdateBy, Date recUpdateDatetime,
//			String recCreateBy, Date recCreateDatetime) {
//		this.siteId = siteId;
//		this.creditType = creditType;
//		this.creditTotal = creditTotal;
//		this.creditDatetime = creditDatetime;
//		this.recUpdateBy = recUpdateBy;
//		this.recUpdateDatetime = recUpdateDatetime;
//		this.recCreateBy = recCreateBy;
//		this.recCreateDatetime = recCreateDatetime;
//	}
//
//	/** full constructor */
//	public OrderCredit(String siteId, String creditType, Float creditTotal,
//			String creditReference1, String creditReference2,
//			String creditReference3, String creditReference4,
//			String creditReference5, Date creditDatetime, String recUpdateBy,
//			Date recUpdateDatetime, String recCreateBy, Date recCreateDatetime,
//			OrderHeader orderHeader) {
//		this.siteId = siteId;
//		this.creditType = creditType;
//		this.creditTotal = creditTotal;
//		this.creditReference1 = creditReference1;
//		this.creditReference2 = creditReference2;
//		this.creditReference3 = creditReference3;
//		this.creditReference4 = creditReference4;
//		this.creditReference5 = creditReference5;
//		this.creditDatetime = creditDatetime;
//		this.recUpdateBy = recUpdateBy;
//		this.recUpdateDatetime = recUpdateDatetime;
//		this.recCreateBy = recCreateBy;
//		this.recCreateDatetime = recCreateDatetime;
//		this.orderHeader = orderHeader;
//	}
//
//	// Property accessors
//	public Long getOrderCreditId() {
//		return this.orderCreditId;
//	}
//
//	public void setOrderCreditId(Long orderCreditId) {
//		this.orderCreditId = orderCreditId;
//	}
//
//	public String getSiteId() {
//		return this.siteId;
//	}
//
//	public void setSiteId(String siteId) {
//		this.siteId = siteId;
//	}
//
//	public String getCreditType() {
//		return this.creditType;
//	}
//
//	public void setCreditType(String creditType) {
//		this.creditType = creditType;
//	}
//
//	public Float getCreditTotal() {
//		return this.creditTotal;
//	}
//
//	public void setCreditTotal(Float creditTotal) {
//		this.creditTotal = creditTotal;
//	}
//
//	public String getCreditReference1() {
//		return this.creditReference1;
//	}
//
//	public void setCreditReference1(String creditReference1) {
//		this.creditReference1 = creditReference1;
//	}
//
//	public String getCreditReference2() {
//		return this.creditReference2;
//	}
//
//	public void setCreditReference2(String creditReference2) {
//		this.creditReference2 = creditReference2;
//	}
//
//	public String getCreditReference3() {
//		return this.creditReference3;
//	}
//
//	public void setCreditReference3(String creditReference3) {
//		this.creditReference3 = creditReference3;
//	}
//
//	public String getCreditReference4() {
//		return this.creditReference4;
//	}
//
//	public void setCreditReference4(String creditReference4) {
//		this.creditReference4 = creditReference4;
//	}
//
//	public String getCreditReference5() {
//		return this.creditReference5;
//	}
//
//	public void setCreditReference5(String creditReference5) {
//		this.creditReference5 = creditReference5;
//	}
//
//	public Date getCreditDatetime() {
//		return this.creditDatetime;
//	}
//
//	public void setCreditDatetime(Date creditDatetime) {
//		this.creditDatetime = creditDatetime;
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
//	public OrderHeader getOrderHeader() {
//		return this.orderHeader;
//	}
//
//	public void setOrderHeader(OrderHeader orderHeader) {
//		this.orderHeader = orderHeader;
//	}
//
//}
