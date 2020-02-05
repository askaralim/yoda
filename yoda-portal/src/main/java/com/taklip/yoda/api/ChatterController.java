package com.taklip.yoda.api;

import com.taklip.yoda.json.JSONArray;
import com.taklip.yoda.json.JSONObject;
import com.taklip.yoda.model.*;
import com.taklip.yoda.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatterController {
	@Autowired
	ChatterService chatterService;

	@Autowired
	private ContentService contentService;

	@Autowired
	private BrandService brandService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private TermService termService;

	@GetMapping("/echo")
	public ResponseEntity<ChatResponse> echo() {
		return new ResponseEntity("Success", HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<String> getAnswer(@RequestParam("question") String question) {
		// TODO save questions
		ChatResponse chatResponse = chatterService.getAnswer(question);
		if (chatResponse != null) {
			return new ResponseEntity(chatResponse, HttpStatus.OK);
		}

		return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/export")
	public ResponseEntity<String> exportTrainingData() {
		exportContents();
		exportBrands();
		exportItems();
		exportTerms();

		return new ResponseEntity("", HttpStatus.OK);
	}

	@GetMapping("/train")
	public ResponseEntity<String> train() {


		return new ResponseEntity("", HttpStatus.OK);
	}

	private void exportBrands() {
		List<Brand> brands = brandService.getBrands();

		JSONObject obj = new JSONObject();
		JSONArray questions = new JSONArray();

		for (Brand brand : brands) {
			String name = brand.getName();
			String desc = brand.getDescription();

			desc = desc.replaceAll("\\<.*?\\>", "");

			JSONArray conversation = new JSONArray();

			conversation.add(name);

			StringBuffer sb = new StringBuffer();

			sb.append("【" + name + "】\\n");
			sb.append(desc);
			sb.append("\\n");
			sb.append("详细内容请<a href=\"localhost/brand/" + brand.getId() + "\">点击这里</a>");

			conversation.add(sb.toString());

			questions.add(conversation);
		}

		obj.put("conversations", questions);

		//Write JSON file
		try (FileWriter file = new FileWriter("brands.json")) {
			file.write(obj.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void exportContents() {
		List<Content> contents = contentService.getContents();

		JSONObject obj = new JSONObject();
		JSONArray questions = new JSONArray();

		for (Content content : contents) {
			String title = content.getTitle();
			String shortDesc = content.getShortDescription();

			JSONArray conversation = new JSONArray();

			conversation.add(title);

			StringBuffer sb = new StringBuffer();

			sb.append("【" + title + "】\\n");
			sb.append(shortDesc);
			sb.append("\\n");
			sb.append("详细内容请<a href=\"localhost/content/" + content.getId() + "\">点击这里</a>");

			conversation.add(sb.toString());

			questions.add(conversation);
		}

		obj.put("conversations", questions);

		//Write JSON file
		try (FileWriter file = new FileWriter("contents.json")) {
			file.write(obj.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void exportItems() {
		List<Item> items = itemService.getItems();

		JSONObject obj = new JSONObject();
		JSONArray questions = new JSONArray();

		for (Item item : items) {
			String name = item.getName();
			String desc = item.getDescription();

			desc = desc.replaceAll("\\<.*?\\>", "");

			JSONArray conversation = new JSONArray();

			conversation.add(name);

			StringBuffer sb = new StringBuffer();

			sb.append("【" + name + "】\\n");
			sb.append(desc);
			sb.append("\\n");
			sb.append("详细内容请<a href=\"localhost/item/" + item.getId() + "\">点击这里</a>");

			conversation.add(sb.toString());

			questions.add(conversation);
		}

		obj.put("conversations", questions);

		//Write JSON file
		try (FileWriter file = new FileWriter("items.json")) {
			file.write(obj.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void exportTerms() {
		List<Term> terms = termService.getTerms();

		JSONObject obj = new JSONObject();
		JSONArray questions = new JSONArray();

		for (Term term : terms) {
			String title = term.getTitle();
			String desc = term.getDescription();

			desc = desc.replaceAll("\\<.*?\\>", "");

			JSONArray conversation = new JSONArray();

			conversation.add(title);

			StringBuffer sb = new StringBuffer();

			sb.append("【" + title + "】\\n");
			sb.append(desc);
			sb.append("\\n");
			sb.append("详细内容请<a href=\"localhost/term/" + term.getId() + "\">点击这里</a>");

			conversation.add(sb.toString());

			questions.add(conversation);
		}

		obj.put("conversations", questions);

		//Write JSON file
		try (FileWriter file = new FileWriter("terms.json")) {
			file.write(obj.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}