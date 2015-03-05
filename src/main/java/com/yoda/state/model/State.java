package com.yoda.state.model;

import java.util.Date;

import com.yoda.BaseEntity;
import com.yoda.country.model.Country;

public class State extends BaseEntity {

	private long stateId;

	private long siteId;

	private String stateCode;

	private String stateName;

	private String recUpdateBy;

	private Date recUpdateDatetime;

	private String recCreateBy;

	private Date recCreateDatetime;

//	private Set<Tax> taxes = new HashSet<Tax>(0);

	private Country country;

//	private ShippingRegion shippingRegion;

	public State() {
	}

//	public State(long siteId, String stateCode, String stateName,
//			String recUpdateBy, Date recUpdateDatetime, String recCreateBy,
//			Date recCreateDatetime) {
//		this.siteId = siteId;
//		this.stateCode = stateCode;
//		this.stateName = stateName;
//		this.recUpdateBy = recUpdateBy;
//		this.recUpdateDatetime = recUpdateDatetime;
//		this.recCreateBy = recCreateBy;
//		this.recCreateDatetime = recCreateDatetime;
//	}
//
//	public State(long siteId, String stateCode, String stateName,
//			String recUpdateBy, Date recUpdateDatetime, String recCreateBy,
//			Date recCreateDatetime, Set<Tax> taxes, Country country,
//			ShippingRegion shippingRegion) {
//		this.siteId = siteId;
//		this.stateCode = stateCode;
//		this.stateName = stateName;
//		this.recUpdateBy = recUpdateBy;
//		this.recUpdateDatetime = recUpdateDatetime;
//		this.recCreateBy = recCreateBy;
//		this.recCreateDatetime = recCreateDatetime;
//		this.taxes = taxes;
//		this.country = country;
//		this.shippingRegion = shippingRegion;
//	}

	public long getStateId() {
		return this.stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public long getSiteId() {
		return this.siteId;
	}

	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}

	public String getStateCode() {
		return this.stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getStateName() {
		return this.stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
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

//	public Set<Tax> getTaxes() {
//		return this.taxes;
//	}
//
//	public void setTaxes(Set<Tax> taxes) {
//		this.taxes = taxes;
//	}

	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

//	public ShippingRegion getShippingRegion() {
//		return this.shippingRegion;
//	}
//
//	public void setShippingRegion(ShippingRegion shippingRegion) {
//		this.shippingRegion = shippingRegion;
//	}

}
