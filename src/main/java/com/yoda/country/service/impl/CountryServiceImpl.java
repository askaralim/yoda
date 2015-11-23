//package com.yoda.country.service.impl;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.yoda.country.dao.CountryDAO;
//import com.yoda.country.model.Country;
//import com.yoda.country.service.CountryService;
//
//@Service
//public class CountryServiceImpl implements CountryService {
//
//	@Autowired
//	private CountryDAO countryDAO;
//
//	public List<Country> getBySiteId(long siteId) {
//		return countryDAO.getBySiteId(siteId);
//	}
//
//	public Country getCountry(long siteId, String countryCode) {
//		return countryDAO.getByS_CS(siteId, countryCode);
//	}
//}
