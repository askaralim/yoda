package com.yoda.content.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.content.ContentDisplayCommand;
import com.yoda.content.ContentListCommand;
import com.yoda.content.model.Content;
import com.yoda.content.service.ContentService;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.menu.service.MenuService;
import com.yoda.section.service.SectionService;
import com.yoda.site.model.Site;
import com.yoda.site.service.SiteService;
import com.yoda.user.model.User;
import com.yoda.user.service.UserService;
import com.yoda.util.Constants;
import com.yoda.util.Format;

@Controller
public class ContentController {

	@Autowired
	UserService userService;

	@Autowired
	SiteService siteService;

	@Autowired
	SectionService sectionService;

	@Autowired
	ContentService contentService;

//	@Autowired
//	ContentImageService contentImageService;

	@Autowired
	MenuService menuService;

	@RequestMapping(value="/controlpanel/content/list", method = RequestMethod.GET)
	public ModelAndView showPanel() {

		User user = PortalUtil.getAuthenticatedUser();

		ContentListCommand command = new ContentListCommand();

//		command.setSrPageNo("");

		try {
			List<Content> contents = contentService.getContents(user.getLastVisitSiteId());

			extract(command, user, contents);

			initSearchInfo(command, user.getLastVisitSiteId());

			command.setEmpty(false);

//			command.setEmpty(true);

			initSearchInfo(command, user.getLastVisitSiteId());
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ModelAndView(
			"controlpanel/content/list", "contentListCommand", command);
	}

	public void extract(
			ContentListCommand command, User user, List list)
		throws Exception {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

//		Admin admin = getAdminBean(request);
//
//		long siteId = admin.getSiteId();

//		Session session = contentService.getSession();
//
//		Query query = null;
//
//		String sql = "select content from Content content where siteId = :siteId ";
//
//		if (command.getSrContentTitle().length() > 0) {
//			sql += "and contentTitle like :contentTitle ";
//		}
//
//		if (!command.getSrPublished().equals("*")) {
//			sql += "and published = :published ";
//		}
//
//		sql += "and contentPublishOn between :contentPublishOnStart and :contentPublishOnEnd ";
//		sql += "and contentExpireOn between :contentExpireOnStart and :contentExpireOnEnd ";
//
//		if (!command.getSrUpdateBy().equals("All")) {
//			sql += "and recUpdateBy = :recUpdateBy ";
//		}
//
//		if (!command.getSrCreateBy().equals("All")) {
//			sql += "and recCreateBy = :recCreateBy ";
//		}
//
////		String selectedSections[] = command.getSrSelectedSections();
////
////		if (selectedSections != null) {
////
////			sql += "and sectionId in (";
////
////			int index = 0;
////
////			for (int i = 0; i < selectedSections.length; i++) {
////				Long sectionIds[] = sectionService.getSectionIdTreeList(
////					siteId, Format.getLong(selectedSections[i]));
////
////				for (int j = 0; j < sectionIds.length; j++) {
////
////					if (index > 0) {
////						sql += ",";
////					}
////
////					sql += ":selectedSection" + index++;
////				}
////
////				sql += ") ";
////			}
////		}
//
//		query = session.createQuery(sql);
//
//		Date highDate = dateFormat.parse("31-12-2999");
//		Date lowDate = dateFormat.parse("01-01-1900");
//		Date date = null;
//
//		query.setLong("siteId", siteId);
//
//		if (command.getSrContentTitle().length() > 0) {
//			query.setString("contentTitle", "%" + command.getSrContentTitle() + "%");
//		}
//		if (!command.getSrPublished().equals("*")) {
//			query.setString("published", command.getSrPublished());
//		}
//		if (command.getSrContentPublishOnStart().length() > 0) {
//			date = dateFormat.parse(command.getSrContentPublishOnStart());
//			query.setDate("contentPublishOnStart", date);
//		} else {
//			query.setDate("contentPublishOnStart", lowDate);
//		}
//		if (command.getSrContentPublishOnEnd().length() > 0) {
//			date = dateFormat.parse(command.getSrContentPublishOnEnd());
//			query.setDate("contentPublishOnEnd", date);
//		} else {
//			query.setDate("contentPublishOnEnd", highDate);
//		}
//		if (command.getSrContentExpireOnStart().length() > 0) {
//			date = dateFormat.parse(command.getSrContentExpireOnStart());
//			query.setDate("contentExpireOnStart", date);
//		} else {
//			query.setDate("contentExpireOnStart", lowDate);
//		}
//		if (command.getSrContentExpireOnEnd().length() > 0) {
//			date = dateFormat.parse(command.getSrContentExpireOnEnd());
//			query.setDate("contentExpireOnEnd", date);
//		} else {
//			query.setDate("contentExpireOnEnd", highDate);
//		}
//		if (!command.getSrUpdateBy().equals("All")) {
//			query.setString("recUpdateBy", command.getSrUpdateBy());
//		}
//		if (!command.getSrCreateBy().equals("All")) {
//			query.setString("recCreateBy", command.getSrCreateBy());
//		}
//		if (selectedSections != null) {
//			int index = 0;
//
//			for (int i = 0; i < selectedSections.length; i++) {
//				Long sectionIds[] = sectionService.getSectionIdTreeList(
//					siteId, Format.getLong(selectedSections[i]));
//
//				for (int j = 0; j < sectionIds.length; j++) {
//					query.setLong(
//						"selectedSection" + index++, sectionIds[j].longValue());
//				}
//			}
//		}

//		List list = contentService.search(
//			siteId, command.getSrContentTitle(), command.getSrPublished(),
//			command.getSrUpdateBy(), command.getSrCreateBy(),
//			command.getSrContentPublishOnStart(),
//			command.getSrContentPublishOnEnd(),
//			command.getSrContentExpireOnStart(),
//			command.getSrContentExpireOnEnd());

		Site site = siteService.getSite(user.getLastVisitSiteId());

		if (Format.isNullOrEmpty(command.getSrPageNo())) {
			command.setSrPageNo("1");
		}

		int pageNo = Integer.parseInt(command.getSrPageNo());

		calcPage(user, command, list, pageNo);

		Vector<ContentDisplayCommand> vector = new Vector<ContentDisplayCommand>();

		int startRecord = (command.getPageNo() - 1) * Format.getInt(site.getListingPageSize());

		int endRecord = startRecord + Format.getInt(site.getListingPageSize());

		for (int i = startRecord; i < list.size() && i < endRecord; i++) {
			Content content = (Content) list.get(i);

			ContentDisplayCommand contentDisplay = new ContentDisplayCommand();

			contentDisplay.setContentId(content.getContentId());
			contentDisplay.setTitle(content.getTitle());
			contentDisplay.setPublished(content.isPublished());
			contentDisplay.setPublishDate(Format.getDate(content.getPublishDate()));
			contentDisplay.setExpireDate(Format.getDate(content.getExpireDate()));

			String sectionName = "";

			if (content.getSection() != null) {
				sectionName = sectionService.formatSectionName(user.getLastVisitSiteId()	, content.getSection().getSectionId());
			}

			contentDisplay.setSectionName(sectionName);
			vector.add(contentDisplay);
		}

		ContentDisplayCommand contents[] = new ContentDisplayCommand[vector.size()];

		vector.copyInto(contents);

		command.setContents(contents);
	}

	public void initSearchInfo(ContentListCommand command, int siteId)
		throws Exception {
		if (command.getPublished() == null) {
			command.setPublished("*");
		}

//		Vector<String> userIdVector = Utility.getUserIdsForSite(siteId);

		Vector<String> userIdVector = new Vector<String>();

		List<User> users = userService.getUsers("from User order by userId");

		for (User user : users) {
			if (!user.getUserType().equals(Constants.USERTYPE_SUPER)
				&& !user.getUserType().equals(Constants.USERTYPE_ADMIN)) {
				boolean found = false;

				Iterator iterator = user.getSites().iterator();

				while (iterator.hasNext()) {
					Site site = (Site)iterator.next();

					if (site.getSiteId() == siteId) {
						found = true;
					}
				}

				if (!found) {
					continue;
				}
			}

			userIdVector.add(String.valueOf(user.getUserId()));
		}

		String srSelectUsers[] = new String[userIdVector.size()];

		userIdVector.copyInto(srSelectUsers);

		command.setSelectUsers(srSelectUsers);
		command.setSectionTree(sectionService.makeSectionTree(siteId));
	}

	@RequestMapping(value="/controlpanel/content/list/remove")
	public String removeContents(
			@RequestParam("contentIds") String contentIds,
			HttpServletRequest request) {
		User user = PortalUtil.getAuthenticatedUser();

		String[] arrIds = contentIds.split(",");

		Content content = new Content();

		for (int i = 0; i < arrIds.length; i++) {
			content = contentService.getContent(user.getLastVisitSiteId(), Long.valueOf(arrIds[i]));

			contentService.deleteContent(content);
		}

		return "redirect:/controlpanel/content/list";
	}

//	public ActionForward remove(ActionMapping actionMapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse response) throws Throwable {
//
//		Session session = HibernateConnection.getInstance().getCurrentSession();
//		ContentListingActionForm form = (ContentListingActionForm) actionForm;
//
//		try {
//			if (form.getContents() != null) {
//				ContentDisplayForm contents[] = form.getContents();
//				for (int i = 0; i < contents.length; i++) {
//					if (contents[i].getRemove() == null) {
//						continue;
//					}
//					if (!contents[i].getRemove().equals("Y")) {
//						continue;
//					}
//					Content content = new Content();
//					content = ContentDAO.load(
//							getAdminBean(request).getSiteId(),
//							Format.getLong(contents[i].getContentId()));
//					ContentImage contentImage = content.getImage();
//					if (contentImage != null) {
//						session.delete(contentImage);
//					}
//					Iterator iterator = content.getImages().iterator();
//					while (iterator.hasNext()) {
//						contentImage = (ContentImage) iterator.next();
//						session.delete(contentImage);
//					}
//					iterator = (Iterator) content.getMenus().iterator();
//					while (iterator.hasNext()) {
//						Menu menu = (Menu) iterator.next();
//						menu.setContent(null);
//					}
//					session.delete(content);
//				}
//				session.getTransaction().commit();
//			}
//		} catch (ConstraintViolationException e) {
//			ActionMessages errors = new ActionMessages();
//			errors.add("error", new ActionMessage(
//					"error.remove.contents.constraint"));
//			saveMessages(request, errors);
//			session.getTransaction().rollback();
//			ActionForward forward = actionMapping.findForward("removeError");
//			return forward;
//		}
//
//		ActionForward forward = actionMapping.findForward("removed");
//		forward = new ActionForward(forward.getPath()
//				+ "?process=list&srPageNo=" + form.getPageNo(),
//				forward.getRedirect());
//		return forward;
//	}
//
//	protected java.util.Map getKeyMethodMap() {
//		Map<String, String> map = new HashMap<String, String>();
//		map.put("remove", "remove");
//		map.put("list", "list");
//		map.put("start", "start");
//		map.put("back", "back");
//		map.put("search", "search");
//		return map;
//	}
//

	@RequestMapping(value="/controlpanel/content/list/search", method = RequestMethod.POST)
	public String search(
			@ModelAttribute ContentListCommand command,
			BindingResult result, SessionStatus status,
			HttpServletRequest request)
		throws Throwable {
		User user = PortalUtil.getAuthenticatedUser();

		command.setSrPageNo("");

		int siteId = user.getLastVisitSiteId();

		List<Content> list = contentService.search(
			siteId, command.getTitle(), command.getPublished(),
			command.getUpdateBy(), command.getCreateBy(),
			command.getPublishDateStart(),
			command.getPublishDateEnd(),
			command.getExpireDateStart(),
			command.getExpireDateEnd());

		extract(command, user, list);

		initSearchInfo(command, user.getLastVisitSiteId());

		command.setEmpty(false);

		return "controlpanel/content/list";
	}

	protected void calcPage(
			User user, ContentListCommand command, List list, int pageNo) {

		Site site = siteService.getSite(user.getLastVisitSiteId());

		command.setPageNo(pageNo);

		/* Calc Page Count */
		int pageCount = (list.size() - list.size()
			% Format.getInt(site.getListingPageSize()))
			/ Format.getInt(site.getListingPageSize());

		if (list.size() % Format.getInt(site.getListingPageSize()) > 0) {
			pageCount++;
		}

		command.setPageCount(pageCount);

		int half = Constants.DEFAULT_LISTING_PAGE_COUNT / 2;

		/* Calc Start Page */
		int startPage = pageNo - half + 1;

		if (startPage < 1) {
			startPage = 1;
		}

		command.setStartPage(startPage);

		/* Calc End Page */
		/* Trying to make sure the maximum number of navigation is visible */
		int endPage = startPage + Constants.DEFAULT_LISTING_PAGE_COUNT - 1;

		while (endPage > pageCount && startPage > 1) {
			endPage--;
			startPage--;
		}
		/* Still not possible. Trimming navigation. */

		if (endPage > pageCount) {

			if (pageCount == 0) {
				endPage = 1;
			} else {
				endPage = pageCount;
			}

		}

		command.setStartPage(startPage);
		command.setEndPage(endPage);
	}
}