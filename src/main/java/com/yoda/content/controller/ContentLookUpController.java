package com.yoda.content.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.yoda.content.model.Content;
import com.yoda.content.service.ContentService;
import com.yoda.kernal.util.PortalUtil;
import com.yoda.user.model.User;
import com.yoda.util.Constants;
import com.yoda.util.Validator;

@Controller
@RequestMapping("/controlpanel/lookup/contentLookup")
public class ContentLookUpController {
	@Autowired
	ContentService contentService;

	@RequestMapping(method = RequestMethod.POST)
	public void lookUpContent(
			@RequestParam(value = "contentTitle", required = false) String contentTitle,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		User user = PortalUtil.getAuthenticatedUser();

		List<Content> contents = new ArrayList<Content>();

		if (Validator.isNotNull(contentTitle)) {
			contents = contentService.getContents(user.getLastVisitSiteId(), contentTitle);
		}
		else {
			contents = contentService.getContents(user.getLastVisitSiteId());
		}

		JSONObject jsonResult = new JSONObject();

		jsonResult.put("contentTitle", contentTitle);

		int counter = 0;

		Vector<JSONObject> vector = new Vector<JSONObject>();

		for (Content content : contents) {
			JSONObject jsonContent = new JSONObject();

			jsonContent.put("contentId", content.getContentId());
			jsonContent.put("contentTitle", content.getTitle());

			vector.add(jsonContent);

			counter++;

			if (counter == Constants.ADMIN_SEARCH_MAXCOUNT) {
//				MessageResources resources = this.getResources(request);
				jsonResult.put("message","error.lookup.tooManyRecord");
//						resources.getMessage("error.lookup.tooManyRecord"));
				break;
			}
		}

		jsonResult.put("contents", vector);

		String jsonString = jsonResult.toString();

		response.setContentType("text/html");
		response.setContentLength(jsonString.length());

		OutputStream outputStream = response.getOutputStream();

		outputStream.write(jsonString.getBytes());
		outputStream.flush();
//		return null;
	}
}
