package com.yoda.item.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yoda.item.ItemValidator;
import com.yoda.item.model.Item;
import com.yoda.item.service.ItemService;
import com.yoda.kernal.util.FileUploadUtil;
import com.yoda.util.Format;

@Controller
public class ItemEditController {
	@Autowired
	ItemService itemService;

	@RequestMapping(value = "/controlpanel/items/{itemId}/edit", method = RequestMethod.GET)
	public String initUpdateForm(@PathVariable("itemId") int itemId, Map<String, Object> model) {
		Item item = itemService.getItem(itemId);

		model.put("item", item);

		return "controlpanel/items/form";
	}

	@RequestMapping(value = "/controlpanel/items/{itemId}/edit", method = {RequestMethod.PUT, RequestMethod.POST})
	public ModelAndView processUpdateForm(
			@ModelAttribute("item") Item item, BindingResult result, SessionStatus status) {
		new ItemValidator().validate(item, result);

		ModelMap model = new ModelMap();

		if (result.hasErrors()) {
			model.put("errors", "errors");

			return new ModelAndView("controlpanel/items/form", model);
		}
		else {
			Item itemDb = itemService.update(
				item.getId(), item.getBrand(), item.getDescription(),
				item.getLevel(), item.getName(), item.getPrice());

			model.put("item", itemDb);
			model.put("success", "success");

			status.setComplete();

			return new ModelAndView("controlpanel/items/form", model);
		}
	}

	@RequestMapping(value = "/controlpanel/items/{id}/uploadImage", method = RequestMethod.POST)
	public String uploadImage(
			@RequestParam("file") MultipartFile file,
			@PathVariable("id") int id, HttpServletRequest request,
			HttpServletResponse response)
		throws Throwable {
		byte[] fileData = file.getBytes();

		if (fileData.length <= 0) {
			return "redirect:/controlpanel/items/" + id + "/edit";
		}

		if (Format.isNullOrEmpty(file.getName())) {
			return "redirect:/controlpanel/items/" + id + "/edit";
		}

		String savedPath = new FileUploadUtil().saveImage(file);

		itemService.updateItemImage(id, savedPath);

		return "redirect:/controlpanel/items/" + id + "/edit";
	}

	@RequestMapping(value="/items/{id}/rating" ,method = RequestMethod.POST)
	public void score(
			@PathVariable("id") int id,
			@RequestParam("thumb") String thumb,
			HttpServletRequest request, HttpServletResponse response) {
		Item item  = itemService.getItem(id);

		int rating = 0;

		if (thumb.equals("up")) {
			rating = item.getRating() + 1;
		}
		else if (thumb.equals("down")) {
			rating = item.getRating() - 1;
		}

		item.setRating(rating);

		itemService.update(item);

		JSONObject jsonResult = new JSONObject();

		try {
			jsonResult.put("rating", rating);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}

		String jsonString = jsonResult.toString();

		PrintWriter printWriter = null;

		try {
			printWriter = response.getWriter();
			printWriter.print(jsonString);
		}
		catch (IOException ex) {
		}
		finally {
			if (null != printWriter) {
				printWriter.flush();
				printWriter.close();
			}
		}
	}
}