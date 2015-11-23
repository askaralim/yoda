package com.yoda.contactus.controller;

import java.util.List;

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

@Controller
@RequestMapping("/controlpanel/contactus/{contactUsId}/edit")
public class ContactUsEditController {
//	@Autowired
//	CountryService countryService;
//
//	@Autowired
//	StateService stateService;

	@Autowired
	ContactUsService contactUsService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showPanel(
		@PathVariable("contactUsId") int contactUsId,
		HttpServletRequest request, HttpServletResponse response) {
		User user = PortalUtil.getAuthenticatedUser();

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

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView update(
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
}