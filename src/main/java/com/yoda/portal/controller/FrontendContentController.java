package com.yoda.portal.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.velocity.tools.generic.DateTool;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.content.model.Comment;
import com.yoda.content.model.Content;
import com.yoda.content.model.ContentBrand;
import com.yoda.content.model.ContentUserRate;
import com.yoda.content.service.ContentService;
import com.yoda.item.model.Item;
import com.yoda.item.service.ItemService;
import com.yoda.kernal.model.Pagination;
import com.yoda.kernal.servlet.ServletContextUtil;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.site.model.Site;
import com.yoda.user.model.User;
import com.yoda.util.Constants;
import com.yoda.util.StringPool;
import com.yoda.util.Utility;
import com.yoda.util.Validator;

@Controller
@RequestMapping(value="/content")
public class FrontendContentController extends BaseFrontendController {
	Logger logger = Logger.getLogger(FrontendContentController.class);

	@Autowired
	protected ContentService contentService;

	@Autowired
	protected ItemService itemService;

	@RequestMapping(value="/{contentId}", method = RequestMethod.GET)
	public ModelAndView showContent(
			@PathVariable("contentId") String contentId,
			HttpServletRequest request, HttpServletResponse response) {
		Site site = getSite(request);

		ModelMap model = new ModelMap();
		Content content = contentService.getContent(Long.valueOf(contentId));

		if (Validator.isNull(content) || !Utility.isContentPublished(content)) {
			model.put("pageTitle", site.getSiteName() + " - " + "Page not found");
			model.put("keywords", StringPool.BLANK);
			model.put("description", StringPool.BLANK);
			model.put("url", request.getRequestURL().toString());
			model.put("image", "http://" + site.getDomainName() + "/yoda/uploads/1/content/taklip-logo-560_L.png");

			return new ModelAndView("/404", "requestURL", request.getRequestURL().toString());
		}

		formatContent(request, response, model, content);

		getHorizontalMenu(request, response, model);

		model.put("site", site);

		return new ModelAndView("portal/content/content", model);
	}

	public void formatContent(
			HttpServletRequest request, HttpServletResponse response, 
			ModelMap model, Content content) {
		Site site = getSite(request);

		try {
			kafkaTemplate.send(Constants.KAFKA_TOPIC_REDIS_INCR, Constants.REDIS_CONTENT_HIT_COUNTER, String.valueOf(content.getContentId()));

			CsrfToken csrfToken = (CsrfToken)request.getAttribute(CsrfToken.class.getName());

			if (csrfToken != null) {
				model.put("_csrf", csrfToken);
			}

			List<Item> items = itemService.getItemsByContentId(content.getContentId());
			List<Comment> comments = getComments(content.getContentId());

			for (Item item : items) {
				shortenItemDescription(item);
			}

			for (ContentBrand cb : content.getContentBrands()) {
				cb.setDescription(cb.getDescription().replace("<img src=\"/upload", "<img src=\"/yoda/upload"));
			}

			String contentUrl = ServletContextUtil.getContextPath() + StringPool.SLASH + Constants.FRONTEND_URL_CONTENT + StringPool.SLASH + content.getContentId();

			String desc = content.getDescription();

			content.setDescription(desc.replace("img src", "img data-src"));
			content.setContentUrl(contentUrl);
			content.setItems(items);

			model.put("contentInfo", content);
			model.put("comments", comments);
			model.put("date", new DateTool());	
			model.put("backURL", URLEncoder.encode(request.getRequestURL().toString(), "UTF-8"));

			User loginUser = PortalUtil.getAuthenticatedUser();

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

	@ResponseBody
	@RequestMapping(value="/{contentId}/rate" ,method = RequestMethod.POST)
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
	@RequestMapping(value="/page", method = RequestMethod.GET, produces = {"application/json; charset=UTF-8"})
	public String showPagination(
			@RequestParam(value="offset", defaultValue="0") Integer offset) {
		Pagination<Content> page = contentService.getContentsNotFeatureData(offset, 4);

		JSONArray array = new JSONArray();

		try {
			for (Content content : page.getData()) {
				if (!Utility.isContentPublished(content)) {
					continue;
				}

				JSONObject jsonObject = new JSONObject();

				String contentUrl = ServletContextUtil.getContextPath() + StringPool.SLASH + Constants.FRONTEND_URL_CONTENT + StringPool.SLASH + content.getContentId();

				jsonObject.put("contentUrl", contentUrl);
				jsonObject.put("defaultImageUrl", content.getFeaturedImage());
				jsonObject.put("title", content.getTitle());
				jsonObject.put("hitCounter", content.getHitCounter());
				jsonObject.put("score", content.getScore());
				jsonObject.put("shortDescription", content.getShortDescription());

				array.put(jsonObject);
			}
		}
		catch (JSONException e) {
			logger.warn(e);
		}

		return array.toString();
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