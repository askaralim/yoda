package com.taklip.yoda.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import com.taklip.yoda.model.ImageFile;
import com.taklip.yoda.service.FileService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<Object> list(
        @RequestParam(required = false) Long contentId,
        @RequestParam(required = false) String contentType) {
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
        // return array.toString();
        return new ResponseEntity<>(array, HttpStatusCode.valueOf(200));
    }

    @ResponseBody
    @PostMapping("/add")
    public ResponseEntity<Object> save(
        Map<String, Object> model,
        @RequestParam(name = "modalContentId", required = false) Long contentId,
        @RequestParam(name = "modalContentType", required = false) String contentType,
        @RequestParam("file") MultipartFile[] files,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {

        for (MultipartFile file : files) {
            if (file.getBytes().length <= 0) {
                return new ResponseEntity<>(Map.of("status", "fail"), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            if (StringUtils.isEmpty(file.getName())) {
                return new ResponseEntity<>(Map.of("status", "fail"), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            fileService.save(contentType, contentId, file);
        }

        return new ResponseEntity<>(Map.of("status", "success"), HttpStatus.OK);
    }
}