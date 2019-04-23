package com.taklip.yoda.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.taklip.yoda.model.ImageFile;

public interface FileService {
	void save(String contentType, Long contentId, MultipartFile file) throws IOException ;

	List<ImageFile> getFiles();

	List<ImageFile> getFilesByContent(String contentType, Long contentId);
}