package com.yoda.portal.account.controller;//package com.yoda.portal.account.controller;
//
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.log4j.Logger;
//import org.hibernate.Hibernate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.support.SessionStatus;
//
//import com.yoda.portal.account.AccountLoginCommand;
//import com.yoda.portal.account.AccountLoginValidator;
//import com.yoda.portal.controller.BaseContentController;
//import com.yoda.util.StringPool;
//
//@Controller
//@RequestMapping("/account/login/accountLogin")
//public class AccountLoginController extends BaseContentController {
//	@Autowired
//	CustomerService customerService;
//
//	Logger logger = Logger.getLogger(AccountLoginController.class);
//
//	@RequestMapping(method=RequestMethod.GET)
//	public String start(
//			BindingResult result, SessionStatus status,
//			HttpServletRequest request, HttpServletResponse response) {
//		String action = request.getParameter("action");
//
//		String page = StringPool.BLANK;
//
//		if (action != null) {
//			if (action.equals("logout")) {
//				result.rejectValue("message", "message.logout.successful");
//			}
//
//			if (action.equals("forgot")) {
//				result.rejectValue("message", "message.forgot.successful");
//			}
//		}
//
//		try {
//			if (getCustomer(request) != null) {
//				page = "redirect:/account/page";
//			}
//			else {
//				page = "account/login/accountLogin";
//			}
//
//			createEmptySecureTemplateInfo(request);
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return page;
//	}
//
//	@RequestMapping(method=RequestMethod.POST)
//	public String login(
//			@ModelAttribute AccountLoginCommand command,
//			BindingResult result, SessionStatus status,
//			HttpServletRequest request, HttpServletResponse response)
//		throws Throwable {
//		createEmptySecureTemplateInfo(request);
//
//		new AccountLoginValidator().validate(command, result);
//
//		if(result.hasErrors()) {
//			return "account/login/accountLogin";
//		}
//
//		List<Customer> customers = customerService.getCustomer(getContentBean(request).getSite().getSiteId(), command.getCustEmail());
//
//		if (customers.size() == 0) {
//			command.setCustPassword("");
//
//			result.reject("login", "error.login.invalid");
//
//			return "account/login/accountLogin";
//		}
//
//		Customer customer = (Customer)customers.get(0);
//
//		if (!customer.getCustPassword().equals(command.getCustPassword())) {
//			command.setCustPassword("");
//
//			result.reject("login", "error.login.invalid");
//
//			return "account/login/accountLogin";
//		}
//
//		setCustId(request, customer.getCustId());
//
//		Hibernate.initialize(customer);
//
//		setCachedCustomer(request, customer);
//
//		return "redirect:/account/page";
//	}
//}