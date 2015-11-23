package com.yoda.contactus.controller;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.contactus.ContactUsDisplayCommand;
import com.yoda.contactus.ContactUsListCommand;
import com.yoda.contactus.model.ContactUs;
import com.yoda.contactus.service.ContactUsService;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.site.model.Site;
import com.yoda.site.service.SiteService;
import com.yoda.user.model.User;
import com.yoda.util.Constants;
import com.yoda.util.Format;

@Controller
public class ContactUsController {
	@Autowired
	ContactUsService contactUsService;

	@Autowired
	SiteService siteService;

	@RequestMapping(value="/controlpanel/contactus/list", method = RequestMethod.GET)
	public ModelAndView showPanel(
		HttpServletRequest request, HttpServletResponse response) {
		List<ContactUs> contactUsList = contactUsService.getContactUs(PortalUtil.getSiteId(request));

//		ContactUsListCommand command = new ContactUsListCommand();
//
//		initSearchInfo(command);
//
//		extract(command, user);
//
//		command.setEmpty(false);

		return new ModelAndView(
			"controlpanel/contactus/list", "contactUsList", contactUsList);
	}

	@RequestMapping(value="/controlpanel/contactus/list/resequence", method = RequestMethod.GET)
	public String resequence(
			@ModelAttribute ContactUsListCommand command,
			HttpServletRequest request, HttpServletResponse response)
		throws Throwable {
		ContactUsDisplayCommand contactUsCommands[] = command.getContactUsCommands();

		for (int i = 0; i < contactUsCommands.length; i++) {
			int seqNum = Format.getInt(contactUsCommands[i].getSeqNum());
			ContactUs contactUs = contactUsService.getContactUsById(contactUsCommands[i].getContactUsId());

			contactUs.setSeqNum(seqNum);

			contactUsService.update(contactUs);
		}

		return "/controlpanel/contactus/list";
	}

	@RequestMapping(value = "/controlpanel/contactus/list/remove", method = RequestMethod.GET)
	public String removeContactUs(
			@RequestParam("contactUsIds") String contactUsIds,
			HttpServletRequest request) {
		User user = PortalUtil.getAuthenticatedUser();

		String[] arrIds = contactUsIds.split(",");

		for (int i = 0; i < arrIds.length; i++) {
			try {
				contactUsService.deleteContactUs(user.getLastVisitSiteId(), Integer.valueOf(arrIds[i]));
			}
			catch (NumberFormatException e) {
				e.printStackTrace();
			}
			catch (SecurityException e) {
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "redirect:/controlpanel/contactus/list";
	}

	@RequestMapping(value="/controlpanel/contactus/list/search", method = RequestMethod.POST)
	public String search(
			@PathVariable("contactUsId") long contactUsId,
			@ModelAttribute ContactUsListCommand command,
			BindingResult result, SessionStatus status,
			HttpServletRequest request) {

		command.setSrPageNo(1);

		User user = PortalUtil.getAuthenticatedUser();

		extract(command, user);

		command.setEmpty(false);

		return "controlpanel/contactus/list";
	}

	@RequestMapping(value="/controlpanel/contactus/list/{srPageNo}", method = RequestMethod.POST)
	public String list(
			@PathVariable("srPageNo") int srPageNo,
			@ModelAttribute ContactUsListCommand command,
			BindingResult result, SessionStatus status,
			HttpServletRequest request) {

		User user = PortalUtil.getAuthenticatedUser();

		extract(command, user);

		return "controlpanel/contactus/list";
	}

	public void extract(ContactUsListCommand command, User user) {
		Site site = siteService.getSite(user.getLastVisitSiteId());

		if (command.getSrPageNo() == 0) {
			command.setSrPageNo(1);
		}

		Boolean active = null;

		if (command.getSrActive().equals(Constants.ACTIVE_YES)) {
			active = true;
		}
		else if (command.getSrActive().equals(Constants.ACTIVE_NO)) {
			active = false;
		}

		List<ContactUs> list = contactUsService.search(user.getLastVisitSiteId() ,command.getSrContactUsName(), active);

		calcPage(user, command, list, command.getSrPageNo());

		Vector<ContactUsDisplayCommand> vector = new Vector<ContactUsDisplayCommand>();

		int startRecord = (command.getPageNo() - 1) * Format.getInt(site.getListingPageSize());

		int endRecord = startRecord + Format.getInt(site.getListingPageSize());

		for (int i = startRecord; i < list.size() && i < endRecord; i++) {
			ContactUs contactUs = list.get(i);

			ContactUsDisplayCommand contactUsDisplay = new ContactUsDisplayCommand();

			contactUsDisplay.setContactUsId(contactUs.getContactUsId());
			contactUsDisplay.setContactUsName(contactUs.getName());
			contactUsDisplay.setContactUsEmail(contactUs.getEmail());
			contactUsDisplay.setContactUsPhone(contactUs.getPhone());
			contactUsDisplay.setActive(String.valueOf(contactUs.isActive()));
			contactUsDisplay.setSeqNum(Format.getInt(contactUs.getSeqNum()));

			vector.add(contactUsDisplay);
		}

		ContactUsDisplayCommand contactUsDisplayCommand[] = new ContactUsDisplayCommand[vector.size()];

		vector.copyInto(contactUsDisplayCommand);

		command.setContactUsCommands(contactUsDisplayCommand);
	}

	protected void calcPage(
			User user, ContactUsListCommand command, List list, int pageNo) {

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

	public void initSearchInfo(ContactUsListCommand command) {
		if (command.getSrActive() == null) {
			command.setSrActive("*");
		}
	}
}