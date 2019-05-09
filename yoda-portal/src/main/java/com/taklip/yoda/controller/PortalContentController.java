package com.taklip.yoda.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.model.Comment;
import com.taklip.yoda.model.Content;
import com.taklip.yoda.model.ContentUserRate;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.model.Site;
import com.taklip.yoda.model.User;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.tool.Constants;
import com.taklip.yoda.tool.StringPool;
import com.taklip.yoda.util.AuthenticatedUtil;
import com.taklip.yoda.util.PortalUtil;
import com.taklip.yoda.validator.FrontendContentEditValidator;

@Controller
public class PortalContentController extends PortalBaseController {
	protected Logger logger =  LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected ContentService contentService;

	@Autowired
	protected ItemService itemService;

	@RequestMapping(value="/content/{contentId}", method = RequestMethod.GET)
	public ModelAndView showContent(
			@PathVariable("contentId") String contentId,
			HttpServletRequest request, HttpServletResponse response) {
		Site site = getSite(request);

		ModelMap model = new ModelMap();
		Content content = contentService.getContent(Long.valueOf(contentId));

		if (null == content || !PortalUtil.isContentPublished(content)) {
			model.put("pageTitle", site.getSiteName() + " - " + "Page not found");
			model.put("keywords", StringPool.BLANK);
			model.put("description", StringPool.BLANK);
			model.put("url", request.getRequestURL().toString());
			model.put("image", "http://" + site.getDomainName() + "/yoda/uploads/1/content/taklip-logo-560_L.png");

			return new ModelAndView("/404", "requestURL", request.getRequestURL().toString());
		}

		formatContent(request, response, model, content);

		setUserLoginStatus(request, response, model);

		model.put("site", site);

		pageViewHandler.add(request, ContentTypeEnum.CONTENT.getType(), content.getTitle(), content.getContentId());

		return new ModelAndView("portal/content/content", model);
	}

	@RequestMapping(value = "/content/{contentId}/edit", method = RequestMethod.GET)
	public ModelAndView setupForm(
			@PathVariable("contentId") String contentId,
			HttpServletRequest request, HttpServletResponse response) {
		Site site = getSite(request);

		Content content = new Content();

		try {
			content = contentService.getContent(Long.valueOf(contentId));
		}
		catch (Exception e) {
			logger.error(e.getMessage());

			return new ModelAndView("/404", "requestURL", request.getRequestURL().toString());
		}

		User currentUser = AuthenticatedUtil.getAuthenticatedUser();

		if ((currentUser == null) || (currentUser.getUserId() != content.getCreateBy().getUserId()) ) {
			return new ModelAndView("redirect:/login");
		}

		ModelMap model = new ModelMap();

		setUserLoginStatus(request, response, model);

		model.put("user", currentUser);
		model.put("content", content);
		model.put("site", site);

		return new ModelAndView("portal/user/contentEdit", model);
	}

	@RequestMapping(value = "/content/add", method = RequestMethod.GET)
	public ModelAndView setupForm(
			HttpServletRequest request, HttpServletResponse response) {
		ModelMap model = new ModelMap();

		User currentUser = AuthenticatedUtil.getAuthenticatedUser();

		if (currentUser == null) {
			return new ModelAndView("redirect:/login");
		}

		Site site = getSite(request);

		setUserLoginStatus(request, response, model);

		model.put("user", currentUser);
		model.put("site", site);

		Content content = new Content();

		model.put("content", content);

		return new ModelAndView("portal/user/contentEdit", model);
	}

	@RequestMapping(value = "/content/{contentId}/edit", method = RequestMethod.POST)
	public ModelAndView submitUpdate(
			@ModelAttribute Content content,
			BindingResult result, SessionStatus status,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		Site site = getSite(request);

		ModelMap model = new ModelMap();

		new FrontendContentEditValidator().validate(content, result);

		if(result.hasErrors()) {
			model.put("errors", "errors");

			return new ModelAndView("portal/user/contentEdit", model);
		}

		contentService.updateContent(site.getSiteId(), content, null);

		model.put("success", "success");

		return new ModelAndView("redirect:/content/" + content.getContentId() + "/edit", model);
	}

	@ResponseBody
	@RequestMapping(value="/content/{contentId}/rate" ,method = RequestMethod.POST)
	public String score(
			@PathVariable("contentId") Long contentId,
			@RequestParam("thumb") String thumb,
			HttpServletRequest request, HttpServletResponse response) {

		contentService.saveContentUserRate(contentId, thumb);

		int score = contentService.getContentRate(contentId);

		JSONObject jsonResult = new JSONObject();

		try {
			jsonResult.put("score", score);
		}
		catch (JSONException e) {
			logger.error("JSON : " + jsonResult + ", " + e.getMessage());
		}

		return jsonResult.toString();
	}

	@ResponseBody
	@RequestMapping(value="/content/page", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
	public String showPagination(
			@RequestParam(value="offset", defaultValue="0") Integer offset) {
		Pagination<Content> page = contentService.getContentsNotFeatureData(offset, 4);

		JSONArray array = new JSONArray();

		try {
			for (Content content : page.getData()) {
				if (!PortalUtil.isContentPublished(content)) {
					continue;
				}

				JSONObject jsonObject = new JSONObject();

				String contentUrl = StringPool.SLASH + Constants.FRONTEND_URL_CONTENT + StringPool.SLASH + content.getContentId();

				jsonObject.put("contentUrl", contentUrl);
				jsonObject.put("defaultImageUrl", content.getFeaturedImage());
				jsonObject.put("title", content.getTitle());
				jsonObject.put("hitCounter", content.getHitCounter());
				jsonObject.put("score", content.getScore());
				jsonObject.put("shortDescription", content.getShortDescription());

				array.add(jsonObject);
			}
		}
		catch (JSONException e) {
			logger.warn(e.getMessage());
		}

		return array.toString();
	}

	public void formatContent(
			HttpServletRequest request, HttpServletResponse response, 
			ModelMap model, Content content) {
		Site site = getSite(request);

		try {
			CsrfToken csrfToken = (CsrfToken)request.getAttribute(CsrfToken.class.getName());

			if (csrfToken != null) {
				model.put("_csrf", csrfToken);
			}

			List<Item> items = itemService.getItemsByContentId(content.getContentId());
			List<Comment> comments = getComments(content.getContentId());

			for (Item item : items) {
				shortenItemDescription(item);
			}

			String contentUrl = StringPool.SLASH + Constants.FRONTEND_URL_CONTENT + StringPool.SLASH + content.getContentId();

			String desc = content.getDescription();

			content.setDescription(desc.replace("img src", "img data-src"));
			content.setContentUrl(contentUrl);
			content.setItems(items);

			model.put("contentInfo", content);
			model.put("comments", comments);
			model.put("date", new Date());	
			model.put("backURL", URLEncoder.encode(request.getRequestURL().toString(), "UTF-8"));

			User loginUser = AuthenticatedUtil.getAuthenticatedUser();

			if (loginUser != null) {
				ContentUserRate rate = contentService.getContentUserRateByContentIdAndUserId(content.getContentId(), loginUser.getUserId());

				if (rate != null) {
					if (rate.getScore() == 1) {
						model.put("isUserLike", true);
					}
					else if (rate.getScore() == -1) {
						model.put("isUserDislike", true);
					}
				}

				model.put("userLogin", true);
			}
			else {
				model.put("userLogin", false);
			}

			model.put("pageTitle", "如何选购" + content.getTitle() + " - " + site.getSiteName());
			model.put("keywords", "如何选购" + content.getTitle() + "," + content.getTitle() + "怎么选" + "," + content.getTitle() + "品牌推荐");
			model.put("description", content.getShortDescription());
			model.put("url", request.getRequestURL().toString());
			model.put("image", "http://" + site.getDomainName() + content.getFeaturedImage());
		}
		catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
	}

	public List<Comment> getComments(long contentId) {
		return contentService.getComments(contentId);
	}

	private Item shortenItemDescription(Item item) {
		String desc = item.getDescription();

		if (desc.length() > 200) {
			desc = desc.substring(0, 200);

			if (desc.indexOf("img") > 0) {
				desc = desc.substring(0, desc.indexOf("<img"));
			}

			item.setDescription(desc);
		}

		return item;
	}
}