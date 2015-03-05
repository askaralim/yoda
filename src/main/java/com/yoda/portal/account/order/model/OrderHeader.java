package com.yoda.portal.account.order.model;//package com.yoda.portal.account.order.model;
//
//import java.util.Date;
//import java.util.HashSet;
//import java.util.Set;
//
//import com.yoda.BaseEntity;
//
//public class OrderHeader extends BaseEntity {
//
//	// Fields
//
//	private Long orderHeaderId;
//
//	private long siteId;
//
//	private String orderNum;
//
//	private String custFirstName;
//
//	private String custMiddleName;
//
//	private String custLastName;
//
//	private String custEmail;
//
//	private String custAddressLine1;
//
//	private String custAddressLine2;
//
//	private String custCityName;
//
//	private String custStateName;
//
//	private String custStateCode;
//
//	private String custCountryName;
//
//	private String custCountryCode;
//
//	private String custZipCode;
//
//	private String custPhoneNum;
//
//	private String custFaxNum;
//
//	private Float priceTotal;
//
//	private Float shippingTotal;
//
//	private Float taxTotal;
//
//	private Float orderTotal;
//
//	private String paymentGateway;
//
//	private String paymentType;
//
//	private String creditCardDesc;
//
//	private String custCreditCardNum;
//
//	private String shippingMethodName;
//
//	private Date shippedDatetime;
//
//	private String orderStatus;
//
//	private Date orderDatetime;
//
//	private String recUpdateBy;
//
//	private Date recUpdateDatetime;
//
//	private String recCreateBy;
//
//	private Date recCreateDatetime;
//
//	private Set<OrderDetail> orderDetails = new HashSet<OrderDetail>(0);
//
//	private Set<OrderTax> orderTaxes = new HashSet<OrderTax>(0);
//
//	private Set<OrderTracking> orderTrackings = new HashSet<OrderTracking>(0);
//
//	private Set<CreditHeader> creditHeaders = new HashSet<CreditHeader>(0);
//
//	private ShippingMethod shppingMethod;
//
//	private Customer customer;
//
//	private PaymentTran paymentTran;
//
//	private VoidOrder voidOrder;
//
//	// Constructors
//
//	/** default constructor */
//	public OrderHeader() {
//	}
//
//	/** minimal constructor */
////	public OrderHeader(String siteId, String orderNum, String custFirstName,
////			String custMiddleName, String custLastName, String custEmail,
////			String custAddressLine1, String custAddressLine2,
////			String custCityName, String custStateName, String custStateCode,
////			String custCountryName, String custCountryCode, String custZipCode,
////			String custPhoneNum, String custFaxNum, Float priceTotal,
////			Float shippingTotal, Float taxTotal, Float orderTotal,
////			String paymentGateway, String paymentType,
////			String shippingMethodName, String orderStatus, Date orderDatetime,
////			String recUpdateBy, Date recUpdateDatetime, String recCreateBy,
////			Date recCreateDatetime) {
////		this.siteId = siteId;
////		this.orderNum = orderNum;
////		this.custFirstName = custFirstName;
////		this.custMiddleName = custMiddleName;
////		this.custLastName = custLastName;
////		this.custEmail = custEmail;
////		this.custAddressLine1 = custAddressLine1;
////		this.custAddressLine2 = custAddressLine2;
////		this.custCityName = custCityName;
////		this.custStateName = custStateName;
////		this.custStateCode = custStateCode;
////		this.custCountryName = custCountryName;
////		this.custCountryCode = custCountryCode;
////		this.custZipCode = custZipCode;
////		this.custPhoneNum = custPhoneNum;
////		this.custFaxNum = custFaxNum;
////		this.priceTotal = priceTotal;
////		this.shippingTotal = shippingTotal;
////		this.taxTotal = taxTotal;
////		this.orderTotal = orderTotal;
////		this.paymentGateway = paymentGateway;
////		this.paymentType = paymentType;
////		this.shippingMethodName = shippingMethodName;
////		this.orderStatus = orderStatus;
////		this.orderDatetime = orderDatetime;
////		this.recUpdateBy = recUpdateBy;
////		this.recUpdateDatetime = recUpdateDatetime;
////		this.recCreateBy = recCreateBy;
////		this.recCreateDatetime = recCreateDatetime;
////	}
////
////	/** full constructor */
////	public OrderHeader(String siteId, String orderNum, String custFirstName,
////			String custMiddleName, String custLastName, String custEmail,
////			String custAddressLine1, String custAddressLine2,
////			String custCityName, String custStateName, String custStateCode,
////			String custCountryName, String custCountryCode, String custZipCode,
////			String custPhoneNum, String custFaxNum, Float priceTotal,
////			Float shippingTotal, Float taxTotal, Float orderTotal,
////			String paymentGateway, String paymentType, String creditCardDesc,
////			String custCreditCardNum, String shippingMethodName,
////			Date shippedDatetime, String orderStatus, Date orderDatetime,
////			String recUpdateBy, Date recUpdateDatetime, String recCreateBy,
////			Date recCreateDatetime, Set<OrderDetail> orderDetails,
////			Set<OrderTax> orderTaxes, Set<OrderTracking> orderTrackings,
////			Set<CreditHeader> creditHeaders, ShippingMethod shppingMethod,
////			Customer customer, PaymentTran paymentTran, VoidOrder voidOrder) {
////		this.siteId = siteId;
////		this.orderNum = orderNum;
////		this.custFirstName = custFirstName;
////		this.custMiddleName = custMiddleName;
////		this.custLastName = custLastName;
////		this.custEmail = custEmail;
////		this.custAddressLine1 = custAddressLine1;
////		this.custAddressLine2 = custAddressLine2;
////		this.custCityName = custCityName;
////		this.custStateName = custStateName;
////		this.custStateCode = custStateCode;
////		this.custCountryName = custCountryName;
////		this.custCountryCode = custCountryCode;
////		this.custZipCode = custZipCode;
////		this.custPhoneNum = custPhoneNum;
////		this.custFaxNum = custFaxNum;
////		this.priceTotal = priceTotal;
////		this.shippingTotal = shippingTotal;
////		this.taxTotal = taxTotal;
////		this.orderTotal = orderTotal;
////		this.paymentGateway = paymentGateway;
////		this.paymentType = paymentType;
////		this.creditCardDesc = creditCardDesc;
////		this.custCreditCardNum = custCreditCardNum;
////		this.shippingMethodName = shippingMethodName;
////		this.shippedDatetime = shippedDatetime;
////		this.orderStatus = orderStatus;
////		this.orderDatetime = orderDatetime;
////		this.recUpdateBy = recUpdateBy;
////		this.recUpdateDatetime = recUpdateDatetime;
////		this.recCreateBy = recCreateBy;
////		this.recCreateDatetime = recCreateDatetime;
////		this.orderDetails = orderDetails;
////		this.orderTaxes = orderTaxes;
////		this.orderTrackings = orderTrackings;
////		this.creditHeaders = creditHeaders;
////		this.shppingMethod = shppingMethod;
////		this.customer = customer;
////		this.paymentTran = paymentTran;
////		this.voidOrder = voidOrder;
////	}
//
//	// Property accessors
//	public Long getOrderHeaderId() {
//		return this.orderHeaderId;
//	}
//
//	public void setOrderHeaderId(Long orderHeaderId) {
//		this.orderHeaderId = orderHeaderId;
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
//	public String getOrderNum() {
//		return this.orderNum;
//	}
//
//	public void setOrderNum(String orderNum) {
//		this.orderNum = orderNum;
//	}
//
//	public String getCustFirstName() {
//		return this.custFirstName;
//	}
//
//	public void setCustFirstName(String custFirstName) {
//		this.custFirstName = custFirstName;
//	}
//
//	public String getCustMiddleName() {
//		return this.custMiddleName;
//	}
//
//	public void setCustMiddleName(String custMiddleName) {
//		this.custMiddleName = custMiddleName;
//	}
//
//	public String getCustLastName() {
//		return this.custLastName;
//	}
//
//	public void setCustLastName(String custLastName) {
//		this.custLastName = custLastName;
//	}
//
//	public String getCustEmail() {
//		return this.custEmail;
//	}
//
//	public void setCustEmail(String custEmail) {
//		this.custEmail = custEmail;
//	}
//
//	public String getCustAddressLine1() {
//		return this.custAddressLine1;
//	}
//
//	public void setCustAddressLine1(String custAddressLine1) {
//		this.custAddressLine1 = custAddressLine1;
//	}
//
//	public String getCustAddressLine2() {
//		return this.custAddressLine2;
//	}
//
//	public void setCustAddressLine2(String custAddressLine2) {
//		this.custAddressLine2 = custAddressLine2;
//	}
//
//	public String getCustCityName() {
//		return this.custCityName;
//	}
//
//	public void setCustCityName(String custCityName) {
//		this.custCityName = custCityName;
//	}
//
//	public String getCustStateName() {
//		return this.custStateName;
//	}
//
//	public void setCustStateName(String custStateName) {
//		this.custStateName = custStateName;
//	}
//
//	public String getCustStateCode() {
//		return this.custStateCode;
//	}
//
//	public void setCustStateCode(String custStateCode) {
//		this.custStateCode = custStateCode;
//	}
//
//	public String getCustCountryName() {
//		return this.custCountryName;
//	}
//
//	public void setCustCountryName(String custCountryName) {
//		this.custCountryName = custCountryName;
//	}
//
//	public String getCustCountryCode() {
//		return this.custCountryCode;
//	}
//
//	public void setCustCountryCode(String custCountryCode) {
//		this.custCountryCode = custCountryCode;
//	}
//
//	public String getCustZipCode() {
//		return this.custZipCode;
//	}
//
//	public void setCustZipCode(String custZipCode) {
//		this.custZipCode = custZipCode;
//	}
//
//	public String getCustPhoneNum() {
//		return this.custPhoneNum;
//	}
//
//	public void setCustPhoneNum(String custPhoneNum) {
//		this.custPhoneNum = custPhoneNum;
//	}
//
//	public String getCustFaxNum() {
//		return this.custFaxNum;
//	}
//
//	public void setCustFaxNum(String custFaxNum) {
//		this.custFaxNum = custFaxNum;
//	}
//
//	public Float getPriceTotal() {
//		return this.priceTotal;
//	}
//
//	public void setPriceTotal(Float priceTotal) {
//		this.priceTotal = priceTotal;
//	}
//
//	public Float getShippingTotal() {
//		return this.shippingTotal;
//	}
//
//	public void setShippingTotal(Float shippingTotal) {
//		this.shippingTotal = shippingTotal;
//	}
//
//	public Float getTaxTotal() {
//		return this.taxTotal;
//	}
//
//	public void setTaxTotal(Float taxTotal) {
//		this.taxTotal = taxTotal;
//	}
//
//	public Float getOrderTotal() {
//		return this.orderTotal;
//	}
//
//	public void setOrderTotal(Float orderTotal) {
//		this.orderTotal = orderTotal;
//	}
//
//	public String getPaymentGateway() {
//		return this.paymentGateway;
//	}
//
//	public void setPaymentGateway(String paymentGateway) {
//		this.paymentGateway = paymentGateway;
//	}
//
//	public String getPaymentType() {
//		return this.paymentType;
//	}
//
//	public void setPaymentType(String paymentType) {
//		this.paymentType = paymentType;
//	}
//
//	public String getCreditCardDesc() {
//		return this.creditCardDesc;
//	}
//
//	public void setCreditCardDesc(String creditCardDesc) {
//		this.creditCardDesc = creditCardDesc;
//	}
//
//	public String getCustCreditCardNum() {
//		return this.custCreditCardNum;
//	}
//
//	public void setCustCreditCardNum(String custCreditCardNum) {
//		this.custCreditCardNum = custCreditCardNum;
//	}
//
//	public String getShippingMethodName() {
//		return this.shippingMethodName;
//	}
//
//	public void setShippingMethodName(String shippingMethodName) {
//		this.shippingMethodName = shippingMethodName;
//	}
//
//	public Date getShippedDatetime() {
//		return this.shippedDatetime;
//	}
//
//	public void setShippedDatetime(Date shippedDatetime) {
//		this.shippedDatetime = shippedDatetime;
//	}
//
//	public String getOrderStatus() {
//		return this.orderStatus;
//	}
//
//	public void setOrderStatus(String orderStatus) {
//		this.orderStatus = orderStatus;
//	}
//
//	public Date getOrderDatetime() {
//		return this.orderDatetime;
//	}
//
//	public void setOrderDatetime(Date orderDatetime) {
//		this.orderDatetime = orderDatetime;
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
//	public Set<OrderDetail> getOrderDetails() {
//		return this.orderDetails;
//	}
//
//	public void setOrderDetails(Set<OrderDetail> orderDetails) {
//		this.orderDetails = orderDetails;
//	}
//
//	public Set<OrderTax> getOrderTaxes() {
//		return this.orderTaxes;
//	}
//
//	public void setOrderTaxes(Set<OrderTax> orderTaxes) {
//		this.orderTaxes = orderTaxes;
//	}
//
//	public Set<OrderTracking> getOrderTrackings() {
//		return this.orderTrackings;
//	}
//
//	public void setOrderTrackings(Set<OrderTracking> orderTrackings) {
//		this.orderTrackings = orderTrackings;
//	}
//
//	public Set<CreditHeader> getCreditHeaders() {
//		return this.creditHeaders;
//	}
//
//	public void setCreditHeaders(Set<CreditHeader> creditHeaders) {
//		this.creditHeaders = creditHeaders;
//	}
//
//	public ShippingMethod getShppingMethod() {
//		return this.shppingMethod;
//	}
//
//	public void setShppingMethod(ShippingMethod shppingMethod) {
//		this.shppingMethod = shppingMethod;
//	}
//
//	public Customer getCustomer() {
//		return this.customer;
//	}
//
//	public void setCustomer(Customer customer) {
//		this.customer = customer;
//	}
//
//	public PaymentTran getPaymentTran() {
//		return this.paymentTran;
//	}
//
//	public void setPaymentTran(PaymentTran paymentTran) {
//		this.paymentTran = paymentTran;
//	}
//
//	public VoidOrder getVoidOrder() {
//		return this.voidOrder;
//	}
//
//	public void setVoidOrder(VoidOrder voidOrder) {
//		this.voidOrder = voidOrder;
//	}
//
//}
