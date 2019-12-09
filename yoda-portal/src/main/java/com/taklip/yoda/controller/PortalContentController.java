package com.taklip.yoda.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.model.*;
import com.taklip.yoda.service.ContentService;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.tool.StringPool;
import com.taklip.yoda.util.AuthenticatedUtil;
import com.taklip.yoda.util.PortalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/content")
public class PortalContentController extends PortalBaseController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected ContentService contentService;

	@Autowired
	protected ItemService itemService;
	
	@GetMapping("/{id}")
	public ModelAndView showContent(
			@PathVariable("id") String id, HttpServletRequest request) {
		Site site = getSite();

		ModelMap model = new ModelMap();
		Content content = contentService.getContent(Long.valueOf(id));

		if (null == content || !PortalUtil.isContentPublished(content)) {
			model.put("pageTitle", site.getSiteName() + " - " + "Page not found");
			model.put("keywords", StringPool.BLANK);
			model.put("description", StringPool.BLANK);
			model.put("url", request.getRequestURL().toString());
			model.put("image", "http://" + site.getDomainName() + "/yoda/uploads/1/content/taklip-logo-560_L.png");

			return new ModelAndView("/404", "requestURL", request.getRequestURL().toString());
		}

		formatContent(request, model, content);

		setUserLoginStatus(model);

		model.put("site", site);

		pageViewHandler.add(request, ContentTypeEnum.CONTENT.getType(), content.getTitle(), content.getId());

		return new ModelAndView("portal/content", model);
	}

	@GetMapping("/{id}/edit")
	public ModelAndView setupForm(
			@PathVariable("id") String id, HttpServletRequest request) {
		Site site = getSite();

		Content content;

		try {
			content = contentService.getContent(Long.valueOf(id));
		}
		catch (Exception e) {
			logger.error(e.getMessage());

			return new ModelAndView("/404", "requestURL", request.getRequestURL().toString());
		}

		User currentUser = AuthenticatedUtil.getAuthenticatedUser();

		if ((currentUser == null) || (currentUser.getId() != content.getCreateBy().getId()) ) {
			return new ModelAndView("redirect:/login");
		}

		ModelMap model = new ModelMap();

		setUserLoginStatus(model);

		model.put("user", currentUser);
		model.put("content", content);
		model.put("site", site);

		return new ModelAndView("portal/user/contentEdit", model);
	}

	@GetMapping("/add")
	public ModelAndView setupForm() {
		ModelMap model = new ModelMap();

		User currentUser = AuthenticatedUtil.getAuthenticatedUser();

		if (currentUser == null) {
			return new ModelAndView("redirect:/login");
		}

		Site site = getSite();

		setUserLoginStatus(model);

		model.put("user", currentUser);
		model.put("site", site);

		Content content = new Content();

		model.put("content", content);

		return new ModelAndView("portal/user/contentEdit", model);
	}

	@PostMapping("/save")
	public ModelAndView submitUpdate(
			@Valid Content content, BindingResult result)
		throws Exception {
		ModelMap model = new ModelMap();

		if(result.hasErrors()) {
			model.put("errors", "errors");

			return new ModelAndView("portal/user/contentEdit", model);
		}

		contentService.updateContent(content, null);

		model.put("success", "success");

		return new ModelAndView("redirect:/content/" + content.getId() + "/edit", model);
	}

	@ResponseBody
	@PostMapping("/{id}/rate")
	public String score(
			@PathVariable("id") Long id, @RequestParam("thumb") String thumb) {

		contentService.saveContentUserRate(id, thumb);

		int score = contentService.getContentRate(id);

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
	@RequestMapping(value="/page", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
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

				jsonObject.put("id", content.getId());
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
			HttpServletRequest request, ModelMap model, Content content) {
		Site site = getSite();

		try {
//			CsrfToken csrfToken = (CsrfToken)request.getAttribute(CsrfToken.class.getName());
//
//			if (csrfToken != null) {
//				model.put("_csrf", csrfToken);
//			}

			List<Item> items = itemService.getItemsByContentId(content.getId());
			List<Comment> comments = getComments(content.getId());

			for (Item item : items) {
				shortenItemDescription(item);
			}

			String desc = content.getDescription();

			content.setDescription(desc.replace("img src", "img data-src"));
			content.setItems(items);

			model.put("content", content);
			model.put("comments", comments);
			model.put("date", new Date());
			model.put("backURL", URLEncoder.encode(request.getRequestURL().toString(), "UTF-8"));

			User loginUser = AuthenticatedUtil.getAuthenticatedUser();

			if (loginUser != null) {
				ContentUserRate rate = contentService.getContentUserRateByContentIdAndUserId(content.getId(), loginUser.getId());

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