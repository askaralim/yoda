package com.yoda.util;

import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.yoda.content.model.Content;
//import net.fckeditor.FCKeditor;
//import org.json.JSONObject;
//import com.yoda.admin.AdminBean;
//import com.yoda.admin.AdminLookupDispatchAction;
//import com.yoda.ui.dropdown.DropDownMenu;
//import com.yoda.ui.dropdown.DropDownMenuContainer;
//
public class Utility {
//	static SimpleDateFormat dateFormat = new SimpleDateFormat(
//			"yyyyMMddHHmmssSSS");
//	static String lastOrderNum = "-";
//	static String lastCreditNum = "-";
//	static String lastVoidOrderNum = "-";
//	static int HTTP_TIMEOUT = 30000;

//	static public String formatSectionName(long siteId, long sectionId)
//			throws Exception {
//		String sectionString = "";
//		while (true) {
//			Section section = new Section();
//			section = SectionDAO.load(siteId, sectionId);
//			sectionId = section.getSectionParentId();
//			if (sectionId == null) {
//				break;
//			}
//			if (sectionString.length() > 0) {
//				sectionString = " - " + sectionString;
//			}
//			sectionString = section.getSectionShortTitle() + sectionString;
//		}
//		return sectionString;
//	}

//	/*****/
//
//	static public DropDownMenu[] makeMenuTreeList(String siteId)
//			throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		Query query = session
//				.createQuery("from menu in class Menu where menu.siteId = :siteId and menuParentId is null order by seqNum");
//		query.setString("siteId", siteId);
//		Iterator iterator = query.iterate();
//		Vector<DropDownMenu> menuVector = new Vector<DropDownMenu>();
//		menuVector.add(makeMenuTree(siteId, Constants.MENUSET_MAIN));
//		menuVector.add(makeMenuTree(siteId, Constants.MENUSET_SECONDARY));
//		while (iterator.hasNext()) {
//			Menu menu = (Menu) iterator.next();
//			if (menu.getMenuSetName().equals(Constants.MENUSET_MAIN)) {
//				continue;
//			}
//			if (menu.getMenuSetName().equals(Constants.MENUSET_SECONDARY)) {
//				continue;
//			}
//			/*
//			 * DropDownMenu list[] = new DropDownMenu[1]; list[0] =
//			 * makeMenuTree(siteId, menu.getMenuSetName()); DropDownMenu ddm =
//			 * new DropDownMenu(); ddm.setMenuName(menu.getMenuSetName());
//			 * ddm.setMenuItems(list); menuVector.add(ddm);
//			 */
//			menuVector.add(makeMenuTree(siteId, menu.getMenuSetName()));
//		}
//		DropDownMenu ddmList[] = new DropDownMenu[menuVector.size()];
//		menuVector.copyInto(ddmList);
//		return ddmList;
//	}
//
//	static public DropDownMenu makeMenuTree(String siteId, String menuSetName)
//			throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		Query query = session
//				.createQuery("from menu in class Menu where siteId = :siteId and menu.menuSetName = :menuSetName and menu.menuParentId is null order by seqNum");
//		query.setString("siteId", siteId);
//		query.setString("menuSetName", menuSetName);
//		Iterator iterator = query.iterate();
//		Menu menu = null;
//		if (iterator.hasNext()) {
//			menu = (Menu) iterator.next();
//		}
//
//		DropDownMenu menus[] = makeMenu(siteId, menuSetName, menu.getMenuId());
//		DropDownMenu ddm = new DropDownMenu();
//		ddm.setMenuName(menu.getMenuSetName());
//		ddm.setMenuKey(Format.getLong(menu.getMenuId()));
//		ddm.setMenuItems(menus);
//
//		return ddm;
//	}
//
//	static public DropDownMenu[] makeMenu(String siteId, String menuSetName,
//			Long menuParentId) throws Exception {
//		DropDownMenu menus[] = null;
//		Vector<DropDownMenu> menuList = new Vector<DropDownMenu>();
//
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		Query query = session
//				.createQuery("from menu in class Menu where siteId = :siteId and menu.menuSetName = :menuSetName and menu.menuParentId = :menuParentId order by seqNum");
//		query.setString("siteId", siteId);
//		query.setString("menuSetName", menuSetName);
//		query.setLong("menuParentId", menuParentId.longValue());
//		Iterator iterator = query.iterate();
//		while (iterator.hasNext()) {
//			Menu menu = (Menu) iterator.next();
//			DropDownMenu ddm = new DropDownMenu();
//			ddm.setMenuKey(Format.getLong(menu.getMenuId()));
//			ddm.setMenuName(menu.getMenuName());
//			if (menu.getMenuId() != null) {
//				DropDownMenu childMenus[] = makeDdmMenu(siteId, menuSetName,
//						menu.getMenuId());
//				ddm.setMenuItems(childMenus);
//			}
//			menuList.add(ddm);
//		}
//		menus = new DropDownMenu[menuList.size()];
//		menuList.copyInto(menus);
//		return menus;
//	}
//
//	/*****/
//
//	static public JSONObject makeJSONMenuTree(String siteId) throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		JSONObject object = new JSONObject();
//		String sql = "from	 menu in class Menu " + "where  siteId = :siteId "
//				+ "and    menu.menuParentId = null " + "order	 by menuSetName";
//		Query query = session.createQuery(sql);
//		query.setString("siteId", siteId);
//		Iterator iterator = query.iterate();
//		Vector<JSONObject> menuSetVector = new Vector<JSONObject>();
//		while (iterator.hasNext()) {
//			Menu menu = (Menu) iterator.next();
//			JSONObject menuSetObject = makeJSONMenuTreeNode(siteId,
//					menu.getMenuId());
//			menuSetVector.add(menuSetObject);
//		}
//		object.put("menuSets", menuSetVector);
//
//		return object;
//	}
//
//	static public JSONObject makeJSONMenuTreeNode(String siteId, Long menuId)
//			throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		JSONObject jsonObject = new JSONObject();
//		Menu menu = MenuDAO.load(siteId, menuId);
//		if (menu.getMenuParentId() == null) {
//			jsonObject.put("menuSetName", menu.getMenuSetName());
//		} else {
//			jsonObject.put("menuId", menu.getMenuId());
//			jsonObject.put("menuName", menu.getMenuName());
//		}
//
//		String sql = "";
//		sql = "from	 menu in class Menu " + "where siteId = :siteId "
//				+ "and   menu.menuParentId = :menuParentId "
//				+ "order by seqNum";
//		Query query = session.createQuery(sql);
//		query.setString("siteId", siteId);
//		query.setLong("menuParentId", menuId);
//		Iterator iterator = query.iterate();
//		Vector<JSONObject> vector = new Vector<JSONObject>();
//		while (iterator.hasNext()) {
//			Menu child = (Menu) iterator.next();
//			JSONObject object = makeJSONMenuTreeNode(siteId, child.getMenuId());
//			vector.add(object);
//		}
//		jsonObject.put("menus", vector);
//		return jsonObject;
//	}
//
//	static public JSONObject makeJSONSectionTree(String siteId)
//			throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		JSONObject object = new JSONObject();
//		String sql = "from	 section in class Section "
//				+ "where siteId = :siteId "
//				+ "and   section.sectionParentId = null ";
//		Query query = session.createQuery(sql);
//		query.setString("siteId", siteId);
//		Iterator iterator = query.iterate();
//		if (iterator.hasNext()) {
//			Section section = (Section) iterator.next();
//			return makeJSONSectionTreeNode(siteId, section.getSectionId());
//		}
//
//		return object;
//	}
//
//	static public JSONObject makeJSONSectionTreeNode(String siteId,
//			Long sectionId) throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		JSONObject jsonObject = new JSONObject();
//		Section section = SectionDAO.load(siteId, sectionId);
//		jsonObject.put("sectionId", section.getSectionId());
//		jsonObject.put("sectionShortTitle", section.getSectionShortTitle());
//
//		String sql = "";
//		sql = "from	 section in class Section " + "where siteId = :siteId "
//				+ "and   section.sectionParentId = :sectionParentId "
//				+ "order by seqNum";
//		Query query = session.createQuery(sql);
//		query.setString("siteId", siteId);
//		query.setLong("sectionParentId", sectionId);
//		Iterator iterator = query.iterate();
//		Vector<JSONObject> vector = new Vector<JSONObject>();
//		while (iterator.hasNext()) {
//			Section child = (Section) iterator.next();
//			JSONObject object = makeJSONSectionTreeNode(siteId,
//					child.getSectionId());
//			vector.add(object);
//		}
//		jsonObject.put("sections", vector);
//		return jsonObject;
//	}
//

///
//	static public Long[] getSectionIdTreeList(String siteId, Long parentId)
//			throws Exception {
//		Vector<Long> list = new Vector<Long>();
//		getSectionIdTreeListWorker(siteId, list, parentId);
//		Long sectionIdList[] = new Long[list.size()];
//		list.copyInto(sectionIdList);
//		return sectionIdList;
//	}
//
//	static public void getSectionIdTreeListWorker(String siteId,
//			Vector<Long> list, Long parentId) throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		Query query = session
//				.createQuery("from section in class Section where siteId = :siteId and section.sectionParentId = :sectionParentId order by seqNum");
//		query.setString("siteId", siteId);
//		query.setLong("sectionParentId", parentId.longValue());
//		list.add(parentId);
//		Iterator iterator = query.iterate();
//		while (iterator.hasNext()) {
//			Section section = (Section) iterator.next();
//			list.add(section.getSectionId());
//			getSectionIdTreeListWorker(siteId, list, section.getSectionId());
//		}
//		return;
//	}
//
//	/*****/
//



//	static public String formatMenuName(long siteId, long menuId)
//		throws Exception {
//		String menuString = "";
//
//		while (true) {
//
//			Menu menu = new Menu();
//
//			menu = MenuDAO.getMenu(menuId);
//
//			if (menu.getSiteId() != siteId) {
//				throw new SecurityException();
//			}
//
//			if (menuString.length() > 0) {
//				menuString = " - " + menuString;
//			}
//
//			if (menu.getMenuParentId() == 0) {
//				menuString = menu.getMenuSetName() + menuString;
//				break;
//			}
//
//			menuString = menu.getMenuName() + menuString;
//			menuId = menu.getMenuParentId();
//		}
//
//		return menuString;
//	}

//	static public boolean isSpecialOn(Item item) {
//		if (item.getItemSpecPrice() != null) {
//
//			Date current = new Date(System.currentTimeMillis());
//
//			if (current.after(item.getItemSpecPublishOn())
//				&& current.before(item.getItemSpecExpireOn())) {
//				return true;
//			}
//		}
//
//		return false;
//	}

//	static public String getPriceDisplay(Float itemPrice, Integer itemMultQty,
//			Float itemMultPrice) {
//		String display = Format.getFloatObj(itemPrice);
//		if (itemMultQty != null && itemMultQty.intValue() != 0) {
//			display += ", " + Format.getIntObj(itemMultQty) + "/"
//					+ Format.getFloatObj(itemMultPrice);
//		}
//		return display;
//	}
//
//	static public String getItemPriceDisplay(Item item) {
//		Float itemPrice = item.getItemPrice();
//		Integer itemMultQty = new Integer(0);
//		Float itemMultPrice = new Float(0);
//		if (item.getItemMultQty() != null) {
//			itemMultQty = item.getItemMultQty();
//			itemMultPrice = item.getItemMultPrice();
//		}
//		if (isSpecialOn(item)) {
//			itemPrice = item.getItemSpecPrice();
//			itemMultQty = new Integer(0);
//			itemMultPrice = new Float(0);
//			if (item.getItemSpecMultQty() != null) {
//				itemMultQty = item.getItemSpecMultQty();
//				itemMultPrice = item.getItemSpecMultPrice();
//			}
//		}
//		return getPriceDisplay(itemPrice, itemMultQty, itemMultPrice);
//	}
//
//	static public String getItemShortDescDisplay(Item item) {
//		return getShortDescDisplay(item.getItemShortDesc(),
//				item.getItemShortDesc1());
//	}
//
//	static public String getShortDescDisplay(String itemShortDesc,
//			String itemShortDesc1) {
//		String desc = "";
//		desc += itemShortDesc;
//		if (itemShortDesc1 != null && itemShortDesc1.trim().length() > 0) {
//			desc += " - " + itemShortDesc1;
//		}
//		return desc;
//	}
//
//	static public ItemPriceInfo getItemPriceTotal(Float itemPriceObj,
//			Integer itemMultQtyObj, Float itemMultPriceObj,
//			Float itemSpecPriceObj, Integer itemSpecMultQtyObj,
//			Float itemSpecMultPriceObj, ItemTaxInfo itemTaxInfo[], int qty) {
//		ItemPriceInfo itemPriceInfo = new ItemPriceInfo();
//
//		float itemPrice = itemPriceObj.floatValue();
//		itemPriceInfo.setItemPrice(new Float(itemPrice));
//		int itemMultQty = 0;
//		float itemMultPrice = 0;
//		if (itemMultQtyObj != null) {
//			itemMultQty = itemMultQtyObj.intValue();
//			itemMultPrice = itemMultPriceObj.floatValue();
//		}
//		if (itemSpecPriceObj != null) {
//			itemPrice = itemSpecPriceObj.floatValue();
//			itemMultQty = 0;
//			itemMultPrice = 0;
//			if (itemSpecMultQtyObj != null) {
//				itemMultQty = itemSpecMultQtyObj.intValue();
//				itemMultPrice = itemSpecMultPriceObj.floatValue();
//			}
//		}
//
//		float taxTotal = 0;
//		if (itemMultQty == 0) {
//			itemPriceInfo.setItemPriceTotal(new Float(itemPrice * qty));
//			for (int i = 0; i < qty; i++) {
//				taxTotal = calculateTaxes(itemPrice, itemTaxInfo);
//			}
//			itemPriceInfo.setItemTaxTotal(taxTotal);
//			return itemPriceInfo;
//		}
//
//		float price = 0;
//		while (qty >= itemMultQty) {
//			price += itemMultPrice;
//			qty -= itemMultQty;
//			taxTotal += calculateTaxes(itemMultPrice, itemTaxInfo);
//		}
//		price += itemPrice * qty;
//		for (int i = 0; i < qty; i++) {
//			taxTotal += calculateTaxes(itemPrice, itemTaxInfo);
//		}
//
//		itemPriceInfo.setItemPriceTotal(new Float(price));
//		itemPriceInfo.setItemTaxTotal(taxTotal);
//		return itemPriceInfo;
//	}
//
//	static public float calculateTaxes(float itemPrice,
//			ItemTaxInfo itemTaxInfo[]) {
//		if (itemTaxInfo == null) {
//			return 0;
//		}
//		float taxTotal = 0;
//		for (int j = 0; j < itemTaxInfo.length; j++) {
//			float tax = 0;
//			if (itemTaxInfo[j].getTaxRate() != null) {
//				tax = itemTaxInfo[j].getTaxRate().floatValue();
//			}
//			float taxAmount = round(itemPrice * tax, 0) / 100;
//			itemTaxInfo[j].setTaxAmount(taxAmount
//					+ itemTaxInfo[j].getTaxAmount().floatValue());
//			taxTotal += taxAmount;
//		}
//		return taxTotal;
//	}
//
//	static public ItemPriceInfo getItemPriceTotal(Item item,
//			ItemTaxInfo itemTaxInfo[], int qty) {
//		if (isSpecialOn(item)) {
//			return getItemPriceTotal(item.getItemPrice(),
//					item.getItemMultQty(), item.getItemMultPrice(),
//					item.getItemSpecPrice(), item.getItemSpecMultQty(),
//					item.getItemSpecMultPrice(), itemTaxInfo, qty);
//		} else {
//			return getItemPriceTotal(item.getItemPrice(),
//					item.getItemMultQty(), item.getItemMultPrice(), null, null,
//					null, itemTaxInfo, qty);
//		}
//	}
//
//	static public Float getItemPriceDiscount(Item item, int qty) {
//		float itemPrice = item.getItemPrice().floatValue();
//		if (isSpecialOn(item)) {
//			itemPrice = item.getItemSpecPrice().floatValue();
//		}
//		float itemTotal = itemPrice * qty;
//		ItemPriceInfo itemPriceInfo = getItemPriceTotal(item, null, qty);
//		float itemDiscount = itemTotal - itemPriceInfo.getItemPriceTotal();
//		return new Float(itemDiscount);
//	}
//
//	static public Vector<String> getUserIdsForSite(String siteId)
//			throws Exception {
//		Vector<String> vector = new Vector<String>();
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		Query query = session.createQuery("from User order by userId");
//
//		Iterator iterator = query.iterate();
//		while (iterator.hasNext()) {
//			User user = (User) iterator.next();
//			if (!user.getUserType().equals(Constants.USERTYPE_SUPER)
//					&& !user.getUserType().equals(Constants.USERTYPE_ADMIN)) {
//				boolean found = false;
//				Iterator iteratorSite = user.getSites().iterator();
//				while (iteratorSite.hasNext()) {
//					Site site = (Site) iteratorSite.next();
//					if (site.getSiteId().equals(siteId)) {
//						found = true;
//					}
//				}
//				if (!found) {
//					continue;
//				}
//			}
//			vector.add(user.getUserId());
//		}
//		return vector;
//	}

//	static public String getSiteParamValueString(Site site, String name) {
//		Iterator iterator = site.getSiteParams().iterator();
//
//		while (iterator.hasNext()) {
//			SiteParam siteParam = (SiteParam) iterator.next();
//
//			if (siteParam.getSiteParamName().equals(name))
//				return siteParam.getSiteParamValue();
//		}
//
//		return null;
//	}
//
//	static public Float getSiteParamValueFloat(Site site, String name) {
//		Iterator iterator = site.getSiteParams().iterator();
//		while (iterator.hasNext()) {
//			SiteParam siteParam = (SiteParam) iterator.next();
//			if (siteParam.getSiteParamName().equals(name))
//				return Format.getFloatObj(siteParam.getSiteParamValue());
//		}
//		return null;
//	}
//
//	// TODO Park - cannot be done in a clustered environment
//	static public synchronized String generateOrderNum() {
//		String orderNum = "";
//		while (orderNum != lastOrderNum) {
//			orderNum = dateFormat.format(new Date());
//			lastOrderNum = orderNum;
//		}
//		return orderNum;
//	}
//
//	// TODO Park - cannot be done in a clustered environment
//	static public synchronized String generateVoidOrderNum() {
//		String voidOrderNum = "";
//		while (voidOrderNum != lastVoidOrderNum) {
//			voidOrderNum = dateFormat.format(new Date());
//			lastVoidOrderNum = voidOrderNum;
//		}
//		return lastVoidOrderNum;
//	}
//
//	// TODO Park - cannot be done in a clustered environment
//	static public synchronized String generateCreditNum() {
//		String creditNum = "";
//		while (creditNum != lastCreditNum) {
//			creditNum = dateFormat.format(new Date());
//			lastCreditNum = creditNum;
//		}
//		return creditNum;
//	}
//
//	static public String getParam(Site site, String name) {
//		Iterator iterator = site.getSiteParams().iterator();
//
//		while (iterator.hasNext()) {
//			SiteParam siteParam = (SiteParam)iterator.next();
//
//			if (siteParam.getSiteParamName().equals(name)) {
//				return siteParam.getSiteParamValue();
//			}
//
//		}
//
//		return "";
//	}
//
//	static public String getParam(Iterator siteParamDAOs, String name) {
//		while (siteParamDAOs.hasNext()) {
//			SiteParamDAO siteParamDAO = (SiteParamDAO) siteParamDAOs.next();
//			if (siteParamDAO.getSiteParamName().equals(name)) {
//				return siteParamDAO.getSiteParamValue();
//			}
//		}
//		return "";
//	}
//
//	static public String getCountryName(String siteId, String countryCode)
//			throws Exception {
//		String countryName = null;
//
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		Query query = session
//				.createQuery("from Country where siteId = :siteId and countryCode = :countryCode");
//		query.setString("siteId", siteId);
//		query.setString("countryCode", countryCode);
//		List list = query.list();
//		if (list.size() > 0) {
//			Country country = (Country) list.iterator().next();
//			countryName = country.getCountryName();
//		}
//
//		return countryName;
//	}
//
//	static public String getStateName(String siteId, String stateCode)
//			throws Exception {
//		String stateName = null;
//
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		Query query = session
//				.createQuery("from State where siteId = :siteId and stateCode = :stateCode");
//		query.setString("siteId", siteId);
//		query.setString("stateCode", stateCode);
//
//		List list = query.list();
//
//		if (list.size() > 0) {
//			State state = (State) list.iterator().next();
//			stateName = state.getStateName();
//		}
//
//		return stateName;
//	}
//
//	static public float round(Float value, int digit) {
//		int power = 1;
//		float result;
//		for (int i = 0; i < digit; i++) {
//			power = power * 10;
//		}
//		result = Math.round(value * power) / power;
//		return result;
//	}
//
	static public byte[] httpGet(String url) throws Exception {
		Logger logger = Logger.getLogger(Utility.class);

		logger.debug("getting: " + url);
		byte response[] = null;
		URL u = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) u.openConnection();
		// TODO should be setting timeout
		// connection.setConnectTimeout(HTTP_TIMEOUT);
		// connection.setReadTimeout(HTTP_TIMEOUT);
		connection.setRequestProperty("Content-Type", "text/xml");
		connection.setRequestMethod("GET");
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.connect();

		if (connection.getResponseCode() != 200) {
			logger.error("Error calling performing HttpGet: "
					+ connection.getResponseCode());
			throw new Exception("Error calling webservice: "
					+ connection.getResponseCode());
		}

		int contentLength = connection.getContentLength();
		InputStream is = connection.getInputStream();

		response = new byte[contentLength];
		int totalread = 0;
		int count = 0;
		while (totalread < contentLength) {
			if (count > 0) {
				logger.debug("Reading chunk: " + count);
			}
			totalread += is
					.read(response, totalread, contentLength - totalread);
			if (++count > 500) {
				logger.error("Aborting read after 500 tries");
				break;
			}
		}
		logger.debug(response);
		is.close();

		return response;
	}

//	static public String callWebService(String serviceURL, String request)
//			throws Exception {
//		Logger logger = Logger.getLogger(Utility.class);
//
//		byte response[] = null;
//		URL u = new URL(serviceURL);
//		HttpURLConnection connection = (HttpURLConnection) u.openConnection();
//		// TODO should be setting timeout
//		// connection.setConnectTimeout(HTTP_TIMEOUT);
//		// connection.setReadTimeout(HTTP_TIMEOUT);
//		connection.setRequestProperty("Content-Type", "text/xml");
//		connection.setRequestMethod("POST");
//		connection.setDoInput(true);
//		connection.setDoOutput(true);
//		connection.connect();
//		OutputStream out = connection.getOutputStream();
//		logger.debug(request);
//		out.write(request.getBytes());
//		out.close();
//
//		if (connection.getResponseCode() != 200) {
//			logger.error(request);
//			logger.error("Error calling webservice: "
//					+ connection.getResponseCode());
//			throw new Exception("Error calling webservice: "
//					+ connection.getResponseCode());
//		}
//
//		int contentLength = connection.getContentLength();
//		InputStream is = connection.getInputStream();
//
//		response = new byte[contentLength];
//		int totalread = 0;
//		int count = 0;
//		while (totalread < contentLength) {
//			if (count > 0) {
//				logger.debug("Reading chunk: " + count);
//			}
//			totalread += is
//					.read(response, totalread, contentLength - totalread);
//			if (++count > 500) {
//				logger.error("Aborting read after 500 tries");
//				break;
//			}
//		}
//		logger.debug(response);
//		is.close();
//
//		return new String(response);
//	}

//	static public boolean isItemPublished(Item item) {
//		if (item.getPublished() == Constants.PUBLISHED_NO) {
//			return false;
//		}
//
//		Date current = new Date(System.currentTimeMillis());
//
//		if (current.before(item.getItemPublishOn())
//			|| current.after(item.getItemExpireOn())) {
//			return false;
//		}
//
//		return true;
//	}
//
	static public boolean isContentPublished(Content content) {
		if (!content.isPublished()) {
			return false;
		}

		Date current = new Date(System.currentTimeMillis());

		if (current.before(content.getPublishDate())
			|| current.after(content.getExpireDate())) {

			return false;
		}

		return true;
	}
//
//	static public boolean hasInventory(Item item) {
//		if (item.getItemQty() - item.getItemBookedQty() > 0) {
//			return true;
//		}
//		return false;
//	}

//	static public boolean isDirectory(String name) {
//		if (name == null) {
//			return false;
//		}
//		if (name.trim().length() == 0) {
//			return false;
//		}
//		File file = new File(name);
//		if (file.isDirectory()) {
//			return true;
//		}
//		return false;
//	}
//
//	static public boolean isDirectoryEmpty(String name) {
//		if (name.trim().length() == 0) {
//			return true;
//		}
//		File file = new File(name);
//		if (!file.isDirectory()) {
//			return true;
//		}
//		if (file.list().length == 0) {
//			return true;
//		}
//		return false;
//	}
//
	static public String getWorkingDirectory() {
		String name = ApplicationGlobal.getWorkingDirectory();
		if (!name.endsWith("/") && !name.endsWith("\\")) {
			name += "/";
		}
		return name;
	}

//	static public String getIndexPrefix(long SiteId) {
//		String prefix = getWorkingDirectory() + "p/" + "/index/";
//
//		return prefix;
//	}
//
//	static public String getTemplatePrefix(long siteId, String templateName) {
//		String prefix = getWorkingDirectory() + "p/" + siteId + "/template/" + templateName + "/";
//
//		return prefix;
//	}

//	static public String getTemplateUrlPrefix(long siteId, String templateName) {
//		return "/p/" + siteId + "/template/" + templateName + "/";
//	}

	static public String getResourcePrefix(long siteId) {
		String prefix = getWorkingDirectory() + "p/" + siteId + "/resource/";
		return prefix;
	}

	static public String getResourceUrlPrefix(long siteId) {
		return "/p/" + siteId + "/resource/";
	}
//
//	static public boolean isImage(String filename) {
//		int index = filename.lastIndexOf('.');
//		if (index == -1) {
//			return false;
//		}
//		if (filename.length() - 1 == index) {
//			return false;
//		}
//		String extension = filename.substring(index + 1);
//		for (int i = 0; i < Constants.IMAGE_MIME.length; i++) {
//			if (extension.toLowerCase().trim().equals(Constants.IMAGE_MIME[i])) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	// TODO - http://www.sitepoint.com/article/card-validation-class-php
//	static public boolean isValidCreditCard(int creditCardType,
//			String creditCardNum) {
//		return true;
//		/*
//		 * switch (creditCardType) { case Constants.CREDITCARD_VISA: break; case
//		 * Constants.CREDITCARD_MASTERCARD: break; case
//		 * Constants.CREDITCARD_AMEX: break; default: return false; } return
//		 * true;
//		 */
//	}
//
	public static String getStackTrace(Throwable t) {
		StringWriter string = new StringWriter();
		PrintWriter print = new PrintWriter(string, true);
		t.printStackTrace(print);
		print.flush();
		string.flush();
		return string.toString();
	}
//
//	public static void saveSiteParam(String userId, String siteId, String name,
//			String value) throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		Site site = (Site) session.load(Site.class, siteId);
//		Iterator iterator = site.getSiteParams().iterator();
//		while (iterator.hasNext()) {
//			SiteParam siteParam = (SiteParam) iterator.next();
//			if (siteParam.getSiteParamName().equals(name)) {
//				session.delete(siteParam);
//			}
//		}
//
//		SiteParam siteParam = new SiteParam();
//		siteParam.setSiteParamName(name);
//		siteParam.setSiteParamValue(value == null ? "" : value);
//		siteParam.setRecUpdateBy(userId);
//		siteParam.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//		siteParam.setRecCreateBy(userId);
//		siteParam.setRecCreateDatetime(new Date(System.currentTimeMillis()));
//		site.getSiteParams().add(siteParam);
//		session.save(siteParam);
//	}
//
	static public String encode(String input)
		throws UnsupportedEncodingException {
		input = input.replaceAll("/", "_*_");
		return URLEncoder.encode(input, "UTF-8");
	}

	/*
	 * This method is used to reencode already encoded string. Reason being when
	 * the string is already encodeed and being passed back and forth the
	 * browser, some characters may accidentally decoded. Therefore, this is to
	 * decode the string into its original representation and encode again.
	 */
	static public String reEncode(String input) throws Exception {
		String value = URLDecoder.decode(input, "UTF-8");
		return URLEncoder.encode(value, "UTF-8");
	}

//	// This should be removed before production.
//	static String safePrefix = "D:\\work\\ajk";
//
//	static public boolean removeFile(String directory) throws Exception {
//		File file = new File(directory);
//		File files[] = file.listFiles();
//		if (files != null) {
//			for (int i = 0; i < files.length; i++) {
//				if (files[i].isDirectory()) {
//					removeFile(files[i].getAbsolutePath());
//				} else {
//					if (files[i].getCanonicalPath().startsWith(safePrefix)) {
//						if (!files[i].delete()) {
//							return false;
//						}
//					}
//				}
//			}
//		}
//		if (file.getCanonicalPath().startsWith(safePrefix)) {
//			if (!file.delete()) {
//				return false;
//			}
//		}
//		return true;
//	}
//
//	static public String trim(String input, int size) {
//		if (input == null) {
//			return input;
//		}
//		if (input.length() <= size) {
//			return input;
//		}
//		return input.substring(0, size - 1);
//	}
//
//	static public String getFckEditor(
//			HttpServletRequest request, String id, String width, String height,
//			String toolSet, String value) {
////		Admin adminBean = AdminLookupDispatchAction.getAdminBean(request);
//
//		FCKeditor editor;
//		String text = value;
//
//		if (text == null) {
//			text = "";
//		}
//
//		if (text.length() == 0) {
//			text = "&nbsp";
//		}
//
//		editor = new FCKeditor(request, id, width, height, toolSet, text, null);
//
////		String basePath = "/" + adminBean.getContextPath() + "/FCKeditor/";
//		String basePath = request.getContextPath() + "/FCKeditor";
//
//		editor.setBasePath(basePath);
//
//		return editor.createHtml();
//	}

//	static public boolean isExtendedTransaction(String paymentGateway) {
//		if (paymentGateway.equals(Constants.PAYMENTGATEWAY_PAYPAL)) {
//			return false;
//		}
//		return true;
//	}
//
//	static public String getServletLocation(ServletContext context) {
//		return context.getRealPath("/");
//	}

	static public Level getLogLevel(Logger logger) {
		if (logger.getLevel() != null) {
			return logger.getLevel();
		}
		return logger.getParent().getLevel();
	}
}
