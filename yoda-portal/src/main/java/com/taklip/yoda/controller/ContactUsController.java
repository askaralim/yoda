package com.taklip.yoda.controller;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.taklip.yoda.model.ContactUs;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.ContactUsService;
import com.taklip.yoda.service.SiteService;
import com.taklip.yoda.tool.Constants;
import com.taklip.yoda.util.AuthenticatedUtil;
import com.taklip.yoda.util.SiteUtil;
import com.taklip.yoda.validator.ContactUsEditValidator;
import com.taklip.yoda.vo.ContactUsDisplayCommand;
import com.taklip.yoda.vo.ContactUsEditCommand;
import com.taklip.yoda.vo.ContactUsListCommand;

@Controller
public class ContactUsController {
	@Autowired
	ContactUsService contactUsService;

	@Autowired
	SiteService siteService;

	@RequestMapping(value="/controlpanel/contactus/list", method = RequestMethod.GET)
	public ModelAndView showPanel(
		HttpServletRequest request, HttpServletResponse response) {
		List<ContactUs> contactUsList = contactUsService.getContactUs(SiteUtil.getDefaultSite().getSiteId());

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

	@RequestMapping(value="/controlpanel/contactus/add", method = RequestMethod.GET)
	public ModelAndView setupForm(
			HttpServletRequest request, HttpServletResponse response) {
		User user = AuthenticatedUtil.getAuthenticatedUser();

		long siteId = user.getLastVisitSiteId();

		ContactUsEditCommand command = new ContactUsEditCommand();

		command.setActive(true);

		initSearchInfo(command, siteId);

		return new ModelAndView(
			"controlpanel/contactus/edit", "contactUsEditCommand", command);
	}

	@RequestMapping(value="/controlpanel/contactus/add", method = RequestMethod.POST)
	public ModelAndView add(
			@ModelAttribute ContactUsEditCommand command,
			BindingResult result, SessionStatus status,
			HttpServletRequest request)
		throws Throwable {

		User user = AuthenticatedUtil.getAuthenticatedUser();

		new ContactUsEditValidator().validate(command, result);

		ModelMap model = new ModelMap();

		if(result.hasErrors()) {
			initSearchInfo(command, user.getLastVisitSiteId());

			model.put("errors", "errors");

			return new ModelAndView("controlpanel/contactus/edit", model);
		}

		ContactUs contactUs = contactUsService.addContactUs(
			user.getLastVisitSiteId(), command.isActive(),
			command.getContactUsName(), command.getContactUsEmail(),
			command.getContactUsPhone(), command.getContactUsAddressLine1(),
			command.getContactUsAddressLine2(), command.getContactUsCityName(),
			command.getContactUsZipCode(), command.getSeqNum(),
			command.getContactUsDesc());

		command.setContactUsId(contactUs.getContactUsId());

		initSearchInfo(command, user.getLastVisitSiteId());

		return new ModelAndView("redirect:/controlpanel/contactus/" + contactUs.getContactUsId() + "/edit", model);
	}

	@RequestMapping(value="/controlpanel/contactus/{contactUsId}/edit", method = RequestMethod.GET)
	public ModelAndView showPanel(
		@PathVariable("contactUsId") int contactUsId,
		HttpServletRequest request, HttpServletResponse response) {
		User user = AuthenticatedUtil.getAuthenticatedUser();

		ContactUsEditCommand command = new ContactUsEditCommand();

		ContactUs contactUs = new ContactUs();

		try {
			contactUs = contactUsService.getContactUsById(contactUsId);
		}
		catch (SecurityException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		copyProperties(command, contactUs);

		initSearchInfo(command, user.getLastVisitSiteId());


		return new ModelAndView(
			"controlpanel/contactus/edit", "contactUsEditCommand", command);
	}

	@RequestMapping(value="/controlpanel/contactus/{contactUsId}/edit", method = RequestMethod.POST)
	public ModelAndView update(
			@ModelAttribute ContactUsEditCommand command,
			BindingResult result, SessionStatus status,
			HttpServletRequest request)
		throws Throwable {

		User user = AuthenticatedUtil.getAuthenticatedUser();

		new ContactUsEditValidator().validate(command, result);

		ModelMap model = new ModelMap();

		if(result.hasErrors()) {
			initSearchInfo(command, user.getLastVisitSiteId());

			model.put("errors", "errors");

			return new ModelAndView("controlpanel/contactus/edit", model);
		}

		ContactUs contactUs = contactUsService.updateContactUs(
			command.getContactUsId(),
			user.getLastVisitSiteId(), command.isActive(),
			command.getContactUsName(), command.getContactUsEmail(),
			command.getContactUsPhone(), command.getContactUsAddressLine1(),
			command.getContactUsAddressLine2(), command.getContactUsCityName(),
			command.getContactUsZipCode(), command.getSeqNum(),
			command.getContactUsDesc());

		command.setContactUsId(contactUs.getContactUsId());

		initSearchInfo(command, user.getLastVisitSiteId());

		model.put("success", "success");

		return new ModelAndView("controlpanel/contactus/edit", model);
	}

	private void copyProperties(
			ContactUsEditCommand command, ContactUs contactUs) {
		command.setContactUsId(contactUs.getContactUsId());
		command.setContactUsName(contactUs.getName());
		command.setContactUsEmail(contactUs.getEmail());
		command.setContactUsAddressLine1(contactUs.getAddressLine1());
		command.setContactUsAddressLine2(contactUs.getAddressLine2());
		command.setContactUsCityName(contactUs.getCityName());
		command.setContactUsZipCode(contactUs.getZipCode());
		command.setContactUsPhone(contactUs.getPhone());
		command.setContactUsDesc(contactUs.getDescription());
		command.setActive(contactUs.isActive());
	}

	public void initSearchInfo(ContactUsEditCommand command, long siteId) {
//		List<Country> countries = countryService.getBySiteId(siteId);
//
//		command.setCountries(countries);
//
//		List<State> states = stateService.getBySiteId(siteId);
//
//		command.setStates(states);
	}

	@RequestMapping(value="/controlpanel/contactus/list/resequence", method = RequestMethod.GET)
	public String resequence(
			@ModelAttribute ContactUsListCommand command,
			HttpServletRequest request, HttpServletResponse response)
		throws Throwable {
		ContactUsDisplayCommand contactUsCommands[] = command.getContactUsCommands();

		for (int i = 0; i < contactUsCommands.length; i++) {
			int seqNum = Integer.valueOf(contactUsCommands[i].getSeqNum());
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
		User user = AuthenticatedUtil.getAuthenticatedUser();

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

		User user = AuthenticatedUtil.getAuthenticatedUser();

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

		User user = AuthenticatedUtil.getAuthenticatedUser();

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

		int startRecord = (command.getPageNo() - 1) * Integer.valueOf(site.getListingPageSize());

		int endRecord = startRecord + Integer.valueOf(site.getListingPageSize());

		for (int i = startRecord; i < list.size() && i < endRecord; i++) {
			ContactUs contactUs = list.get(i);

			ContactUsDisplayCommand contactUsDisplay = new ContactUsDisplayCommand();

			contactUsDisplay.setContactUsId(contactUs.getContactUsId());
			contactUsDisplay.setContactUsName(contactUs.getName());
			contactUsDisplay.setContactUsEmail(contactUs.getEmail());
			contactUsDisplay.setContactUsPhone(contactUs.getPhone());
			contactUsDisplay.setActive(String.valueOf(contactUs.isActive()));
			contactUsDisplay.setSeqNum(String.valueOf(contactUs.getSeqNum()));

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
			% Integer.valueOf(site.getListingPageSize()))
			/ Integer.valueOf(site.getListingPageSize());

		if (list.size() % Integer.valueOf(site.getListingPageSize()) > 0) {
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