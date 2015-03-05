//package com.yoda.portal.content.data;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Vector;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.yoda.contactus.service.ContactUsService;
//import com.yoda.content.model.Content;
//import com.yoda.content.service.ContentService;
//import com.yoda.homepage.model.HomePage;
//import com.yoda.homepage.service.HomePageService;
//import com.yoda.item.service.ItemService;
//import com.yoda.kernal.servlet.ServletContextUtil;
//import com.yoda.menu.model.Menu;
//import com.yoda.menu.service.MenuService;
//import com.yoda.section.model.Section;
//import com.yoda.section.service.SectionService;
//import com.yoda.site.model.Site;
//import com.yoda.site.service.SiteService;
//import com.yoda.user.model.User;
//import com.yoda.user.service.UserService;
//import com.yoda.util.Constants;
//import com.yoda.util.Format;
//import com.yoda.util.StringPool;
//import com.yoda.util.Utility;
//import com.yoda.util.Validator;
//
//public class ContentApi {
//	@Autowired
//	protected SiteService siteService;
//
//	@Autowired
//	private SectionService sectionService;
//
//	@Autowired
//	protected ContentService contentService;
//
//	@Autowired
//	private UserService userService;
//
//	@Autowired
//	private ItemService itemService;
//
//	@Autowired
//	protected MenuService menuService;
//
//	@Autowired
//	private HomePageService homePageService;
//
//	@Autowired
//	private ContactUsService contactUsService;
//
//	Logger logger = Logger.getLogger(ContentApi.class);
//
////	public ContentApi(){
////	}
//
////	public ContentApi(HttpServletRequest request, Site site)
////			throws Exception {
//////		Session session = HibernateConnection.getInstance().getCurrentSession();
////		this.siteId = site.getSiteId();
////		this.site = site;
////
////		/*
////		 * ContentBean contentBean = (ContentBean)
////		 * request.getAttribute("contentBean"); this.siteId =
////		 * contentBean.getSite().getSiteId();
////		 */
////
//////		Site site = SiteCacheUtil.getSiteById(siteId);
////
////		String currencyId = Utility.getParam(site, Constants.SITEPARAM_GENERAL_CURRENCY_DEFAULT);
////
////		imageUrlPrefix = "/" + ApplicationGlobal.getContextPath() + "/services/ImageProvider.do";
////
////		// String currencyId =
////		// contentBean.getSiteParamValueString(Constants.SITEPARAM_GENERAL_CURRENCY_DEFAULT);
////
//////		Currency currency = (Currency) session.get(Currency.class, Format.getLong(currencyId));
////
//////		if (currency == null) {
//////			logger.error("Currency [" + currency + "] does not exist");
//////		}
//////		else {
//////			currencyCode = currency.getCurrencyCode();
//////		}
////
////	}
//
//	public SiteInfo getSite(Site site) throws Exception {
//		SiteInfo siteInfo = new SiteInfo();
//
//		siteInfo.setSiteId(site.getSiteId());
//		siteInfo.setSiteName(site.getSiteName());
//
//		siteInfo.setSiteDomainName(site.getDomainName());
//		siteInfo.setSiteFooter(site.getFooter());
//
//		return siteInfo;
//	}
//
////	public Vector getContactUs(long siteId) throws Exception {
////		List<ContactUs> contactUsList = contactUsService.getContent(siteId, Constants.VALUE_YES);
////
////		Vector<ContactUsInfo> vector = new Vector<ContactUsInfo>();
////
////		for (ContactUs contactUs : contactUsList) {
////			ContactUsInfo info = new ContactUsInfo();
////
////			info.setContactUsName(contactUs.getName());
////			info.setContactUsAddressLine1(contactUs.getAddressLine1());
////			info.setContactUsAddressLine2(contactUs.getAddressLine2());
////			info.setContactUsCityName(contactUs.getCityName());
////			info.setContactUsDesc(contactUs.getDescription());
////			info.setSeqNum(Format.getInt(contactUs.getSeqNum()));
////			info.setContactUsZipCode(contactUs.getZipCode());
////			info.setContactUsEmail(contactUs.getEmail());
////			info.setContactUsPhone(contactUs.getPhone());
////
////			vector.add(info);
////		}
////
////		return vector;
////	}
//
//	public List<MenuInfo> getMenu(Site site, String menuSetName)
//			throws Exception {
//		Menu parent = menuService.getMenu(site.getSiteId(), menuSetName);
//
//		return getMenus(site, menuSetName, parent.getMenuId());
//	}
//
//	private List<MenuInfo> getMenus(
//			Site site, String menuSetName, Long menuParentId)
//		throws Exception {
//		List<MenuInfo> menuInfos = new ArrayList<MenuInfo>();
//
//		List<Menu> menus = menuService.getMenu(site.getSiteId(), menuSetName, menuParentId);
//
//		for (Menu menu : menus) {
//			MenuInfo menuInfo = new MenuInfo();
//
//			menuInfo.setMenuTitle(menu.getMenuTitle());
//			menuInfo.setMenuName(menu.getMenuName());
//			menuInfo.setSeqNo(menu.getSeqNum());
//			menuInfo.setMenuWindowMode(menu.getMenuWindowMode());
//			menuInfo.setMenuWindowTarget(menu.getMenuWindowTarget());
//			menuInfo.setMenus(getMenus(site, menuSetName, menu.getMenuId()));
//
//			String publicURLPrefix = siteService.getPublicURLPrefix(site);
//
//			String frontEndUrlPrefix = Constants.FRONTEND_URL_PREFIX;
//
//			if (Validator.isNotNull(frontEndUrlPrefix)) {
//				frontEndUrlPrefix = StringPool.SLASH + frontEndUrlPrefix;
//			}
//
//			String contextPath = ServletContextUtil.getContextPath();
//
//			String menuAnchor = null;
//			String url = null;
//			String menuName = null;
//
//			if (menu.getMenuType().equals(Constants.MENU_STATIC_URL) && menu.getMenuUrl() != null) {
//				url = menu.getMenuUrl();
//			}
//			else if (menu.getMenuType().equals(Constants.MENU_HOME)) {
//				url = publicURLPrefix + contextPath + frontEndUrlPrefix + StringPool.SLASH + menu.getMenuName();
//			}
////			else if (menu.getMenuType().equals(Constants.MENU_ITEM) && menu.getItem() != null) {
////				Item item = menu.getItem();
////
////				url = publicURLPrefix + contextPath + frontEndUrlPrefix + StringPool.SLASH + menu.getMenuName();
////			}
//			else if (menu.getMenuType().equals(Constants.MENU_CONTENT) && menu.getContent() != null) {
//				Content content = menu.getContent();
//
//				url = publicURLPrefix + contextPath + frontEndUrlPrefix + StringPool.SLASH + menu.getMenuName() + StringPool.SLASH + Constants.FRONTEND_URL_CONTENT + StringPool.SLASH + content.getContentId();
//			}
//			else if (menu.getMenuType().equals(Constants.MENU_SECTION) && menu.getSection() != null) {
//				Section section = menu.getSection();
//
//				url = publicURLPrefix + contextPath + frontEndUrlPrefix + StringPool.SLASH + menu.getMenuName() + StringPool.SLASH + Constants.FRONTEND_URL_SECTION + StringPool.SLASH + section.getSectionId();
//			}
//			else if (menu.getMenuType().equals(Constants.MENU_CONTACTUS)) {
//				url = publicURLPrefix + contextPath + frontEndUrlPrefix + StringPool.SLASH + menu.getMenuName();
//			}
//			else if (menu.getMenuType().equals(Constants.MENU_SIGNIN)) {
//				url = siteService.getSecureURLPrefix(site) + contextPath + StringPool.SLASH + "/account/login/accountLogin";
//			}
//			else if (menu.getMenuType().equals(Constants.MENU_SIGNOUT)) {
//				url = siteService.getSecureURLPrefix(site) + contextPath + "/account/login/accountLogout";
//			}
//
//			if (url == null) {
//				url = publicURLPrefix + contextPath + "/";
//			}
//
//			menuInfo.setMenuUrl(url);
//			menuName = menu.getMenuName();
//
//			menuAnchor = "<a href=\"" + url + "\"" + "onclick=\"javascrpt:window.open('" + url + "', " + "'" + menu.getMenuWindowTarget() + "' ";
//
//			if (menu.getMenuWindowMode().trim().length() != 0) {
//				menuAnchor += ", '" + menu.getMenuWindowMode() + "'";
//			}
//
//			menuAnchor += ");return false;\">";
//			menuAnchor += menuName;
//			menuAnchor += "</a>";
//			menuInfo.setMenuAnchor(menuAnchor);
//
//			menuInfos.add(menuInfo);
//		}
//
//		return menuInfos;
//	}
//
//	/******************************************************************************************************/
//
//	public ContentInfo getContent(
//			long siteId, Content content, boolean checkExpiry,
//			boolean updateStatistics)
//		throws Exception {
//		return processContent(siteId, content, checkExpiry, updateStatistics);
//	}
//
//	public ContentInfo getContent(
//			long siteId, String contentNaturalKey, boolean checkExpiry,
//			boolean updateStatistics)
//		throws Exception {
//		Content content = contentService.getContent(siteId, contentNaturalKey);
//
//		return processContent(siteId, content, checkExpiry, updateStatistics);
//	}
//
//	public ContentInfo getContent(
//			long siteId, long contentId, boolean checkExpiry, boolean updateStatistics)
//		throws Exception {
//		Content content = contentService.getContent(siteId, contentId);
//
//		return processContent(siteId, content, checkExpiry, updateStatistics);
//	}
//
//	public ContentInfo processContent(
//			long siteId, Content content, boolean checkExpiry, boolean updateStatistics)
//		throws Exception {
//		if (content == null) {
//			return null;
//		}
//
//		if (checkExpiry) {
//			if (!Utility.isContentPublished(content)) {
//				return null;
//			}
//		}
//
//		if (content.getSiteId() != siteId) {
//			return null;
//		}
//
//		if (updateStatistics) {
//			content.setHitCounter(content.getHitCounter() + 1);
//		}
//
//		ContentInfo contentInfo = formatContent(content);
//
//		return contentInfo;
//	}
//
//	public ContentInfo formatContent(Content content) throws Exception {
//		ContentInfo contentInfo = new ContentInfo();
//
//		contentInfo.setNaturalKey(content.getNaturalKey());
//		contentInfo.setContentId(content.getContentId());
//		contentInfo.setTitle(content.getTitle());
//		contentInfo.setShortDescription(content.getShortDescription());
//		contentInfo.setDescription(content.getDescription());
//
//		User user = userService.getUser(content.getUpdateBy());
//
//		contentInfo.setUpdateDate(Format.getFullDate(content.getUpdateDate()));
//		contentInfo.setCreateBy(user.getUsername());
//		contentInfo.setCreateDate(Format.getFullDate(content.getCreateDate()));
//
//		if (Format.isNullOrEmpty(content.getPageTitle())) {
//			contentInfo.setPageTitle(content.getTitle());
//		}
//		else {
//			contentInfo.setPageTitle(content.getPageTitle());
//		}
//
//		String contextPath = ServletContextUtil.getContextPath();
//
//		String frontEndUrlPrefix = Constants.FRONTEND_URL_PREFIX;
//
//		if (Validator.isNotNull(frontEndUrlPrefix)) {
//			frontEndUrlPrefix = StringPool.SLASH + frontEndUrlPrefix;
//		}
//
//		String contentUrl = contextPath + frontEndUrlPrefix + StringPool.SLASH + Constants.FRONTEND_URL_CONTENT + StringPool.SLASH + content.getContentId();
//
//		contentInfo.setContentUrl(contentUrl);
//
//		contentInfo.setDefaultImageUrl(null);
//
////		String imageUrlPrefix = contextPath + "/images";
////		String imageUrlPrefix = "/service/imageprovider";
//
//		/*if (content.getImage() != null) {*/
//		if (content.getFeaturedImage() != null) {
//			contentInfo.setDefaultImageUrl(content.getFeaturedImage());
//			/*contentInfo.setDefaultImageUrl(imageUrlPrefix + "?type=C&imageId=" + content.getImage().getImageId());*/
//		}
//
//		return contentInfo;
//	}
//
//	public HomeInfo getHome(long siteId) throws Exception {
//		HomeInfo homeInfo = new HomeInfo();
//
//		List<HomePage> homePages = homePageService.getHomePagesBySiteIdAndFeatureData(siteId);
//
//		for (HomePage homePage : homePages) {
//			if (homePage.getContent() != null) {
//				Content content = homePage.getContent();
//
//				if (!Utility.isContentPublished(content)) {
//					break;
//				}
//
//				ContentInfo contentInfo = formatContent(content);
//
//				contentInfo.setFeature(true);
//
//				homeInfo.setHomePageFeatureData(contentInfo);
//			}
//		}
//
//		homePages = homePageService.getHomePagesBySiteIdAndFeatureDataNotY(siteId);
//
//		List<DataInfo> dataInfos = new ArrayList<DataInfo>();
//
////		Vector<DataInfo> vector = new Vector<DataInfo>();
//
//		for (HomePage homePage : homePages) {
//			if (homePage.getContent() != null) {
//				Content content = homePage.getContent();
//
//				if (!Utility.isContentPublished(content)) {
//					continue;
//				}
//
//				dataInfos.add(formatContent(content));
//			}
//		}
//
////		Object homePageDatas[] = new Object[vector.size()];
//
////		vector.copyInto(homePageDatas);
////		homeInfo.setHomePageDatas(homePageDatas);
//		homeInfo.setHomePageDatas(dataInfos);
//
////		Site site = siteService.getSite(siteId);
//
////		String pageTitle = Utility.getParam(site, Constants.HOME_TITLE);
////
////		homeInfo.setPageTitle(pageTitle);
//
//		return homeInfo;
//	}
//
//	public SectionInfo getSection(
//			long siteId, String sectionNaturalKey, String topSectionNaturalKey, int pageSize,
//			int pageNavCount, int pageNum, String sortBy)
//		throws Exception {
//		String contextPath = ServletContextUtil.getContextPath();
//
//		SectionInfo sectionInfo = new SectionInfo();
//
//		String key = Utility.reEncode(sectionNaturalKey);
//		String topKey = Utility.reEncode(topSectionNaturalKey);
//
//		Section section = sectionService.getSectionBySiteId_NaturalKey(siteId, key);
//
//		if (section == null) {
//			return null;
//		}
//
//		sectionInfo.setSectionNaturalKey(sectionNaturalKey);
//		sectionInfo.setSectionId(section.getSectionId());
//		sectionInfo.setTopSectionId(0);
//		sectionInfo.setTopSectionNaturalKey(topSectionNaturalKey);
//		sectionInfo.setSectionShortTitle(section.getSectionShortTitle());
//		sectionInfo.setSectionTitle(section.getSectionTitle());
//		sectionInfo.setSectionDesc(section.getSectionDesc());
//
////		String url = "/" + ApplicationGlobal.getContextPath()
//		String url = contextPath + StringPool.SLASH
//			+ Constants.FRONTEND_URL_PREFIX + StringPool.SLASH
//			+ Constants.FRONTEND_URL_SECTION + StringPool.SLASH + topSectionNaturalKey + // topSection
//			StringPool.SLASH + section.getSectionNaturalKey(); // section
//
//		sectionInfo.setSectionUrl(url);
//		sectionInfo.setSortBy(sortBy == null ? "" : sortBy);
//
//		List<Section> childSections = sectionService.getSectionBySiteId_SectionParentId_Published(siteId, section.getSectionId(), Constants.PUBLISHED_YES);
//
//		Vector<Object> vector = new Vector<Object>();
//
//		for (Section childSection : childSections) {
//			SectionInfo childSectionInfo = new SectionInfo();
//
//			childSectionInfo.setSectionId(childSection.getSectionId());
//			childSectionInfo.setSectionShortTitle(childSection.getSectionShortTitle());
//			childSectionInfo.setSectionTitle(childSection.getSectionTitle());
//			childSectionInfo.setSectionDesc(childSection.getSectionDesc());
//
////			url = "/" + ApplicationGlobal.getContextPath()
//			url = contextPath + StringPool.SLASH
//				+ Constants.FRONTEND_URL_PREFIX + StringPool.SLASH
//				+ Constants.FRONTEND_URL_SECTION + StringPool.SLASH
//				+ topSectionNaturalKey + // topSection
//				StringPool.SLASH + childSection.getSectionNaturalKey(); // section
//
//			childSectionInfo.setSectionUrl(url);
//
//			vector.add(childSectionInfo);
//		}
//
//		SectionInfo childSectionInfos[] = new SectionInfo[vector.size()];
//
//		vector.copyInto(childSectionInfos);
//
//		sectionInfo.setChildSectionInfos(childSectionInfos);
//		sectionInfo.setChildCount(childSectionInfos.length);
//
//		int itemStart = (pageNum - 1) * pageSize;
//		int itemEnd = itemStart + pageSize - 1;
//		int index = 0;
//
//		String sql = "select type, objectId, description, price " +
//			"from (select 'I' type, item_id objectId, item_short_desc description, item_price price " +
//				"from item where site_id = '" + siteId + "' and section_id = " + section.getSectionId() + " and published = 'Y' and now() between item_publish_on and item_expire_on " +
//					"union select 'C' type, content_id objectId, content_title description, 0 price from content where site_id = '" + siteId + "' and section_id = " + section.getSectionId() +
//					" and published = 'Y'and now() between content_publish_on and content_expire_on) as mytable ";
//
//		List<Section> list = sectionService.search(sql, sortBy);
//
//		int totalCount = list.size();
//
//		Iterator sqlIterator = list.iterator();
//
//		vector = new Vector<Object>();
//
//		while (sqlIterator.hasNext()) {
//			Object object = sqlIterator.next();
//
//			if (index >= itemStart && index <= itemEnd) {
//				Object results[] = (Object[]) object;
//
//				String type = (String) results[0];
//
//				String objectId = (String) results[1];
//
//				if (type.equals("I")) {
////					Item item = itemService.getItem(siteId, Format.getLong(objectId));
////
////					ItemInfo itemInfo = formatItem(item);
////
////					vector.add(itemInfo);
//				}
//				else {
//					Content content = contentService.getContent(siteId, Format.getLong(objectId));
//
//					ContentInfo contentInfo = formatContent(content);
//
//					vector.add(contentInfo);
//				}
//
//				if (index > itemEnd) {
//					break;
//				}
//			}
//
//			index++;
//		}
//
//		int midpoint = pageNavCount / 2;
//		int recordCount = totalCount;
//		int pageTotal = recordCount / pageSize;
//
//		if (recordCount % pageSize > 0) {
//			pageTotal++;
//		}
//
//		sectionInfo.setPageTotal(pageTotal);
//		sectionInfo.setPageNum(pageNum);
//
//		int pageStart = pageNum - midpoint;
//
//		if (pageStart < 1) {
//			pageStart = 1;
//		}
//
//		int pageEnd = pageStart + pageNavCount - 1;
//
//		if (pageEnd > pageTotal) {
//			pageEnd = pageTotal;
//
//			if (pageEnd - pageStart + 1 < pageNavCount) {
//				pageStart = pageEnd - pageNavCount + 1;
//				if (pageStart < 1) {
//					pageStart = 1;
//				}
//			}
//		}
//
//		sectionInfo.setPageStart(pageStart);
//		sectionInfo.setPageEnd(pageEnd);
//
//		Object sectionDatas[] = new Object[vector.size()];
//
//		vector.copyInto(sectionDatas);
//		sectionInfo.setSectionDatas(sectionDatas);
//
//		Vector<SectionInfo> titleSectionVector = new Vector<SectionInfo>();
//
//		Long sectionParentId = section.getSectionParentId();
//
//		while (true) {
//			key = Utility.reEncode(section.getSectionNaturalKey());
//
//			if (key.equals(topKey)) {
//				break;
//			}
//
//			section = sectionService.getSectionBySiteId_SectionId(siteId, sectionParentId);
//
//			if (section.getSectionParentId() == 0) {
//				break;
//			}
//
//			SectionInfo titleSectionInfo = new SectionInfo();
//
//			titleSectionInfo.setSectionNaturalKey(section.getSectionNaturalKey());
//			titleSectionInfo.setSectionId(section.getSectionId());
//			titleSectionInfo.setSectionShortTitle(section.getSectionShortTitle());
//			titleSectionInfo.setSectionTitle(section.getSectionTitle());
//			titleSectionInfo.setSectionDesc(section.getSectionDesc());
//
////			url = "/" + ApplicationGlobal.getContextPath()
//			url = contextPath + StringPool.SLASH
//					+ Constants.FRONTEND_URL_PREFIX + StringPool.SLASH
//					+ Constants.FRONTEND_URL_SECTION + StringPool.SLASH
//					+ topSectionNaturalKey + // topSection
//					StringPool.SLASH + section.getSectionNaturalKey(); // section
//
//			titleSectionInfo.setSectionUrl(url);
//
//			titleSectionVector.add(titleSectionInfo);
//
//			sectionParentId = section.getSectionParentId();
//		}
//
//		// Reverse sequence order
//		SectionInfo titleSectionInfos[] = new SectionInfo[titleSectionVector.size()];
//
//		int pos = 0;
//
//		for (int i = titleSectionVector.size() - 1; i >= 0; i--) {
//			titleSectionInfos[pos++] = (SectionInfo) titleSectionVector.get(i);
//		}
//
//		sectionInfo.setTitleSectionInfos(titleSectionInfos);
//
//		// TODO need to be implemented
//		Object sectionFeatureDatas[] = new Object[0];
//
//		sectionInfo.setSectionFeatureDatas(sectionFeatureDatas);
//
//		return sectionInfo;
//	}
//
//	public SearchInfo getSearch(
//			long siteId, String query, int pageSize, int pageNavCount,
//			int pageNum)
//		throws Exception {
////		Indexer indexer = Indexer.getInstance(siteId);
////		QueryResult queryResult = indexer.search(query, pageNum, pageSize);
////
////		SearchInfo info = new SearchInfo();
////
////		info.setQuery(query);
////		info.setHitsCount(queryResult.getHitCount());
////
////		int recordCount = queryResult.getHitCount();
////		int pageTotal = recordCount / pageSize;
////
////		if (recordCount % pageSize > 0) {
////			pageTotal++;
////		}
////
////		info.setPageTotal(pageTotal);
////		info.setPageNum(pageNum);
////
////		int pageStart = pageNum - pageNavCount / 2;
////
////		if (pageStart < 1) {
////			pageStart = 1;
////		}
////
////		int pageEnd = pageNum + (pageNavCount + 1) / 2;
////
////		if (pageEnd > pageTotal) {
////			pageEnd = pageTotal;
////		}
////
////		info.setPageStart(pageStart);
////		info.setPageEnd(pageEnd);
////
////		Vector<DataInfo> vector = new Vector<DataInfo>();
////
////		QueryHit queryHits[] = queryResult.getQueryHits();
////
////		for (int i = 0; i < queryHits.length; i++) {
////			QueryHit queryHit = queryHits[i];
////			String type = queryHit.getType();
////			String id = queryHit.getId();
////
////			if (type.equals(Constants.DATA_TYPE_CONTENT)) {
////				ContentInfo contentInfo = this.getContent(siteId, Format.getLong(id), true, false);
////
////				vector.add(contentInfo);
////			}
//////			else {
//////				ItemInfo itemInfo = getItem(Format.getLong(id), true, false);
//////
//////				vector.add(itemInfo);
//////			}
////		}
////
////		Object searchDatas[] = new Object[vector.size()];
////
////		vector.copyInto(searchDatas);
////
////		info.setSearchDatas(searchDatas);
////
////		return info;
//		return null;
//	}
//}