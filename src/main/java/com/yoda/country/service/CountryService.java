package com.yoda.country.service;

import java.util.List;

import com.yoda.country.model.Country;

public interface CountryService {
	List<Country> getBySiteId(long siteId);

	Country getCountry(long siteId, String countryCode);
}
