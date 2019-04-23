package com.taklip.yoda.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.taklip.yoda.model.ImageFile;
import com.taklip.yoda.model.JsonResponse;
import com.taklip.yoda.service.FileService;

@RestController
@RequestMapping("/file")
public class FileController {
	@Autowired
	FileService fileService;

	@RequestMapping(value="/list", method = RequestMethod.GET)
	public List<ImageFile> list(
		@RequestParam(name = "contentId", required = false) Long contentId,
		@RequestParam(name = "contentType", required = false) String contentType,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		List<ImageFile> files = fileService.getFilesByContent(contentType, contentId);

		return files;
	}

	@ResponseBody
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public JsonResponse save(
		Map<String, Object> model,
		@RequestParam(name = "modalContentId", required = false) Long contentId,
		@RequestParam(name = "modalContentType", required = false) String contentType,
		@RequestParam("file") MultipartFile[] files,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		for (MultipartFile file : files) {
			if (file.getBytes().length <= 0) {
				new JsonResponse<>(500, "fail", null);
			}

			if (StringUtils.isEmpty(file.getName())) {
				new JsonResponse<>(500, "fail", null);
			}

			fileService.save(contentType, contentId, file);
		}

		return new JsonResponse<>(200, "success", null);
	}
}