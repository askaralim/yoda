package com.yoda.item;//package com.yoda.controlpanel.item;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Vector;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.hibernate.Query;
//import org.hibernate.Session;
//import org.hibernate.exception.ConstraintViolationException;
//import org.springframework.web.portlet.bind.annotation.ActionMapping;
//
//import com.yoda.controlpanel.item.dao.ItemDAO;
//import com.yoda.controlpanel.item.model.Item;
//import com.yoda.controlpanel.item.model.ItemImage;
//import com.yoda.controlpanel.menu.model.Menu;
//import com.yoda.util.Format;
//import com.yoda.util.Utility;
//
//public class ItemListingAction extends AdminListingAction {
//
//	public void extract(
//			AdminListingActionForm actionForm,
//			HttpServletRequest request)
//		throws Throwable {
//
//		ItemListCommand form = (ItemListCommand) actionForm;
//
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		AdminBean adminBean = getAdminBean(request);
//		String siteId = adminBean.getSite().getSiteId();
//
//		Query query = null;
//		String sql = "select item from Item item where siteId = :siteId ";
//
//		if (form.getSrItemNum() != null && form.getSrItemNum().length() > 0) {
//			sql += "and itemNum like :itemNum ";
//		}
//
//		if (form.getSrItemUpcCd().length() > 0) {
//			sql += "and itemUpcCd like :itemUpcCd ";
//		}
//
//		if (form.getSrItemShortDesc().length() > 0) {
//			sql += "and itemShortDesc like :itemShortDesc ";
//		}
//
//		if (form.getSrItemShortDesc1().length() > 0) {
//			sql += "and itemShortDesc1 like :itemShortDesc1 ";
//		}
//
//		if (!form.getSrPublished().equals("*")) {
//			sql += "and published = :published ";
//		}
//
//		sql += "and itemPublishOn between :itemPublishOnStart and :itemPublishOnEnd ";
//		sql += "and itemExpireOn between :itemExpireOnStart and :itemExpireOnEnd ";
//
//		if (!form.getSrUpdateBy().equals("All")) {
//			sql += "and recUpdateBy = :recUpdateBy ";
//		}
//
//		if (!form.getSrCreateBy().equals("All")) {
//			sql += "and recCreateBy = :recCreateBy ";
//		}
//
//		String selectedSections[] = form.getSrSelectedSections();
//
//		if (selectedSections != null) {
//			sql += "and sectionId in (";
//			int index = 0;
//
//			for (int i = 0; i < selectedSections.length; i++) {
//				Long sectionIds[] = Utility.getSectionIdTreeList(siteId,
//						Format.getLong(selectedSections[i]));
//				for (int j = 0; j < sectionIds.length; j++) {
//					if (index > 0) {
//						sql += ",";
//					}
//					sql += ":selectedSection" + index++;
//				}
//			}
//
//			sql += ") ";
//		}
//
//		query = session.createQuery(sql);
//		Date highDate = dateFormat.parse("31-12-2999");
//		Date lowDate = dateFormat.parse("01-01-1900");
//		Date date = null;
//
//		query.setString("siteId", adminBean.getSiteId());
//
//		if (form.getSrItemNum().length() > 0) {
//			query.setString("itemNum", form.getSrItemNum() + "%");
//		}
//
//		if (form.getSrItemUpcCd().length() > 0) {
//			query.setString("itemUpcCd", form.getSrItemUpcCd() + "%");
//		}
//
//		if (form.getSrItemShortDesc().length() > 0) {
//			query.setString("itemShortDesc", "%" + form.getSrItemShortDesc()
//					+ "%");
//		}
//
//		if (form.getSrItemShortDesc1().length() > 0) {
//			query.setString("itemShortDesc1", "%" + form.getSrItemShortDesc1()
//					+ "%");
//		}
//
//		if (!form.getSrPublished().equals("*")) {
//			query.setString("published", form.getSrPublished());
//		}
//
//		if (form.getSrItemPublishOnStart().length() > 0) {
//			date = dateFormat.parse(form.getSrItemPublishOnStart());
//			query.setDate("itemPublishOnStart", date);
//		} else {
//			query.setDate("itemPublishOnStart", lowDate);
//		}
//
//		if (form.getSrItemPublishOnEnd().length() > 0) {
//			date = dateFormat.parse(form.getSrItemPublishOnEnd());
//			query.setDate("itemPublishOnEnd", date);
//		} else {
//			query.setDate("itemPublishOnEnd", highDate);
//		}
//
//		if (form.getSrItemExpireOnStart().length() > 0) {
//			date = dateFormat.parse(form.getSrItemExpireOnStart());
//			query.setDate("itemExpireOnStart", date);
//		} else {
//			query.setDate("itemExpireOnStart", lowDate);
//		}
//
//		if (form.getSrItemExpireOnEnd().length() > 0) {
//			date = dateFormat.parse(form.getSrItemExpireOnEnd());
//			query.setDate("itemExpireOnEnd", date);
//		} else {
//			query.setDate("itemExpireOnEnd", highDate);
//		}
//
//		if (!form.getSrUpdateBy().equals("All")) {
//			query.setString("recUpdateBy", form.getSrUpdateBy());
//		}
//
//		if (!form.getSrCreateBy().equals("All")) {
//			query.setString("recCreateBy", form.getSrCreateBy());
//		}
//
//		if (selectedSections != null) {
//			int index = 0;
//
//			for (int i = 0; i < selectedSections.length; i++) {
//				Long sectionIds[] = Utility.getSectionIdTreeList(siteId,
//						Format.getLong(selectedSections[i]));
//				for (int j = 0; j < sectionIds.length; j++) {
//					query.setLong("selectedSection" + index++,
//							sectionIds[j].longValue());
//				}
//			}
//
//		}
//
//		List list = query.list();
//
//		if (Format.isNullOrEmpty(form.getSrPageNo())) {
//			form.setSrPageNo("1");
//		}
//
//		int pageNo = Integer.parseInt(form.getSrPageNo());
//		calcPage(adminBean, form, list, pageNo);
//		Vector<ItemDisplayCommand> vector = new Vector<ItemDisplayCommand>();
//
//		int startRecord = (form.getPageNo() - 1) * adminBean.getListingPageSize();
//		int endRecord = startRecord + adminBean.getListingPageSize();
//
//		for (int i = startRecord; i < list.size() && i < endRecord; i++) {
//			Item item = (Item) list.get(i);
//			ItemDisplayCommand itemDisplay = new ItemDisplayCommand();
//			itemDisplay.setItemId(Format.getLong(item.getItemId()));
//			itemDisplay.setItemNum(item.getItemNum());
//			itemDisplay.setItemShortDesc(item.getItemShortDesc());
//			itemDisplay.setItemShortDesc1(item.getItemShortDesc1());
//			itemDisplay.setPublished(String.valueOf(item.getPublished()));
//			itemDisplay.setItemPublishOn(Format.getFullDate(item
//					.getItemPublishOn()));
//			itemDisplay.setItemExpireOn(Format.getFullDate(item
//					.getItemExpireOn()));
//			String sectionName = "";
//
//			if (item.getSection() != null) {
//				sectionName = Utility.formatSectionName(siteId, item
//						.getSection().getSectionId());
//			}
//
//			itemDisplay.setSectionName(sectionName);
//			vector.add(itemDisplay);
//		}
//
//		ItemDisplayCommand items[] = new ItemDisplayCommand[vector.size()];
//		vector.copyInto(items);
//		form.setItems(items);
//	}
//
//	public void initSearchInfo(AdminListingActionForm actionForm, String siteId)
//			throws Exception {
//		ItemListCommand form = (ItemListCommand) actionForm;
//		if (form.getSrPublished() == null) {
//			form.setSrPublished("*");
//		}
//		Vector<String> userIdVector = Utility.getUserIdsForSite(siteId);
//		String srSelectUsers[] = new String[userIdVector.size()];
//		userIdVector.copyInto(srSelectUsers);
//		form.setSrSelectUsers(srSelectUsers);
//		form.setSrSectionTree(Utility.makeSectionTree(siteId));
//	}
//
//	public ActionForward remove(ActionMapping actionMapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Throwable {
//
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		ItemListCommand form = (ItemListCommand) actionForm;
//		String itemIds[] = form.getItemIds();
//
//		try {
//			if (form.getItems() != null) {
//				ItemDisplayCommand items[] = form.getItems();
//				for (int i = 0; i < items.length; i++) {
//					if (items[i].getRemove() == null) {
//						continue;
//					}
//					if (!items[i].getRemove().equals("Y")) {
//						continue;
//					}
//					Item item = new Item();
//					item = ItemDAO.load(getAdminBean(request).getSiteId(),
//							Format.getLong(items[i].getItemId()));
//					ItemImage itemImage = item.getImage();
//					if (itemImage != null) {
//						session.delete(itemImage);
//					}
//					Iterator iterator = item.getImages().iterator();
//					while (iterator.hasNext()) {
//						itemImage = (ItemImage) iterator.next();
//						session.delete(itemImage);
//					}
//					iterator = (Iterator) item.getMenus().iterator();
//					while (iterator.hasNext()) {
//						Menu menu = (Menu) iterator.next();
//						menu.setItem(null);
//					}
//					session.delete(item);
//				}
//				session.getTransaction().commit();
//			}
//		} catch (ConstraintViolationException e) {
//			ActionMessages errors = new ActionMessages();
//			errors.add("error", new ActionMessage(
//					"error.remove.items.constraint"));
//			saveMessages(request, errors);
//			session.getTransaction().rollback();
//			ActionForward forward = actionMapping.findForward("removeError");
//			return forward;
//		}
//
//		if (itemIds != null) {
//			for (int i = 0; i < itemIds.length; i++) {
//				Item item = new Item();
//				item = ItemDAO.load(getAdminBean(request).getSiteId(),
//						Format.getLong(itemIds[i]));
//				session.delete(item);
//			}
//		}
//
//		ActionForward forward = actionMapping.findForward("removed");
//		forward = new ActionForward(forward.getPath()
//				+ "?process=list&srPageNo=" + form.getPageNo(),
//				forward.getRedirect());
//		return forward;
//	}
//
//	public void initForm(AdminListingActionForm form) {
//		((ItemListCommand) form).setItems(null);
//	}
//
//	protected java.util.Map getKeyMethodMap() {
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("remove", "remove");
//		map.put("search", "search");
//		map.put("start", "start");
//		map.put("back", "back");
//		map.put("list", "list");
//		return map;
//	}
//}