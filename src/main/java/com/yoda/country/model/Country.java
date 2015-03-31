package com.yoda.country.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.yoda.BaseEntity;
import com.yoda.state.model.State;

public class Country extends BaseEntity {

	private long countryId;

	private long siteId;

	private String countryCode;

	private String countryName;

	private String recUpdateBy;

	private Date recUpdateDatetime;

	private String recCreateBy;

	private Date recCreateDatetime;

	private Set<State> states = new HashSet<State>(0);

//	private Set<Tax> taxes = new HashSet<Tax>(0);
//
//	private ShippingRegion shippingRegion;

	public long getCountryId() {
		return this.countryId;
	}

	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}

	public long getSiteId() {
		return this.siteId;
	}

	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
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

	public Set<State> getStates() {
		return this.states;
	}

	public void setStates(Set<State> states) {
		this.states = states;
	}

//	public Set<Tax> getTaxes() {
//		return this.taxes;
//	}
//
//	public void setTaxes(Set<Tax> taxes) {
//		this.taxes = taxes;
//	}
//
//	public ShippingRegion getShippingRegion() {
//		return this.shippingRegion;
//	}
//
//	public void setShippingRegion(ShippingRegion shippingRegion) {
//		this.shippingRegion = shippingRegion;
//	}
}