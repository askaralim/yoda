package com.yoda.site;//package com.yoda.controlpanel.site;
//
//import java.util.Date;
//import java.util.Iterator;
//import java.util.Set;
//import java.util.Vector;
//
//import org.apache.commons.beanutils.PropertyUtils;
//import org.hibernate.Query;
//import org.hibernate.Session;
//
//import com.yoda.controlpanel.country.dao.CountryDAO;
//import com.yoda.controlpanel.country.model.Country;
//import com.yoda.controlpanel.menu.model.Menu;
//import com.yoda.controlpanel.section.model.Section;
//import com.yoda.controlpanel.shipping.model.ShippingMethod;
//import com.yoda.controlpanel.shipping.model.ShippingMethodRegion;
//import com.yoda.controlpanel.shipping.model.ShippingMethodRegionType;
//import com.yoda.controlpanel.shipping.model.ShippingRate;
//import com.yoda.controlpanel.shipping.model.ShippingRegion;
//import com.yoda.controlpanel.shipping.model.ShippingType;
//import com.yoda.controlpanel.site.model.Site;
//import com.yoda.controlpanel.state.dao.StateDAO;
//import com.yoda.controlpanel.state.model.State;
//import com.yoda.controlpanel.tax.model.Tax;
//import com.yoda.controlpanel.template.model.Template;
//import com.yoda.controlpanel.user.model.User;
//import com.yoda.hibernate.CreditCard;
//import com.yoda.hibernate.Currency;
//import com.yoda.hibernate.HibernateConnection;
//import com.yoda.util.Constants;
//
//public class SiteLoader {
//	/*
//	 * Used to load up tables when a new site is initially creatd. Tables to be
//	 * loaded. Country State Currency Menu Section Tax Template ShippingRegion
//	 * CreditCard
//	 */
//	long siteId = null;
//	long userId = null;
//
//	public SiteLoader(long siteId, long userId) {
//		this.siteId = siteId;
//		this.userId = userId;
//	}
//
//	public void load() throws Exception {
//		loadCountry();
//		loadState();
//		loadCurrency();
//		loadMenu();
//		loadSection();
//		loadTax();
//		loadTemplate();
//		loadShippingType();
//		loadShippingRegion();
//		loadShippingMethod();
//		loadCreditCard();
//	}
//
//	public void remove() throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		String sql = null;
//		Query query = null;
//		Iterator iterator = null;
//		Iterator children = null;
//		Vector<Object> list = null;
//
//		Site site = (Site) session.load(Site.class, siteId);
//		children = site.getUsers().iterator();
//		list = new Vector<Object>();
//		while (children.hasNext()) {
//			list.add(children.next());
//		}
//		children = list.iterator();
//		while (children.hasNext()) {
//			User user = (User) children.next();
//			user.getSites().remove(site);
//		}
//
//		sql = "delete from Currency where siteId = :siteId";
//		query = session.createQuery(sql);
//		query.setString("siteId", siteId);
//		query.executeUpdate();
//
//		sql = "delete from Menu where siteId = :siteId";
//		query = session.createQuery(sql);
//		query.setString("siteId", siteId);
//		query.executeUpdate();
//
//		sql = "delete from Section where siteId = :siteId";
//		query = session.createQuery(sql);
//		query.setString("siteId", siteId);
//		query.executeUpdate();
//
//		sql = "from Tax where siteId = :siteId";
//		query = session.createQuery(sql);
//		query.setString("siteId", siteId);
//		iterator = query.iterate();
//		while (iterator.hasNext()) {
//			Tax tax = (Tax) iterator.next();
//
//			children = tax.getCountries().iterator();
//			list = new Vector<Object>();
//			while (children.hasNext()) {
//				list.add(children.next());
//			}
//			children = list.iterator();
//			while (children.hasNext()) {
//				Country country = (Country) children.next();
//				tax.getCountries().remove(country);
//			}
//
//			children = tax.getStates().iterator();
//			list = new Vector<Object>();
//			while (children.hasNext()) {
//				list.add(children.next());
//			}
//			children = list.iterator();
//			while (children.hasNext()) {
//				State state = (State) children.next();
//				tax.getStates().remove(state);
//			}
//
//			session.delete(tax);
//		}
//
//		sql = "delete from Template where siteId = :siteId";
//		query = session.createQuery(sql);
//		query.setString("siteId", siteId);
//		query.executeUpdate();
//
//		sql = "from State where siteId = :siteId";
//		query = session.createQuery(sql);
//		query.setString("siteId", siteId);
//		iterator = query.iterate();
//		while (iterator.hasNext()) {
//			State state = (State) iterator.next();
//			state.setCountry(null);
//			session.delete(state);
//		}
//
//		sql = "from Country where siteId = :siteId";
//		query = session.createQuery(sql);
//		query.setString("siteId", siteId);
//		iterator = query.iterate();
//		while (iterator.hasNext()) {
//			Country country = (Country) iterator.next();
//			session.delete(country);
//		}
//
//		sql = "from ShippingRate where siteId = :siteId";
//		query = session.createQuery(sql);
//		query.setString("siteId", siteId);
//		iterator = query.iterate();
//		while (iterator.hasNext()) {
//			ShippingRate shippingRate = (ShippingRate) iterator.next();
//			session.delete(shippingRate);
//		}
//
//		sql = "from ShippingMethod where siteId = :siteId";
//		query = session.createQuery(sql);
//		query.setString("siteId", siteId);
//		iterator = query.iterate();
//		while (iterator.hasNext()) {
//			ShippingMethod shippingMethod = (ShippingMethod) iterator.next();
//			session.delete(shippingMethod);
//		}
//
//		sql = "from ShippingRegion where siteId = :siteId";
//		query = session.createQuery(sql);
//		query.setString("siteId", siteId);
//		iterator = query.iterate();
//		while (iterator.hasNext()) {
//			ShippingRegion shippingRegion = (ShippingRegion) iterator.next();
//			session.delete(shippingRegion);
//		}
//
//		sql = "from ShippingType where siteId = :siteId";
//		query = session.createQuery(sql);
//		query.setString("siteId", siteId);
//		iterator = query.iterate();
//		while (iterator.hasNext()) {
//			ShippingType shippingType = (ShippingType) iterator.next();
//			session.delete(shippingType);
//		}
//	}
//
//	public void loadCountry() throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		String sql = "from Country country where siteId = :siteId order by countryCode";
//		Query query = session.createQuery(sql);
//		query.setString("siteId", Constants.SITE_DEFAULT);
//		Iterator iterator = query.iterate();
//		while (iterator.hasNext()) {
//			Country master = (Country) iterator.next();
//			Country country = new Country();
//			PropertyUtils.copyProperties(country, master);
//			country.setSiteId(siteId);
//			country.setCountryId(null);
//			country.setRecUpdateBy(userId);
//			country.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//			country.setRecCreateBy(userId);
//			country.setRecCreateDatetime(new Date(System.currentTimeMillis()));
//			country.setShippingRegion(null);
//			country.setStates(null);
//			country.setTaxes(null);
//			session.save(country);
//		}
//	}
//
//	public void loadState() throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		String sql = "from State where siteId = :siteId order by stateCode";
//		Query query = session.createQuery(sql);
//		query.setString("siteId", Constants.SITE_DEFAULT);
//		Iterator iterator = query.iterate();
//		while (iterator.hasNext()) {
//			State master = (State) iterator.next();
//			State state = new State();
//			PropertyUtils.copyProperties(state, master);
//			state.setSiteId(siteId);
//			state.setStateId(null);
//			state.setRecUpdateBy(userId);
//			state.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//			state.setRecCreateBy(userId);
//			state.setRecCreateDatetime(new Date(System.currentTimeMillis()));
//			state.setShippingRegion(null);
//			Country mc = master.getCountry();
//			Country country = null;
//			if (mc != null) {
//				country = CountryDAO.loadByCountryName(siteId,
//						mc.getCountryName());
//			}
//			state.setCountry(country);
//			state.setTaxes(null);
//			session.save(state);
//		}
//	}
//
//	public void loadCurrency() throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		String sql = "from Currency where siteId = :siteId order by currencyCode";
//		Query query = session.createQuery(sql);
//		query.setString("siteId", Constants.SITE_DEFAULT);
//		Iterator iterator = query.iterate();
//		while (iterator.hasNext()) {
//			Currency master = (Currency) iterator.next();
//			Currency currency = new Currency();
//			PropertyUtils.copyProperties(currency, master);
//			currency.setSiteId(siteId);
//			currency.setCurrencyId(null);
//			currency.setRecUpdateBy(userId);
//			currency.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//			currency.setRecCreateBy(userId);
//			currency.setRecCreateDatetime(new Date(System.currentTimeMillis()));
//			session.save(currency);
//		}
//	}
//
//	public void loadMenu() throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		String sql = "from Menu where siteId = :siteId order by menuSetName, menuName";
//		Query query = session.createQuery(sql);
//		query.setString("siteId", Constants.SITE_DEFAULT);
//		Iterator iterator = query.iterate();
//		while (iterator.hasNext()) {
//			Menu master = (Menu) iterator.next();
//			Menu menu = new Menu();
//			PropertyUtils.copyProperties(menu, master);
//			menu.setSiteId(siteId);
//			menu.setMenuId(null);
//			menu.setRecUpdateBy(userId);
//			menu.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//			menu.setRecCreateBy(userId);
//			menu.setRecCreateDatetime(new Date(System.currentTimeMillis()));
//			menu.setSection(null);
//			menu.setContent(null);
//			menu.setItem(null);
//			session.save(menu);
//		}
//	}
//
//	public void loadSection() throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		String sql = "from Section where siteId = :siteId order by sectionTitle";
//		Query query = session.createQuery(sql);
//		query.setString("siteId", Constants.SITE_DEFAULT);
//		Iterator iterator = query.iterate();
//		while (iterator.hasNext()) {
//			Section master = (Section) iterator.next();
//			Section section = new Section();
//			PropertyUtils.copyProperties(section, master);
//			section.setSiteId(siteId);
//			section.setSectionId(null);
//			section.setRecUpdateBy(userId);
//			section.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//			section.setRecCreateBy(userId);
//			section.setRecCreateDatetime(new Date(System.currentTimeMillis()));
//			section.setContents(null);
//			section.setItems(null);
//			section.setMenus(null);
//			session.save(section);
//		}
//	}
//
//	public void loadTax() throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		String sql = "from Tax where siteId = :siteId order by taxCode";
//		Query query = session.createQuery(sql);
//		query.setString("siteId", Constants.SITE_DEFAULT);
//		Iterator iterator = query.iterate();
//		while (iterator.hasNext()) {
//			Tax master = (Tax) iterator.next();
//			Tax tax = new Tax();
//			Set<Country> countries = tax.getCountries();
//			Set<State> states = tax.getStates();
//			PropertyUtils.copyProperties(tax, master);
//			tax.setSiteId(siteId);
//			tax.setTaxId(null);
//			tax.setRecUpdateBy(userId);
//			tax.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//			tax.setRecCreateBy(userId);
//			tax.setRecCreateDatetime(new Date(System.currentTimeMillis()));
//			tax.setCountries(countries);
//			tax.setStates(states);
//			Iterator it = null;
//			if (master.getCountries() != null) {
//				it = master.getCountries().iterator();
//				while (it.hasNext()) {
//					Country mc = (Country) it.next();
//					Country country = CountryDAO.loadByCountryName(siteId,
//							mc.getCountryName());
//					tax.getCountries().add(country);
//				}
//			}
//			if (master.getStates() != null) {
//				it = master.getStates().iterator();
//				while (it.hasNext()) {
//					State mc = (State) it.next();
//					State state = StateDAO.loadByStateName(siteId,
//							mc.getStateName());
//					tax.getStates().add(state);
//				}
//			}
//			session.save(tax);
//		}
//	}
//
//	public void loadTemplate() throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		String sql = "from Template where siteId = :siteId order by templateName";
//		Query query = session.createQuery(sql);
//		query.setString("siteId", Constants.SITE_DEFAULT);
//		Iterator iterator = query.iterate();
//		while (iterator.hasNext()) {
//			Template master = (Template) iterator.next();
//			Template template = new Template();
//			PropertyUtils.copyProperties(template, master);
//			template.setSiteId(siteId);
//			template.setTemplateId(null);
//			template.setRecUpdateBy(userId);
//			template.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//			template.setRecCreateBy(userId);
//			template.setRecCreateDatetime(new Date(System.currentTimeMillis()));
//			session.save(template);
//		}
//	}
//
//	Vector<ShippingType> shippingTypes = new Vector<ShippingType>();
//
//	public void loadShippingType() throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		String sql = "from ShippingType where siteId = :siteId order by shippingTypeId";
//		Query query = session.createQuery(sql);
//		query.setString("siteId", Constants.SITE_DEFAULT);
//		Iterator iterator = query.iterate();
//		while (iterator.hasNext()) {
//			ShippingType master = (ShippingType) iterator.next();
//			ShippingType shippingType = new ShippingType();
//			PropertyUtils.copyProperties(shippingType, master);
//			shippingType.setSiteId(siteId);
//			shippingType.setRecUpdateBy(userId);
//			shippingType.setRecUpdateDatetime(new Date(System
//					.currentTimeMillis()));
//			shippingType.setRecCreateBy(userId);
//			shippingType.setRecCreateDatetime(new Date(System
//					.currentTimeMillis()));
//			shippingType.setShippingMethodRegionTypes(null);
//			shippingTypes.add(shippingType);
//			session.save(shippingType);
//		}
//	}
//
//	Vector<ShippingRegion> shippingRegions = new Vector<ShippingRegion>();
//
//	public void loadShippingRegion() throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		String sql = "from ShippingRegion where siteId = :siteId order by shippingRegionId";
//		Query query = session.createQuery(sql);
//		query.setString("siteId", Constants.SITE_DEFAULT);
//		Iterator iterator = query.iterate();
//		while (iterator.hasNext()) {
//			ShippingRegion master = (ShippingRegion) iterator.next();
//			ShippingRegion shippingRegion = new ShippingRegion();
//			Set<Country> countries = shippingRegion.getCountries();
//			Set<State> states = shippingRegion.getStates();
//			PropertyUtils.copyProperties(shippingRegion, master);
//			shippingRegion.setSiteId(siteId);
//			shippingRegion.setShippingRegionId(null);
//			shippingRegion.setRecUpdateBy(userId);
//			shippingRegion.setRecUpdateDatetime(new Date(System
//					.currentTimeMillis()));
//			shippingRegion.setRecCreateBy(userId);
//			shippingRegion.setRecCreateDatetime(new Date(System
//					.currentTimeMillis()));
//			shippingRegion.setCountries(countries);
//			shippingRegion.setStates(states);
//			shippingRegion.setShippingMethodRegions(null);
//			shippingRegion.setShippingMethodRegionTypes(null);
//			Iterator it = null;
//			if (master.getCountries() != null) {
//				it = master.getCountries().iterator();
//				while (it.hasNext()) {
//					Country mc = (Country) it.next();
//					Country country = CountryDAO.loadByCountryName(siteId,
//							mc.getCountryName());
//					shippingRegion.getCountries().add(country);
//				}
//			}
//			if (master.getStates() != null) {
//				it = master.getStates().iterator();
//				while (it.hasNext()) {
//					State mc = (State) it.next();
//					State state = StateDAO.loadByStateName(siteId,
//							mc.getStateName());
//					shippingRegion.getStates().add(state);
//				}
//			}
//			shippingRegions.add(shippingRegion);
//			session.save(shippingRegion);
//		}
//	}
//
//	public void loadShippingMethod() throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		String sql = "from ShippingMethod where siteId = :siteId order by shippingMethodId";
//		Query query = session.createQuery(sql);
//		query.setString("siteId", Constants.SITE_DEFAULT);
//		Iterator iterator = query.iterate();
//		while (iterator.hasNext()) {
//			ShippingMethod master = (ShippingMethod) iterator.next();
//			ShippingMethod shippingMethod = new ShippingMethod();
//			shippingMethod.setSiteId(siteId);
//			shippingMethod
//					.setShippingMethodName(master.getShippingMethodName());
//			shippingMethod.setSeqNum(master.getSeqNum());
//			shippingMethod.setPublished(master.getPublished());
//			shippingMethod.setRecUpdateBy(userId);
//			shippingMethod.setRecUpdateDatetime(new Date(System
//					.currentTimeMillis()));
//			shippingMethod.setRecCreateBy(userId);
//			shippingMethod.setRecCreateDatetime(new Date(System
//					.currentTimeMillis()));
//			if (master.getShippingMethodRegions() != null) {
//				Iterator it = master.getShippingMethodRegions().iterator();
//				while (it.hasNext()) {
//					ShippingMethodRegion m_shippingMethodRegion = (ShippingMethodRegion) it
//							.next();
//					ShippingMethodRegion shippingMethodRegion = new ShippingMethodRegion();
//					shippingMethodRegion.setSiteId(siteId);
//					shippingMethodRegion.setPublished(m_shippingMethodRegion
//							.getPublished());
//					shippingMethodRegion.setRecUpdateBy(userId);
//					shippingMethodRegion.setRecUpdateDatetime(new Date(System
//							.currentTimeMillis()));
//					shippingMethodRegion.setRecCreateBy(userId);
//					shippingMethodRegion.setRecCreateDatetime(new Date(System
//							.currentTimeMillis()));
//					shippingMethodRegion.setShippingMethod(shippingMethod);
//					ShippingRegion shippingRegion = getShippingRegion(m_shippingMethodRegion
//							.getShippingRegion().getShippingRegionName());
//					shippingMethodRegion.setShippingRegion(shippingRegion);
//
//					if (m_shippingMethodRegion.getShippingMethodRegionTypes() != null) {
//						Iterator it1 = m_shippingMethodRegion
//								.getShippingMethodRegionTypes().iterator();
//						while (it1.hasNext()) {
//							ShippingMethodRegionType m_shippingMethodRegionType = (ShippingMethodRegionType) it1
//									.next();
//							ShippingMethodRegionType shippingMethodRegionType = new ShippingMethodRegionType();
//							shippingMethodRegionType.setSiteId(siteId);
//							shippingMethodRegionType
//									.setPublished(m_shippingMethodRegionType
//											.getPublished());
//							shippingMethodRegionType.setRecUpdateBy(userId);
//							shippingMethodRegionType
//									.setRecUpdateDatetime(new Date(System
//											.currentTimeMillis()));
//							shippingMethodRegionType.setRecCreateBy(userId);
//							shippingMethodRegionType
//									.setRecCreateDatetime(new Date(System
//											.currentTimeMillis()));
//							shippingMethodRegionType
//									.setShippingRegion(shippingRegion);
//							shippingMethodRegionType
//									.setShippingMethod(shippingMethod);
//							shippingMethodRegionType
//									.setShippingType(getShippingType(m_shippingMethodRegionType
//											.getShippingType()
//											.getShippingTypeName()));
//
//							ShippingRate m_shippingRate = m_shippingMethodRegionType
//									.getShippingRate();
//							ShippingRate shippingRate = new ShippingRate();
//							PropertyUtils.copyProperties(shippingRate,
//									m_shippingRate);
//							shippingRate.setSiteId(siteId);
//							shippingRate.setPublished(m_shippingRate
//									.getPublished());
//							shippingRate.setRecUpdateBy(userId);
//							shippingRate.setRecUpdateDatetime(new Date(System
//									.currentTimeMillis()));
//							shippingRate.setRecCreateBy(userId);
//							shippingRate.setRecCreateDatetime(new Date(System
//									.currentTimeMillis()));
//							shippingMethodRegionType
//									.setShippingRate(shippingRate);
//
//							session.save(shippingRate);
//							session.save(shippingMethodRegionType);
//							shippingMethodRegion.getShippingMethodRegionTypes()
//									.add(shippingMethodRegionType);
//						}
//					}
//
//					shippingMethod.getShippingMethodRegions().add(
//							shippingMethodRegion);
//				}
//			}
//			session.save(shippingMethod);
//		}
//	}
//
//	public ShippingRegion getShippingRegion(String shippingRegionName) {
//		Iterator iterator = shippingRegions.iterator();
//		while (iterator.hasNext()) {
//			ShippingRegion shippingRegion = (ShippingRegion) iterator.next();
//			if (shippingRegion.getShippingRegionName().equals(
//					shippingRegionName)) {
//				return shippingRegion;
//			}
//		}
//		return null;
//	}
//
//	public ShippingType getShippingType(String shippingTypeName) {
//		Iterator iterator = shippingTypes.iterator();
//		while (iterator.hasNext()) {
//			ShippingType shippingType = (ShippingType) iterator.next();
//			if (shippingType.getShippingTypeName().equals(shippingTypeName)) {
//				return shippingType;
//			}
//		}
//		return null;
//	}
//
//	public void loadCreditCard() throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		String sql = "from CreditCard where siteId = :siteId order by creditCardId";
//		Query query = session.createQuery(sql);
//		query.setString("siteId", Constants.SITE_DEFAULT);
//		Iterator iterator = query.iterate();
//		while (iterator.hasNext()) {
//			CreditCard master = (CreditCard) iterator.next();
//			CreditCard creditCard = new CreditCard();
//			PropertyUtils.copyProperties(creditCard, master);
//			creditCard.setSiteId(siteId);
//			creditCard.setCreditCardId(null);
//			creditCard.setRecUpdateBy(userId);
//			creditCard
//					.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//			creditCard.setRecCreateBy(userId);
//			creditCard
//					.setRecCreateDatetime(new Date(System.currentTimeMillis()));
//			session.save(creditCard);
//		}
//	}
//}
