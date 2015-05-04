package com.yoda.country.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.yoda.BaseEntity;
import com.yoda.state.model.State;

@Entity
@Table(name = "country")
public class Country extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "country_id")
	private long countryId;

	@Column(name = "site_id")
	private long siteId;

	@Column(name = "country_code")
	private String countryCode;

	@Column(name = "country_name")
	private String countryName;

	@Column(name = "rec_update_by")
	private String recUpdateBy;

	@Column(name = "rec_update_datetime")
	private Date recUpdateDatetime;

	@Column(name = "rec_create_by")
	private String recCreateBy;

	@Column(name = "rec_create_datetime")
	private Date recCreateDatetime;

	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	@JoinColumn(name = "country_id")
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