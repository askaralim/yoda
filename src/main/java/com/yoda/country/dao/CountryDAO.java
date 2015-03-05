package com.yoda.country.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.yoda.BaseDAO;
import com.yoda.country.model.Country;

@Repository
public class CountryDAO extends BaseDAO<Country> {

	private static final String FINDER_COLUNN_SITE_ID = "FROM country in class Country where country.siteId = ? order by countryName";
	private static final String FINDER_COLUNN_SITE_ID_COUNTRY_CODE = "from Country where siteId = :siteId and countryCode = ?";

	public List<Country> getBySiteId(long siteId) {

		return find(FINDER_COLUNN_SITE_ID, siteId);
	}

	public Country getByS_CS(long siteId, String countryCode) {

		Session session = getSession();

		Query query = session.createQuery(FINDER_COLUNN_SITE_ID_COUNTRY_CODE);

		query.setLong("siteId", siteId);
		query.setString("countryCode", countryCode);

		List list = query.list();

		Country country = null;
		if (list.size() > 0) {
			country = (Country) list.iterator().next();
		}

		return country;
	}

//	public Country load(long siteId, Long countryId)
//			throws SecurityException, Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		Country country = (Country) session.get(Country.class, countryId);
//
//		if (country.getSiteId() != siteId) {
//			throw new SecurityException();
//		}
//
//		return country;
//	}

//	public Country loadByCountryName(String siteId, String countryName)
//			throws SecurityException, Exception {
//
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		Query query = session
//			.createQuery("from Country where siteId = :siteId and countryName = :countryName");
//		query.setString("siteId", siteId);
//		query.setString("countryName", countryName);
//		Iterator iterator = query.iterate();
//		if (iterator.hasNext()) {
//			return (Country) iterator.next();
//		}
//		return null;
//	}

//	public Country loadByCountryCode(String siteId, String countryCode)
//			throws SecurityException, Exception {
//
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		Query query = session
//				.createQuery("from Country where siteId = :siteId and countryCode = :countryCode");
//		query.setString("siteId", siteId);
//		query.setString("countryCode", countryCode);
//		Iterator iterator = query.iterate();
//		if (iterator.hasNext()) {
//			return (Country) iterator.next();
//		}
//		return null;
//	}
}