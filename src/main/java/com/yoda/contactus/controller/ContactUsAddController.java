package com.yoda.contactus.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.contactus.ContactUsEditCommand;
import com.yoda.contactus.ContactUsEditValidator;
import com.yoda.contactus.model.ContactUs;
import com.yoda.contactus.service.ContactUsService;
import com.yoda.country.model.Country;
import com.yoda.country.service.CountryService;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.state.model.State;
import com.yoda.state.service.StateService;
import com.yoda.user.model.User;
import com.yoda.util.Constants;

@Controller
@RequestMapping("/controlpanel/contactus/add")
public class ContactUsAddController {
	@Autowired
	CountryService countryService;

	@Autowired
	StateService stateService;

	@Autowired
	ContactUsService contactUsService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView setupForm(
			HttpServletRequest request, HttpServletResponse response) {
		User user = PortalUtil.getAuthenticatedUser();

		long siteId = user.getLastVisitSiteId();

		ContactUsEditCommand command = new ContactUsEditCommand();

		command.setActive(String.valueOf(Constants.ACTIVE_YES));

		initSearchInfo(command, siteId);

		return new ModelAndView(
			"controlpanel/contactus/edit", "contactUsEditCommand", command);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView add(
			@ModelAttribute ContactUsEditCommand command,
			BindingResult result, SessionStatus status,
			HttpServletRequest request)
		throws Throwable {

		User user = PortalUtil.getAuthenticatedUser();

		new ContactUsEditValidator().validate(command, result);

		ModelMap model = new ModelMap();

		if(result.hasErrors()) {
			initSearchInfo(command, user.getLastVisitSiteId());

			model.put("errors", "errors");

			return new ModelAndView("controlpanel/contactus/edit", model);
		}

		ContactUs contactUs = contactUsService.addContactUs(
			user.getLastVisitSiteId(), user.getUserId(), command.getActive(),
			command.getContactUsName(), command.getContactUsEmail(),
			command.getContactUsPhone(), command.getContactUsAddressLine1(),
			command.getContactUsAddressLine2(), command.getContactUsCityName(),
			command.getContactUsZipCode(), command.getSeqNum(),
			command.getContactUsDesc());

		command.setContactUsId(contactUs.getContactUsId());

		initSearchInfo(command, user.getLastVisitSiteId());

		return new ModelAndView("redirect:/controlpanel/contactus/" + contactUs.getContactUsId() + "/edit", model);
	}

	public void initSearchInfo(ContactUsEditCommand command, long siteId) {
		List<Country> countries = countryService.getBySiteId(siteId);

		command.setCountries(countries);

		List<State> states = stateService.getBySiteId(siteId);

		command.setStates(states);
	}
}