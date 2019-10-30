package com.taklip.yoda.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.taklip.yoda.model.ImageFile;
import com.taklip.yoda.model.Response;
import com.taklip.yoda.service.FileService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {
	private final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	FileService fileService;

	@ResponseBody
	@GetMapping("/list")
	public Response<Object> list(
		@RequestParam(name = "contentId", required = false) Long contentId,
		@RequestParam(name = "contentType", required = false) String contentType) {
		List<ImageFile> files = fileService.getFilesByContent(contentType, contentId);

		JSONArray array = new JSONArray();

		try {
			for (ImageFile file : files) {
				JSONObject jsonObject = new JSONObject();

				jsonObject.put("id", file.getId());
				jsonObject.put("filePath", file.getFilePath());

				array.add(jsonObject);
			}
		}
		catch (JSONException e) {
			logger.error(e.getMessage());
		}

//		return array.toString();
		return new Response<>(200, "success", array);
	}

	@ResponseBody
	@PostMapping("/add")
	public Response<Object> save(
		Map<String, Object> model,
		@RequestParam(name = "modalContentId", required = false) Long contentId,
		@RequestParam(name = "modalContentType", required = false) String contentType,
		@RequestParam("file") MultipartFile[] files,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		for (MultipartFile file : files) {
			if (file.getBytes().length <= 0) {
				new Response<>(500, "fail", null);
			}

			if (StringUtils.isEmpty(file.getName())) {
				new Response<>(500, "fail", null);
			}

			fileService.save(contentType, contentId, file);
		}

		return new Response<>(200, "success", null);
	}
}