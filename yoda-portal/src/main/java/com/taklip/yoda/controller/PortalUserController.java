package com.taklip.yoda.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.taklip.yoda.model.*;
import com.taklip.yoda.service.*;
import com.taklip.yoda.util.AuthenticatedUtil;
import com.taklip.yoda.util.Validator;
import com.taklip.yoda.validator.UserSettingsValidator;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class PortalUserController extends PortalBaseController {
	private final Logger logger = LoggerFactory.getLogger(PortalUserController.class);

	@Autowired
	protected ContentService contentService;

	@Autowired
	protected UserService userService;

	@Autowired
	protected UserFollowerService userFollowerService;

	@Autowired
	protected UserFolloweeService userFolloweeService;

	@Autowired
	protected PostService postService;

	@Autowired
	protected AuthenticationManager authenticationManager;

	@GetMapping("/{id}")
	public ModelAndView setupForm(
			@PathVariable("id") long id, HttpServletRequest request) {
		ModelMap model = new ModelMap();

		Site site = getSite();

		User user = userService.getUser(id);

		if (null == user) {
			return new ModelAndView("/404", "requestURL", id);
		}

		Pagination<Post> page = postService.getPostsByUser(id, new RowBounds(0, 10));
		List<Content> contents = contentService.getContentByUserId(user.getId());
		int followerCount = userFollowerService.getUserFollowerCount(user.getId());
		int followeeCount = userFolloweeService.getUserFolloweeCount(user.getId());

		model.put("user", user);
		model.put("contents", contents);
		model.put("page", page);
		model.put("followerCount", followerCount);
		model.put("followeeCount", followeeCount);

		setUserLoginStatus(model);

		model.put("pageTitle", user.getUsername() + " | " + site.getSiteName());
		model.put("keywords", "如何选购适合自己的产品,网购,科普,品牌推荐,产品推荐");
		model.put("description", "「taklip太离谱」提供的内容是为了帮用户更有效的选择适合自己的产品。基本每篇内容都包括以下部分：需要知道、相关品牌、推荐产品。");
		model.put("url", request.getRequestURL().toString());
		model.put("image", "http://" + site.getDomainName() + "/yoda/uploads/1/content/taklip-logo-560_L.png");
		model.put("site", site);

		User currentUser = AuthenticatedUtil.getAuthenticatedUser();

		model.put("currentUser", currentUser);

		return new ModelAndView("portal/user/profile", model);
	}

	@GetMapping("/settings")
	public ModelAndView setupForm(HttpServletRequest request) {
		Site site = getSite();

		ModelMap model = new ModelMap();

		User user = AuthenticatedUtil.getAuthenticatedUser();

		if (null == user) {
			return new ModelAndView("redirect:/login", model);
		}

		model.put("user", user);
		model.put("tab", "basic");

		setUserLoginStatus(model);

		model.put("pageTitle", user.getUsername() + " | " + site.getSiteName());
		model.put("keywords", "如何选购适合自己的产品,网购,科普,品牌推荐,产品推荐");
		model.put("description", "「taklip太离谱」提供的内容是为了帮用户更有效的选择适合自己的产品。基本每篇内容都包括以下部分：需要知道、相关品牌、推荐产品。");
		model.put("url", request.getRequestURL().toString());
		model.put("image", "http://" + site.getDomainName() + "/yoda/uploads/1/content/taklip-logo-560_L.png");
		model.put("site", site);

		return new ModelAndView("portal/user/settings", model);
	}

	@PostMapping("/settings")
	public ModelAndView update(
			@ModelAttribute User user,
			@RequestParam("photo") MultipartFile photo,
			BindingResult result) {
		new UserSettingsValidator().validate(user, result);

		ModelMap model = new ModelMap();

		if (result.hasErrors()) {
			logger.error(result.toString());
			model.put("errors", "errors");
			return new ModelAndView("portal/user/settings", model);
		}

		Site site = getSite();

		setUserLoginStatus(model);

		model.put("site", site);

		User userDb = userService.update(user, photo);

		Authentication authentication = new UsernamePasswordAuthenticationToken(userDb, userDb.getPassword());

		SecurityContextHolder.getContext().setAuthentication(authentication);

		model.put("success", "success");
		model.put("user", userDb);

		return new ModelAndView("portal/user/settings", model);
	}

	@GetMapping("/register")
	public String setupForm(@ModelAttribute User user) {
		return "portal/user/register";
	}

	@PostMapping("/register")
	public ModelAndView submit(
			@RequestParam("username") String username,
			@RequestParam("email") String email,
			@RequestParam("password") String password) {
		ModelAndView model = new ModelAndView();

		User userDb = userService.getUserByUserName(username);

		if (null != userDb) {
			model.addObject("error", "duplicate-username");

			model.setViewName("portal/user/register");

			return model;
		}

		if (!Validator.isEmailAddress(email)) {
			model.addObject("error", "invalid-email");

			model.setViewName("portal/user/register");

			return model;
		}

		userDb = userService.getUserByEmail(email);

		if (null != userDb) {
			model.addObject("error", "duplicate-email");

			model.setViewName("portal/user/register");

			return model;
		}

		try {
			User user = new User();
			user.setPassword(password);
			user.setUsername(username);
			user.setEmail(email);
			userService.add(user);
		} catch (Exception e) {
			logger.error("Saving User with username:" + username + " - password:" + password + " - email:" + email + e.getMessage());
		}

		try {
			UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(email, password);

			Authentication result = authenticationManager.authenticate(authResult);

			// redirect into secured main page if authentication successful
			if (result.isAuthenticated()) {
				SecurityContextHolder.getContext().setAuthentication(result);
				model.setViewName("redirect:/");

				return model;
			}
		} catch (Exception e) {
			logger.debug("Problem authenticating user" + username, e);
		}

		model.setViewName("redirect:/");

		return model;
	}

	@ResponseBody
	@PostMapping("/register/ajax")
	public String ajaxRegister(
			@RequestParam("username") String username,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			HttpServletRequest request) {
		User userDb = userService.getUserByUserName(username);

		RequestContext requestContext = new RequestContext(request);

		JSONObject jsonResult = new JSONObject();

		try {
			if (null != userDb) {
				jsonResult.put("error", requestContext.getMessage("duplicate-username"));
			}

			if (!Validator.isEmailAddress(email)) {
				jsonResult.put("error", requestContext.getMessage("invalid-email"));
			}

			userDb = userService.getUserByEmail(email);

			if (null != userDb) {
				jsonResult.put("error", requestContext.getMessage("duplicate-email"));
			}
		} catch (JSONException e) {
			logger.error(e.getMessage());
		}

		if (jsonResult.isEmpty()) {
			try {
				User user = new User();
				user.setPassword(password);
				user.setUsername(username);
				user.setEmail(email);
				userService.add(user);
			} catch (Exception e) {
				logger.error("Saving User with username:" + username + " - password:" + password + " - email:" + email + e.getMessage());
			}

			try {
				UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(email, password);

				Authentication result = authenticationManager.authenticate(authResult);

				// redirect into secured main page if authentication successful
				if (result.isAuthenticated()) {
					SecurityContextHolder.getContext().setAuthentication(result);
				}
			} catch (Exception e) {
				logger.debug("Problem authenticating user" + username, e);
			}
		}

		String jsonString = jsonResult.toString();

		return jsonString;
	}

	@RequestMapping(value = "/post/new", method = RequestMethod.POST)
	public String addPost(@ModelAttribute Post post) {

		post.setDescription(HtmlUtils.htmlEscape(post.getDescription()));

		postService.save(post);

		return "redirect:/user/" + post.getUserId();
	}

	@ResponseBody
	@RequestMapping(value = "/post/page", method = RequestMethod.GET)
	public String showPagination(
			@RequestParam("userId") Long userId,
			@RequestParam(value = "offset", defaultValue = "0") Integer offset) {
		Pagination<Post> page = postService.getPostsByUser(userId, new RowBounds(offset, 10));

		JSONArray array = new JSONArray();

		SimpleDateFormat datetimeformat = new SimpleDateFormat("yyyy-MM-dd hh:mm");

		try {
			for (Post post : page.getData()) {
				JSONObject jsonObject = new JSONObject();

				jsonObject.put("id", post.getId());
				jsonObject.put("description", post.getDescription());
				jsonObject.put("createDate", datetimeformat.format(post.getCreateDate()));

				array.add(jsonObject);
			}
		} catch (JSONException e) {
			logger.error(e.getMessage());
		}

		return array.toString();
	}
}