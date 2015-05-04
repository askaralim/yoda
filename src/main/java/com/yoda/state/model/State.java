package com.yoda.state.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.yoda.BaseEntity;
import com.yoda.country.model.Country;

@Entity
@Table(name = "state")
public class State extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "state_id")
	private long stateId;

	@Column(name = "site_id")
	private long siteId;

	@Column(name = "state_code")
	private String stateCode;

	@Column(name = "state_name")
	private String stateName;

	@Column(name = "rec_update_by")
	private String recUpdateBy;

	@Column(name = "rec_update_datetime")
	private Date recUpdateDatetime;

	@Column(name = "rec_create_by")
	private String recCreateBy;

	@Column(name = "rec_create_datetime")
	private Date recCreateDatetime;

//	private Set<Tax> taxes = new HashSet<Tax>(0);

	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;

//	@ManyToOne
//	@JoinColumn(name = "shipping_region_id")
//	private ShippingRegion shippingRegion;

	public State() {
	}

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
