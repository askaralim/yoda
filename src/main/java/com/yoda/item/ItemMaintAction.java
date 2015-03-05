package com.yoda.item;//package com.yoda.admin.item;
//
//import org.apache.commons.beanutils.BeanUtils;
//import org.apache.struts.action.ActionForward;
//import org.apache.struts.action.ActionMapping;
//import org.apache.struts.action.ActionForm;
//import org.apache.struts.action.ActionMessage;
//import org.apache.struts.action.ActionMessages;
//import org.apache.struts.upload.FormFile;
//import org.apache.struts.util.LabelValueBean;
//import org.apache.struts.util.MessageResources;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.exception.ConstraintViolationException;
//import org.json.JSONObject;
//
//import com.yoda.admin.AdminBean;
//import com.yoda.admin.AdminLookupDispatchAction;
//import com.yoda.dao.ItemDAO;
//import com.yoda.dao.ItemImageDAO;
//import com.yoda.dao.MenuDAO;
//import com.yoda.dao.SectionDAO;
//import com.yoda.hibernate.HibernateConnection;
//import com.yoda.hibernate.HomePage;
//import com.yoda.hibernate.Item;
//import com.yoda.hibernate.ItemImage;
//import com.yoda.hibernate.Menu;
//import com.yoda.hibernate.Section;
//import com.yoda.hibernate.ShippingType;
//import com.yoda.search.Indexer;
//import com.yoda.util.Constants;
//import com.yoda.util.Format;
//import com.yoda.util.ImageScaler;
//import com.yoda.util.Utility;
//
//import fr.improve.struts.taglib.layout.util.FormUtils;
//
//import java.io.OutputStream;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.HashMap;
//import java.util.Set;
//import java.util.Vector;
//
//public class ItemMaintAction extends AdminLookupDispatchAction {
//
//	static String TABINDEX_STATISTICS = "0";
//	static String TABINDEX_PRICING = "1";
//	static String TABINDEX_ADJUSTMENT = "2";
//	static String TABINDEX_IMAGES = "3";
//	static String TABINDEX_MENU = "4";
//	static String TABINDEX_SECTION = "5";
//	
//	public ActionForward create(
//			ActionMapping actionMapping, ActionForm actionForm,
//			HttpServletRequest httpServletRequest,
//			HttpServletResponse httpServletResponse)
//		throws Throwable {
//
//		ItemEditCommand form = (ItemEditCommand) actionForm;
//		if (form == null) {
//			form = new ItemEditCommand();
//		}
//		Item item = new Item();
//		BeanUtils.copyProperties(form, item);
//		AdminBean adminBean = getAdminBean(httpServletRequest);
//		String shippingTypeId = Utility.getSiteParamValueString(
//				adminBean.getSite(),
//				Constants.SITEPARAM_SHIPPING_SHIPPINGTYPE_DEFAULT);
//		form.setShippingTypeId(shippingTypeId);
//
//		createAdditionalInfo(adminBean, form, item);
//
//		form.setItemHitCounter("0");
//		form.setItemRating(Format.getFloat(0));
//		form.setItemRatingCount("0");
//		form.setItemQty("0");
//		form.setItemBookedQty("0");
//		form.setPublished(true);
//		form.setItemPublishOn(Format.getDate(new Date(System
//				.currentTimeMillis())));
//		form.setItemExpireOn(Format.getDate(Format.HIGHDATE));
//		form.setItemId("-1");
//		form.setShippingTypeId("");
//		form.setMode("C");
//
//		FormUtils.setFormDisplayMode(
//			httpServletRequest, form, FormUtils.CREATE_MODE);
//		ActionForward actionForward = actionMapping.findForward("success");
//		return actionForward;
//	}
//
//	public ActionForward edit(
//			ActionMapping actionMapping, ActionForm actionForm,
//			HttpServletRequest httpServletRequest,
//			HttpServletResponse httpServletResponse)
//		throws Throwable {
//
//		ItemEditCommand form = (ItemEditCommand) actionForm;
//		if (form == null) {
//			form = new ItemEditCommand();
//		}
//		AdminBean adminBean = getAdminBean(httpServletRequest);
//		String id = httpServletRequest.getParameter("itemId");
//		Long itemId = null;
//		if (id != null) {
//			itemId = Format.getLong(id);
//		} else {
//			itemId = Format.getLong(form.getItemId());
//		}
//
//		Item item = new Item();
//		item = ItemDAO.load(adminBean.getSiteId(), itemId);
//		copyProperties(form, item);
//		form.setMode("U");
//		FormUtils.setFormDisplayMode(httpServletRequest, form,
//				FormUtils.EDIT_MODE);
//
//		createAdditionalInfo(adminBean, form, item);
//
//		ActionForward actionForward = actionMapping.findForward("success");
//		return actionForward;
//	}
//
//	public ActionForward cancel(ActionMapping actionMapping,
//			ActionForm actionForm, HttpServletRequest httpServletRequest,
//			HttpServletResponse httpServletResponse) {
//		ActionForward actionForward = actionMapping.findForward("cancel");
//		return actionForward;
//	}
//
//	public ActionForward save(ActionMapping mapping, ActionForm actionForm,
//			HttpServletRequest httpServletRequest,
//			HttpServletResponse httpServletResponse) throws Throwable {
//
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		boolean insertMode = false;
//		ItemEditCommand form = (ItemEditCommand) actionForm;
//		if (form.getMode().equals("C")) {
//			insertMode = true;
//		}
//
//		AdminBean adminBean = getAdminBean(httpServletRequest);
//		String siteId = adminBean.getSite().getSiteId();
//
//		Item item = new Item();
//		if (!insertMode) {
//			item = ItemDAO.load(siteId, Format.getLong(form.getItemId()));
//		}
//
//		ActionMessages errors = validate(form, siteId, insertMode);
//		if (errors.size() != 0) {
//			saveMessages(httpServletRequest, errors);
//			copyNonFormProperties(form, item);
//			createAdditionalInfo(adminBean, form, item);
//			return mapping.findForward("error");
//		}
//
//		if (!insertMode) {
//			item.setItemId(Format.getLong(form.getItemId()));
//		}
//		item.setSiteId(siteId);
//		item.setItemNaturalKey(Utility.encode(form.getItemNum()));
//		item.setItemNum(form.getItemNum());
//		item.setItemUpcCd(form.getItemUpcCd());
//		item.setSeqNum(new Integer(0));
//		item.setItemShortDesc(form.getItemShortDesc());
//		item.setItemShortDesc1(form.getItemShortDesc1());
//		item.setItemDesc(form.getItemDesc());
//		item.setPageTitle(form.getPageTitle());
//		item.setItemCost(Format.getFloatObj(form.getItemCost()));
//		item.setItemPrice(Format.getFloatObj(form.getItemPrice()));
//		item.setItemMultQty(Format.getIntObj(form.getItemMultQty()));
//		item.setItemMultPrice(Format.getFloatObj(form.getItemMultPrice()));
//		item.setItemSpecPrice(Format.getFloatObj(form.getItemSpecPrice()));
//		item.setItemSpecMultQty(Format.getIntObj(form.getItemSpecMultQty()));
//		item.setItemSpecMultPrice(Format.getFloatObj(form
//				.getItemSpecMultPrice()));
//		if (!Format.isNullOrEmpty(form.getItemSpecPrice())) {
//			if (Format.isNullOrEmpty(form.getItemSpecPublishOn())) {
//				item.setItemSpecPublishOn(Format.LOWDATE);
//			} else {
//				item.setItemSpecPublishOn(Format.getDate(form
//						.getItemSpecPublishOn()));
//			}
//			if (Format.isNullOrEmpty(form.getItemSpecExpireOn())) {
//				item.setItemSpecExpireOn(Format.HIGHDATE);
//			} else {
//				item.setItemSpecExpireOn(Format.getDate(form
//						.getItemSpecExpireOn()));
//			}
//		}
//		item.setItemPublishOn(Format.getDate(form.getItemPublishOn()));
//		item.setItemExpireOn(Format.getDate(form.getItemExpireOn()));
//		item.setPublished(form.isPublished() ? 'Y' : 'N');
//		item.setRecUpdateBy(adminBean.getUser().getUserId());
//		item.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//		if (insertMode) {
//			item.setItemHitCounter(new Integer(0));
//			item.setItemRating(new Float(0));
//			item.setItemRatingCount(new Integer(0));
//			item.setItemQty(new Integer(0));
//			item.setItemBookedQty(new Integer(0));
//			item.setRecCreateBy(adminBean.getUser().getUserId());
//			item.setRecCreateDatetime(new Date(System.currentTimeMillis()));
//		}
//		if (!Format.isNullOrEmpty(form.getShippingTypeId())) {
//			ShippingType shippingType = (ShippingType) session.get(
//					ShippingType.class,
//					Format.getLong(form.getShippingTypeId()));
//			if (shippingType != null) {
//				item.setShippingType(shippingType);
//			}
//		} else {
//			item.setShippingType(null);
//		}
//
//		if (insertMode) {
//			session.save(item);
//			form.setItemId(Format.getLong(item.getItemId()));
//		} else {
//			session.updateSite(item);
//		}
//
//		HomePage homePage = getHomePages(adminBean, item.getItemId());
//		if (form.isHomePage()) {
//			if (homePage == null) {
//				HomePage hp = new HomePage();
//				hp.setSiteId(adminBean.getSite().getSiteId());
//				hp.setFeatureData('N');
//				hp.setItem(item);
//				hp.setRecUpdateBy(adminBean.getUser().getUserId());
//				hp.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//				hp.setRecCreateBy(adminBean.getUser().getUserId());
//				hp.setRecCreateDatetime(new Date(System.currentTimeMillis()));
//				session.save(hp);
//			}
//		} else {
//			if (homePage != null) {
//				session.delete(homePage);
//			}
//		}
//
//		Indexer.getInstance(siteId).removeItem(item);
//		Indexer.getInstance(siteId).indexItem(item);
//
//		copyProperties(form, item);
//		createAdditionalInfo(adminBean, form, item);
//
//		form.setMode("U");
//		FormUtils.setFormDisplayMode(httpServletRequest, form,
//				FormUtils.EDIT_MODE);
//		return mapping.findForward("success");
//	}
//
//	public ActionForward remove(ActionMapping mapping, ActionForm actionForm,
//			HttpServletRequest httpServletRequest,
//			HttpServletResponse httpServletResponse) throws Throwable {
//
//		AdminBean adminBean = getAdminBean(httpServletRequest);
//		String siteId = adminBean.getSite().getSiteId();
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		ItemEditCommand form = (ItemEditCommand) actionForm;
//
//		Item item = ItemDAO.load(siteId, Format.getLong(form.getItemId()));
//
//		try {
//			ItemImage itemImage = item.getImage();
//			if (itemImage != null) {
//				session.delete(itemImage);
//			}
//			Iterator iterator = item.getImages().iterator();
//			while (iterator.hasNext()) {
//				itemImage = (ItemImage) iterator.next();
//				session.delete(itemImage);
//			}
//			iterator = (Iterator) item.getMenus().iterator();
//			while (iterator.hasNext()) {
//				Menu menu = (Menu) iterator.next();
//				menu.setItem(null);
//			}
//			session.delete(item);
//			session.getTransaction().commit();
//		} catch (ConstraintViolationException e) {
//			ActionMessages errors = new ActionMessages();
//			errors.add("error", new ActionMessage(
//					"error.remove.item.constraint"));
//			saveMessages(httpServletRequest, errors);
//			copyNonFormProperties(form, item);
//			createAdditionalInfo(adminBean, form, item);
//			session.getTransaction().rollback();
//			return mapping.findForward("error");
//		}
//
//		Indexer.getInstance(siteId).removeItem(item);
//		return mapping.findForward("removeConfirm");
//	}
//
//	public ActionForward resetCounter(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Throwable {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		ItemEditCommand form = (ItemEditCommand) actionForm;
//		AdminBean adminBean = getAdminBean(request);
//
//		Item item = new Item();
//		item = ItemDAO.load(adminBean.getSiteId(),
//				Format.getLong(form.getItemId()));
//		item.setItemHitCounter(new Integer(0));
//		item.setRecUpdateBy(adminBean.getUser().getUserId());
//		item.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//		session.updateSite(item);
//
//		JSONObject jsonResult = new JSONObject();
//		jsonResult.put("status", Constants.WEBSERVICE_STATUS_SUCCESS);
//		jsonResult.put("recUpdateBy", item.getRecUpdateBy());
//		jsonResult.put("recUpdateDatetime",
//				Format.getFullDatetime(item.getRecUpdateDatetime()));
//		String jsonString = jsonResult.toString();
//		response.setContentType("text/html");
//		response.setContentLength(jsonString.length());
//		OutputStream outputStream = response.getOutputStream();
//		outputStream.write(jsonString.getBytes());
//		outputStream.flush();
//		session.getTransaction().commit();
//		return null;
//	}
//
//	public ActionForward adjustQty(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Throwable {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		ItemEditCommand form = (ItemEditCommand) actionForm;
//		AdminBean adminBean = getAdminBean(request);
//		JSONObject jsonResult = new JSONObject();
//		MessageResources resources = this.getResources(request);
//
//		Item item = new Item();
//		item = ItemDAO.load(adminBean.getSiteId(),
//				Format.getLong(form.getItemId()));
//		if (!Format.isInt(form.getItemAdjQty())) {
//			jsonResult.put("status", Constants.WEBSERVICE_STATUS_FAILED);
//			jsonResult
//					.put("message", resources.getMessage("error.int.invalid"));
//			String jsonString = jsonResult.toString();
//			response.setContentType("text/html");
//			response.setContentLength(jsonString.length());
//			OutputStream outputStream = response.getOutputStream();
//			outputStream.write(jsonString.getBytes());
//			outputStream.flush();
//			session.getTransaction().commit();
//			return null;
//		}
//
//		int itemQty = item.getItemQty().intValue()
//				+ Format.getInt(form.getItemAdjQty());
//		item.setItemQty(new Integer(itemQty));
//		;
//		form.setItemAdjQty("");
//		item.setRecUpdateBy(adminBean.getUser().getUserId());
//		item.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//		session.updateSite(item);
//
//		jsonResult.put("status", Constants.WEBSERVICE_STATUS_SUCCESS);
//		jsonResult.put("itemQty", Format.getInt(item.getItemQty()));
//		jsonResult.put("recUpdateBy", item.getRecUpdateBy());
//		jsonResult.put("recUpdateDatetime",
//				Format.getFullDatetime(item.getRecUpdateDatetime()));
//		String jsonString = jsonResult.toString();
//		response.setContentType("text/html");
//		response.setContentLength(jsonString.length());
//		OutputStream outputStream = response.getOutputStream();
//		outputStream.write(jsonString.getBytes());
//		outputStream.flush();
//		session.getTransaction().commit();
//		return null;
//	}
//
//	public ActionForward adjustBookedQty(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Throwable {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		ItemEditCommand form = (ItemEditCommand) actionForm;
//		AdminBean adminBean = getAdminBean(request);
//		JSONObject jsonResult = new JSONObject();
//		MessageResources resources = this.getResources(request);
//
//		Item item = new Item();
//		item = ItemDAO.load(adminBean.getSiteId(),
//				Format.getLong(form.getItemId()));
//		if (!Format.isInt(form.getItemAdjBookedQty())) {
//			jsonResult.put("status", Constants.WEBSERVICE_STATUS_FAILED);
//			jsonResult
//					.put("message", resources.getMessage("error.int.invalid"));
//			String jsonString = jsonResult.toString();
//			response.setContentType("text/html");
//			response.setContentLength(jsonString.length());
//			OutputStream outputStream = response.getOutputStream();
//			outputStream.write(jsonString.getBytes());
//			outputStream.flush();
//			session.getTransaction().commit();
//			return null;
//		}
//
//		int itemBookedQty = item.getItemBookedQty().intValue()
//				+ Format.getInt(form.getItemAdjBookedQty());
//		item.setItemBookedQty(new Integer(itemBookedQty));
//		item.setRecUpdateBy(adminBean.getUser().getUserId());
//		item.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//		form.setItemAdjBookedQty("");
//		session.updateSite(item);
//
//		jsonResult.put("status", Constants.WEBSERVICE_STATUS_SUCCESS);
//		jsonResult.put("itemBookedQty", Format.getInt(item.getItemBookedQty()));
//		jsonResult.put("recUpdateBy", item.getRecUpdateBy());
//		jsonResult.put("recUpdateDatetime",
//				Format.getFullDatetime(item.getRecUpdateDatetime()));
//		String jsonString = jsonResult.toString();
//		response.setContentType("text/html");
//		response.setContentLength(jsonString.length());
//		OutputStream outputStream = response.getOutputStream();
//		outputStream.write(jsonString.getBytes());
//		outputStream.flush();
//		session.getTransaction().commit();
//		return null;
//	}
//
//	public ActionForward removeSection(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Throwable {
//
//		ItemEditCommand form = (ItemEditCommand) actionForm;
//		AdminBean adminBean = getAdminBean(request);
//
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		Item item = new Item();
//		item = ItemDAO.load(adminBean.getSiteId(),
//				Format.getLong(form.getItemId()));
//		item.setSection(null);
//		item.setRecUpdateBy(adminBean.getUser().getUserId());
//		item.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//
//		JSONObject jsonResult = new JSONObject();
//		jsonResult.put("status", Constants.WEBSERVICE_STATUS_SUCCESS);
//		jsonResult.put("recUpdateBy", item.getRecUpdateBy());
//		jsonResult.put("recUpdateDatetime",
//				Format.getFullDatetime(item.getRecUpdateDatetime()));
//		String jsonString = jsonResult.toString();
//		response.setContentType("text/html");
//		response.setContentLength(jsonString.length());
//		OutputStream outputStream = response.getOutputStream();
//		outputStream.write(jsonString.getBytes());
//		outputStream.flush();
//		session.getTransaction().commit();
//		return null;
//	}
//
//	public ActionForward addSection(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Throwable {
//
//		AdminBean adminBean = getAdminBean(request);
//		String itemId = request.getParameter("itemId");
//		String sectionId = request.getParameter("sectionId");
//
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		Item item = new Item();
//		item = ItemDAO.load(adminBean.getSiteId(), Format.getLong(itemId));
//		Section section = null;
//		if (!Format.isNullOrEmpty(sectionId)) {
//			section = new Section();
//			section = SectionDAO.load(adminBean.getSiteId(),
//					Format.getLong(sectionId));
//			item.setSection(section);
//			item.setRecUpdateBy(adminBean.getUser().getUserId());
//			item.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//		}
//
//		JSONObject jsonResult = new JSONObject();
//		jsonResult.put("status", Constants.WEBSERVICE_STATUS_SUCCESS);
//		jsonResult.put(
//				"selectedSection",
//				Utility.formatSectionName(adminBean.getSiteId(),
//						section.getSectionId()));
//		jsonResult.put("recUpdateBy", item.getRecUpdateBy());
//		jsonResult.put("recUpdateDatetime",
//				Format.getFullDatetime(item.getRecUpdateDatetime()));
//		String jsonString = jsonResult.toString();
//		response.setContentType("text/html");
//		response.setContentLength(jsonString.length());
//		OutputStream outputStream = response.getOutputStream();
//		outputStream.write(jsonString.getBytes());
//		outputStream.flush();
//		session.getTransaction().commit();
//		return null;
//	}
//
//	public ActionForward removeMenus(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Throwable {
//
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		ItemEditCommand form = (ItemEditCommand) actionForm;
//		AdminBean adminBean = getAdminBean(request);
//
//		Item item = new Item();
//		item = ItemDAO.load(adminBean.getSiteId(),
//				Format.getLong(form.getItemId()));
//
//		String menuIds[] = form.getRemoveMenus();
//		if (menuIds != null) {
//			for (int i = 0; i < menuIds.length; i++) {
//				Menu menu = new Menu();
//				menu = MenuDAO.load(adminBean.getSiteId(),
//						Format.getLong(menuIds[i]));
//				menu.setItem(null);
//				menu.setMenuUrl("");
//				menu.setMenuType("");
//			}
//		}
//		item.setRecUpdateBy(adminBean.getUser().getUserId());
//		item.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//		session.flush();
//
//		JSONObject jsonResult = createJsonSelectedMenus(adminBean.getSiteId(),
//				item);
//		jsonResult.put("recUpdateBy", item.getRecUpdateBy());
//		jsonResult.put("recUpdateDatetime",
//				Format.getFullDatetime(item.getRecUpdateDatetime()));
//		String jsonString = jsonResult.toString();
//		response.setContentType("text/html");
//		response.setContentLength(jsonString.length());
//		OutputStream outputStream = response.getOutputStream();
//		outputStream.write(jsonString.getBytes());
//		outputStream.flush();
//		session.getTransaction().commit();
//		return null;
//	}
//
//	public ActionForward addMenus(ActionMapping mapping, ActionForm actionForm,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Throwable {
//
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		ItemEditCommand form = (ItemEditCommand) actionForm;
//		AdminBean adminBean = getAdminBean(request);
//
//		Item item = new Item();
//		item = ItemDAO.load(adminBean.getSiteId(),
//				Format.getLong(form.getItemId()));
//
//		String menuIds[] = form.getAddMenus();
//		if (menuIds != null) {
//			for (int i = 0; i < menuIds.length; i++) {
//				Menu menu = new Menu();
//				menu = MenuDAO.load(adminBean.getSiteId(),
//						Format.getLong(menuIds[i]));
//				menu.setItem(item);
//				menu.setContent(null);
//				menu.setSection(null);
//				menu.setMenuUrl("");
//				menu.setMenuType(Constants.MENU_ITEM);
//				menu.setMenuWindowMode(form.getMenuWindowMode());
//				menu.setMenuWindowTarget(form.getMenuWindowTarget());
//			}
//		}
//		item.setRecUpdateBy(adminBean.getUser().getUserId());
//		item.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//		session.flush();
//
//		JSONObject jsonResult = createJsonSelectedMenus(adminBean.getSiteId(),
//				item);
//		jsonResult.put("recUpdateBy", item.getRecUpdateBy());
//		jsonResult.put("recUpdateDatetime",
//				Format.getFullDatetime(item.getRecUpdateDatetime()));
//		String jsonString = jsonResult.toString();
//		response.setContentType("text/html");
//		response.setContentLength(jsonString.length());
//		OutputStream outputStream = response.getOutputStream();
//		outputStream.write(jsonString.getBytes());
//		outputStream.flush();
//		session.getTransaction().commit();
//		return null;
//	}
//
//	public JSONObject createJsonSelectedMenus(String siteId, Item item)
//			throws Exception {
//		JSONObject jsonResult = new JSONObject();
//		Iterator iterator = item.getMenus().iterator();
//		Vector<JSONObject> menus = new Vector<JSONObject>();
//		while (iterator.hasNext()) {
//			Menu menu = (Menu) iterator.next();
//			JSONObject menuObject = new JSONObject();
//			menuObject.put("menuId", menu.getMenuId());
//			menuObject.put("menuLongDesc",
//					Utility.formatMenuName(siteId, menu.getMenuId()));
//			menuObject.put("menuWindowMode", menu.getMenuWindowMode());
//			menuObject.put("menuWindowTarget", menu.getMenuWindowTarget());
//			menus.add(menuObject);
//		}
//		jsonResult.put("menus", menus);
//		jsonResult.put("status", Constants.WEBSERVICE_STATUS_SUCCESS);
//
//		return jsonResult;
//	}
//
//	public ActionForward uploadImage(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Throwable {
//
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		ItemEditCommand form = (ItemEditCommand) actionForm;
//		AdminBean adminBean = getAdminBean(request);
//
//		MessageResources resources = this.getResources(request);
//		JSONObject jsonResult = new JSONObject();
//
//		Item item = new Item();
//		item = ItemDAO.load(adminBean.getSiteId(),
//				Format.getLong(form.getKey()));
//
//		FormFile file = form.getFile();
//		byte fileData[] = file.getFileData();
//		ActionMessages errors = validateUploadImage(form, file);
//		if (errors.size() != 0) {
//			jsonResult.put("status", Constants.WEBSERVICE_STATUS_FAILED);
//			jsonResult.put("message",
//					resources.getMessage("error.string.required"));
//			String jsonString = jsonResult.toString();
//			response.setContentType("text/html");
//			response.setContentLength(jsonString.length());
//			OutputStream outputStream = response.getOutputStream();
//			outputStream.write(jsonString.getBytes());
//			outputStream.flush();
//			return null;
//		}
//
//		ImageScaler scaler = null;
//		try {
//			scaler = new ImageScaler(fileData, file.getContentType());
//			scaler.resize(600);
//		} catch (Exception e) {
//			jsonResult.put("status", Constants.WEBSERVICE_STATUS_FAILED);
//			jsonResult.put("message",
//					resources.getMessage("error.image.invalid"));
//			String jsonString = jsonResult.toString();
//			response.setContentType("text/html");
//			response.setContentLength(jsonString.length());
//			OutputStream outputStream = response.getOutputStream();
//			outputStream.write(jsonString.getBytes());
//			outputStream.flush();
//			return null;
//		}
//
//		ItemImage itemImage = new ItemImage();
//		itemImage.setSiteId(adminBean.getSite().getSiteId());
//		itemImage.setImageName(file.getFileName());
//		itemImage.setContentType("image/jpeg");
//		itemImage.setImageValue(scaler.getBytes());
//		itemImage.setImageHeight(scaler.getHeight());
//		itemImage.setImageWidth(scaler.getWidth());
//		itemImage.setRecUpdateBy(adminBean.getUser().getUserName());
//		itemImage.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//		itemImage.setRecCreateBy(adminBean.getUser().getUserName());
//		itemImage.setRecCreateDatetime(new Date(System.currentTimeMillis()));
//		session.save(itemImage);
//		if (item.getImage() == null) {
//			item.setImage(itemImage);
//		} else {
//			item.getImages().add(itemImage);
//		}
//		item.setRecUpdateBy(adminBean.getUser().getUserId());
//		item.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//		session.flush();
//		session.refresh(item);
//		jsonResult = createJsonImages(adminBean.getSiteId(), item);
//		jsonResult.put("recUpdateBy", item.getRecUpdateBy());
//		jsonResult.put("recUpdateDatetime",
//				Format.getFullDatetime(item.getRecUpdateDatetime()));
//		String jsonString = jsonResult.toString();
//		response.setContentType("text/html");
//		response.setContentLength(jsonString.length());
//		OutputStream outputStream = response.getOutputStream();
//		outputStream.write(jsonString.getBytes());
//		outputStream.flush();
//		return null;
//	}
//
//	public JSONObject createJsonImages(String siteId, Item item)
//			throws Exception {
//		JSONObject jsonResult = new JSONObject();
//		ItemImage defaultImage = item.getImage();
//		if (defaultImage != null) {
//			JSONObject jsonDeFaultImage = new JSONObject();
//			jsonDeFaultImage.put("imageId", defaultImage.getImageId());
//			jsonDeFaultImage.put("imageName", defaultImage.getImageName());
//			jsonResult.put("defaultImage", jsonDeFaultImage);
//		}
//
//		Iterator iterator = item.getImages().iterator();
//		Vector<JSONObject> vector = new Vector<JSONObject>();
//		while (iterator.hasNext()) {
//			ItemImage image = (ItemImage) iterator.next();
//			JSONObject jsonImage = new JSONObject();
//			jsonImage.put("imageId", image.getImageId());
//			jsonImage.put("imageName", image.getImageName());
//			vector.add(jsonImage);
//		}
//		jsonResult.put("images", vector);
//		jsonResult.put("status", Constants.WEBSERVICE_STATUS_SUCCESS);
//		return jsonResult;
//	}
//
//	public ActionForward removeImages(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Throwable {
//
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		ItemEditCommand form = (ItemEditCommand) actionForm;
//		AdminBean adminBean = getAdminBean(request);
//
//		Item item = new Item();
//		item = ItemDAO.load(adminBean.getSiteId(),
//				Format.getLong(form.getItemId()));
//		ItemImage defaultImage = item.getImage();
//
//		String imageIds[] = form.getRemoveImages();
//		if (imageIds != null) {
//			for (int i = 0; i < imageIds.length; i++) {
//				if (defaultImage != null
//						&& defaultImage.getImageId().equals(
//								Format.getLong(imageIds[i]))) {
//					item.setImage(null);
//					session.save(item);
//					session.delete(defaultImage);
//					defaultImage = null;
//				} else {
//					ItemImage itemImage = ItemImageDAO.load(
//							adminBean.getSiteId(), Format.getLong(imageIds[i]));
//					session.delete(itemImage);
//				}
//			}
//		}
//		item.setRecUpdateBy(adminBean.getUser().getUserId());
//		item.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//		session.flush();
//		session.refresh(item);
//		if (item.getImage() == null) {
//			Set images = item.getImages();
//			if (!images.isEmpty()) {
//				ItemImage itemImage = (ItemImage) images.iterator().next();
//				item.setImage(itemImage);
//				images.remove(itemImage);
//			}
//		}
//		session.flush();
//		session.refresh(item);
//		copyProperties(form, item);
//
//		JSONObject jsonResult = createJsonImages(adminBean.getSiteId(), item);
//		jsonResult.put("recUpdateBy", item.getRecUpdateBy());
//		jsonResult.put("recUpdateDatetime",
//				Format.getFullDatetime(item.getRecUpdateDatetime()));
//		String jsonString = jsonResult.toString();
//		response.setContentType("text/html");
//		response.setContentLength(jsonString.length());
//		OutputStream outputStream = response.getOutputStream();
//		outputStream.write(jsonString.getBytes());
//		outputStream.flush();
//		return null;
//	}
//
//	public ActionForward defaultImage(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Throwable {
//
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		ItemEditCommand form = (ItemEditCommand) actionForm;
//		AdminBean adminBean = getAdminBean(request);
//
//		Item item = new Item();
//		item = ItemDAO.load(adminBean.getSiteId(),
//				Format.getLong(form.getItemId()));
//
//		String defaultImageId = form.getCreateDefaultImageId();
//		ItemImage itemImage = ItemImageDAO.load(adminBean.getSiteId(),
//				Format.getLong(defaultImageId));
//
//		ItemImage currentImage = item.getImage();
//		if (currentImage != null) {
//			item.getImages().add(currentImage);
//		}
//		item.setImage(itemImage);
//		item.getImages().remove(itemImage);
//		item.setRecUpdateBy(adminBean.getUser().getUserId());
//		item.setRecUpdateDatetime(new Date(System.currentTimeMillis()));
//
//		session.save(item);
//		session.flush();
//		session.refresh(item);
//
//		JSONObject jsonResult = createJsonImages(adminBean.getSiteId(), item);
//		jsonResult.put("recUpdateBy", item.getRecUpdateBy());
//		jsonResult.put("recUpdateDatetime",
//				Format.getFullDatetime(item.getRecUpdateDatetime()));
//		String jsonString = jsonResult.toString();
//		response.setContentType("text/html");
//		response.setContentLength(jsonString.length());
//		OutputStream outputStream = response.getOutputStream();
//		outputStream.write(jsonString.getBytes());
//		outputStream.flush();
//		return null;
//	}
//
//	public void createAdditionalInfo(AdminBean adminBean,
//			ItemEditCommand form, Item item) throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		String siteId = adminBean.getSite().getSiteId();
//
//		Iterator iterator = item.getMenus().iterator();
//		Vector<ItemMenuDisplayCommand> selectedMenuVector = new Vector<ItemMenuDisplayCommand>();
//		while (iterator.hasNext()) {
//			Menu menu = (Menu) iterator.next();
//			ItemMenuDisplayCommand menuDisplayForm = new ItemMenuDisplayCommand();
//			menuDisplayForm.setMenuId(Format.getLong(menu.getMenuId()));
//			menuDisplayForm.setMenuLongDesc(Utility.formatMenuName(siteId,
//					menu.getMenuId()));
//			menuDisplayForm.setMenuWindowMode(menu.getMenuWindowMode());
//			menuDisplayForm.setMenuWindowTarget(menu.getMenuWindowTarget());
//			selectedMenuVector.add(menuDisplayForm);
//		}
//		ItemMenuDisplayCommand selectedMenuList[] = new ItemMenuDisplayCommand[selectedMenuVector
//				.size()];
//		selectedMenuVector.copyInto(selectedMenuList);
//		form.setSelectedMenus(selectedMenuList);
//		form.setSelectedMenusCount(selectedMenuList.length);
//		form.setMenuList(Utility.makeMenuTreeList(siteId));
//
//		Section section = item.getSection();
//		if (section != null) {
//			form.setSelectedSection(Utility.formatSectionName(siteId,
//					section.getSectionId()));
//		}
//		form.setSectionTree(Utility.makeSectionTree(siteId));
//
//		iterator = item.getImages().iterator();
//		Vector<LabelValueBean> imageList = new Vector<LabelValueBean>();
//		while (iterator.hasNext()) {
//			ItemImage image = (ItemImage) iterator.next();
//			LabelValueBean bean = new LabelValueBean();
//			bean.setLabel(image.getImageName());
//			bean.setValue(image.getImageId().toString());
//			imageList.add(bean);
//		}
//		LabelValueBean imageIds[] = new LabelValueBean[imageList.size()];
//		imageList.copyInto(imageIds);
//		form.setImageIds(imageIds);
//
//		form.setHomePage(false);
//		if (getHomePages(adminBean, item.getItemId()) != null) {
//			form.setHomePage(true);
//		}
//
//		Query query = session
//				.createQuery("from ShippingType shippingType where siteId = :siteId order by shippingTypeName");
//		query.setString("siteId", siteId);
//		iterator = query.list().iterator();
//		Vector<LabelValueBean> shippingTypeList = new Vector<LabelValueBean>();
//		shippingTypeList.add(new LabelValueBean("", ""));
//		while (iterator.hasNext()) {
//			ShippingType shippingType = (ShippingType) iterator.next();
//			if (shippingType.getPublished() == Constants.PUBLISHED_NO) {
//				continue;
//			}
//			LabelValueBean bean = new LabelValueBean(
//					shippingType.getShippingTypeName(), shippingType
//							.getShippingTypeId().toString());
//			shippingTypeList.add(bean);
//		}
//		LabelValueBean shippingTypes[] = new LabelValueBean[shippingTypeList
//				.size()];
//		shippingTypeList.copyInto(shippingTypes);
//		form.setShippingTypes(shippingTypes);
//	}
//
//	private HomePage getHomePage(AdminBean adminBean, Long itemId)
//			throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		Query query = session
//				.createQuery("from HomePage homePage where siteId = :siteId");
//		query.setString("siteId", adminBean.getSite().getSiteId());
//		Iterator iterator = query.list().iterator();
//		while (iterator.hasNext()) {
//			HomePage homePage = (HomePage) iterator.next();
//			Item item = homePage.getItem();
//			if (item != null) {
//				if (item.getItemId().equals(itemId)) {
//					return homePage;
//				}
//			}
//		}
//		return null;
//	}
//
//	private void copyProperties(ItemEditCommand form, Item item) {
//		form.setItemId(Format.getLong(item.getItemId()));
//		form.setItemNum(item.getItemNum());
//		form.setItemUpcCd(item.getItemUpcCd());
//		form.setItemShortDesc(item.getItemShortDesc());
//		form.setItemShortDesc1(item.getItemShortDesc1());
//		form.setItemDesc(item.getItemDesc());
//		form.setPageTitle(item.getPageTitle());
//		form.setItemCost(Format.getFloatObj(item.getItemCost()));
//		form.setItemPrice(Format.getFloatObj(item.getItemPrice()));
//		form.setItemMultPrice(Format.getFloatObj(item.getItemMultPrice()));
//		form.setItemMultQty(Format.getIntObj(item.getItemMultQty()));
//		form.setItemSpecPrice(Format.getFloatObj(item.getItemSpecPrice()));
//		form.setItemSpecMultPrice(Format.getFloatObj(item
//				.getItemSpecMultPrice()));
//		form.setItemSpecMultQty(Format.getIntObj(item.getItemSpecMultQty()));
//		form.setItemSpecPublishOn(Format.getDate(item.getItemSpecPublishOn()));
//		form.setItemSpecExpireOn(Format.getDate(item.getItemSpecExpireOn()));
//		form.setItemHitCounter(Format.getIntObj(item.getItemHitCounter()));
//		form.setItemRating(Format.getFloatObj(item.getItemRating()));
//		form.setItemRatingCount(Format.getIntObj(item.getItemRatingCount()));
//		form.setItemQty(Format.getIntObj(item.getItemQty()));
//		form.setItemBookedQty(Format.getIntObj(item.getItemBookedQty()));
//		form.setItemPublishOn(Format.getDate(item.getItemPublishOn()));
//		form.setItemExpireOn(Format.getDate(item.getItemExpireOn()));
//		form.setPublished(item.getPublished() == 'Y' ? true : false);
//		form.setRemoveImages(null);
//		form.setRemoveMenus(null);
//		form.setMenuWindowMode("");
//		ShippingType shippingType = item.getShippingType();
//		if (shippingType != null) {
//			form.setShippingTypeId(shippingType.getShippingTypeId().toString());
//		}
//		form.setRecUpdateBy(item.getRecUpdateBy());
//		form.setRecUpdateDatetime(Format.getFullDatetime(item
//				.getRecUpdateDatetime()));
//		form.setRecCreateBy(item.getRecCreateBy());
//		form.setRecCreateDatetime(Format.getFullDatetime(item
//				.getRecCreateDatetime()));
//
//		copyNonFormProperties(form, item);
//	}
//
//	private void copyNonFormProperties(ItemEditCommand form, Item item) {
//		ItemImage itemImage = item.getImage();
//		if (itemImage != null) {
//			LabelValueBean bean = new LabelValueBean();
//			bean.setLabel(itemImage.getImageName());
//			bean.setValue(Format.getLong(itemImage.getImageId()));
//			form.setDefaultImageId(bean);
//		} else {
//			form.setDefaultImageId(null);
//		}
//	}
//
//	public ActionMessages validateUploadImage(ItemEditCommand form,
//			FormFile file) {
//		ActionMessages errors = new ActionMessages();
//		if (file.getFileName().length() == 0) {
//			errors.add("file", new ActionMessage("error.string.required"));
//		}
//		return errors;
//	}
//
//	public ActionMessages validate(ItemEditCommand form, String siteId,
//			boolean insertMode) throws Exception {
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		ActionMessages errors = new ActionMessages();
//		if (Format.isNullOrEmpty(form.getItemPrice())) {
//			errors.add("itemPrice", new ActionMessage("error.string.required"));
//		} else {
//			if (!Format.isFloat(form.getItemPrice())) {
//				errors.add("itemPrice",
//						new ActionMessage("error.float.invalid"));
//			}
//		}
//
//		if (!Format.isNullOrEmpty(form.getItemMultQty())
//				|| !Format.isNullOrEmpty(form.getItemMultPrice())) {
//			if (Format.isNullOrEmpty(form.getItemMultQty())) {
//				errors.add("itemMultQty", new ActionMessage(
//						"error.string.required"));
//			} else {
//				if (!Format.isInt(form.getItemMultQty())) {
//					errors.add("itemMultQty", new ActionMessage(
//							"error.int.invalid"));
//				} else {
//					int unit = Format.getInt(form.getItemMultQty());
//					if (unit <= 1) {
//						errors.add("itemMultQty", new ActionMessage(
//								"error.multUnit.error"));
//					}
//				}
//			}
//			if (Format.isNullOrEmpty(form.getItemMultPrice())) {
//				errors.add("itemMultPrice", new ActionMessage(
//						"error.string.required"));
//			} else {
//				if (!Format.isFloat(form.getItemMultPrice())) {
//					errors.add("itemMultPrice", new ActionMessage(
//							"error.float.invalid"));
//				}
//			}
//		}
//
//		if (!Format.isNullOrEmpty(form.getItemSpecPrice())) {
//			if (!Format.isFloat(form.getItemSpecPrice())) {
//				errors.add("itemSpecPrice", new ActionMessage(
//						"error.float.invalid"));
//			}
//		}
//		if (!Format.isNullOrEmpty(form.getItemSpecMultQty())
//				|| !Format.isNullOrEmpty(form.getItemSpecMultPrice())) {
//			if (Format.isNullOrEmpty(form.getItemSpecMultQty())) {
//				errors.add("itemSpecMultQty", new ActionMessage(
//						"error.string.required"));
//			} else {
//				if (!Format.isInt(form.getItemSpecMultQty())) {
//					errors.add("itemSpecMultQty", new ActionMessage(
//							"error.int.invalid"));
//				} else {
//					int unit = Format.getInt(form.getItemSpecMultQty());
//					if (unit <= 1) {
//						errors.add("itemSpecMultQty", new ActionMessage(
//								"error.multUnit.error"));
//					}
//				}
//			}
//			if (Format.isNullOrEmpty(form.getItemSpecMultPrice())) {
//				errors.add("itemSpecMultPrice", new ActionMessage(
//						"error.string.required"));
//			} else {
//				if (!Format.isFloat(form.getItemSpecMultPrice())) {
//					errors.add("itemSpecMultPrice", new ActionMessage(
//							"error.float.invalid"));
//				}
//			}
//			if (Format.isNullOrEmpty(form.getItemSpecPrice())) {
//				errors.add("itemSpecPrice", new ActionMessage(
//						"error.string.required"));
//			}
//		}
//
//		if (!Format.isNullOrEmpty(form.getItemSpecPublishOn())) {
//			if (!Format.isDate(form.getItemSpecPublishOn())) {
//				errors.add("itemSpecPublishOn", new ActionMessage(
//						"error.date.invalid"));
//			}
//		}
//		if (!Format.isNullOrEmpty(form.getItemSpecExpireOn())) {
//			if (!Format.isDate(form.getItemSpecExpireOn())) {
//				errors.add("itemSpecExpireOn", new ActionMessage(
//						"error.date.invalid"));
//			}
//		}
//
//		if (!Format.isDate(form.getItemPublishOn())) {
//			errors.add("itemPublishOn", new ActionMessage("error.date.invalid"));
//		}
//		if (!Format.isDate(form.getItemExpireOn())) {
//			errors.add("itemExpireOn", new ActionMessage("error.date.invalid"));
//		}
//		if (!Format.isNullOrEmpty(form.getItemSpecExpireOn())) {
//			if (!Format.isFloat(form.getItemCost())) {
//				errors.add("itemCost", new ActionMessage("error.float.invalid"));
//			}
//		}
//
//		String sql = "from  Item " + "where siteId = :siteId "
//				+ "and   itemNum = :itemNum ";
//		if (!insertMode) {
//			sql += "and  itemId != :itemId";
//		}
//		Query query = session.createQuery(sql);
//		query.setString("siteId", siteId);
//		query.setString("itemNum", form.getItemNum());
//		if (!insertMode) {
//			query.setLong("itemId", Format.getLong(form.getItemId()));
//		}
//		Iterator iterator = query.iterate();
//		if (iterator.hasNext()) {
//			errors.add("itemNum", new ActionMessage(
//					"error.item.itemNum.duplicate"));
//		}
//		return errors;
//	}
//
//	protected java.util.Map getKeyMethodMap() {
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("save", "save");
//		map.put("cancel", "cancel");
//		map.put("edit", "edit");
//		map.put("create", "create");
//		map.put("remove", "remove");
//		map.put("resetCounter", "resetCounter");
//		map.put("removeMenus", "removeMenus");
//		map.put("addMenus", "addMenus");
//		map.put("removeSection", "removeSection");
//		map.put("addSection", "addSection");
//		map.put("uploadImage", "uploadImage");
//		map.put("removeImages", "removeImages");
//		map.put("defaultImage", "defaultImage");
//		map.put("adjustQty", "adjustQty");
//		map.put("adjustBookedQty", "adjustBookedQty");
//		return map;
//	}
//}
