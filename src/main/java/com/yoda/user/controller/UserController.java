package com.yoda.user.controller;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.kernal.util.PortalUtil;
import com.yoda.site.model.Site;
import com.yoda.site.service.SiteService;
import com.yoda.user.UserDisplayCommand;
import com.yoda.user.UserListCommand;
import com.yoda.user.model.User;
import com.yoda.user.service.UserService;
import com.yoda.util.Constants;
import com.yoda.util.Format;

@Controller
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	SiteService siteService;

	@RequestMapping(value="/controlpanel/user/list", method = RequestMethod.GET)
	public ModelAndView showPanel(
		HttpServletRequest request, HttpServletResponse response) {
		UserListCommand command = new UserListCommand();

		command.setSrPageNo("");
		command.setType("*");
		command.setActive("*");

		User user = PortalUtil.getAuthenticatedUser();

		extract(command, user);

		command.setEmpty(false);

		return new ModelAndView(
			"controlpanel/user/list", "userListCommand", command);
	}

	@RequestMapping(value="/controlpanel/user/list/search", method = RequestMethod.POST)
	public String search(
			@ModelAttribute UserListCommand command,
			BindingResult result, SessionStatus status,
			HttpServletRequest request) {

		command.setSrPageNo("");

		User user = PortalUtil.getAuthenticatedUser();

		extract(command, user);

		command.setEmpty(false);

		return "controlpanel/user/list";
	}

	@RequestMapping(value = "/controlpanel/user/list/remove", method = RequestMethod.GET)
	public String removeUsers(
			@RequestParam("userIds") String userIds,
			HttpServletRequest request) {
		User signinUser = PortalUtil.getAuthenticatedUser();

		String[] arrIds = userIds.split(",");

		for (int i = 0; i < arrIds.length; i++) {
			userService.deleteUser(Long.valueOf(arrIds[i]), signinUser);
		}

		return "redirect:/controlpanel/user/list";
	}

	public void extract(UserListCommand command, User signinUser) {
		Site site = siteService.getSite(signinUser.getLastVisitSiteId());

		if (!signinUser.getUserType().equals(Constants.USERTYPE_ADMIN)
			&& !signinUser.getUserType().equals(Constants.USERTYPE_SUPER)) {
			command.setUsers(null);

			return;
		}

//		Query query = null;

		if (command.getSrPageNo().length() == 0) {
			command.setSrPageNo("1");
		}

		List<User> users = userService.search(
			command.getUserId(), command.getUserName(),
			command.getType(), command.getActive());

		int pageNo = Integer.parseInt(command.getSrPageNo());

		calcPage(signinUser, command, users, pageNo);

		Vector<UserDisplayCommand> vector = new Vector<UserDisplayCommand>();

		int startRecord = (command.getPageNo() - 1) * Format.getInt(site.getListingPageSize());

		int endRecord = startRecord + Format.getInt(site.getListingPageSize());

		for (int i = startRecord; i < users.size() && i < endRecord; i++) {

			User user = (User) users.get(i);

			if (!userService.hasAccess(signinUser, user)) {
				continue;
			}

			UserDisplayCommand userDisplay = new UserDisplayCommand();

			userDisplay.setUserId(user.getUserId());
			userDisplay.setUserName(user.getUsername());
			userDisplay.setEmail(user.getEmail());
			userDisplay.setUserPhone(user.getPhone());
//			userDisplay.setUserType(resources.getMessage("user.type." + user.getUserType()));
			userDisplay.setUserType("user.type." + user.getUserType());
			userDisplay.setActive(String.valueOf(user.getActive()));

			vector.add(userDisplay);
		}

		UserDisplayCommand userDisplayForm[] = new UserDisplayCommand[vector.size()];

		vector.copyInto(userDisplayForm);

		command.setUsers(userDisplayForm);
	}

	protected void calcPage(
			User user, UserListCommand command, List<User> list, int pageNo) {

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