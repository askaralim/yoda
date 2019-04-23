package com.taklip.yoda.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taklip.yoda.enums.FileContentTypeEnum;
import com.taklip.yoda.mapper.ImageFileMapper;
import com.taklip.yoda.model.ImageFile;
import com.taklip.yoda.service.FileService;
import com.taklip.yoda.tool.ImageUploader;
import com.taklip.yoda.tool.StringPool;

@Service
public class FileServiceImpl implements FileService {
	@Autowired
	ImageFileMapper fileMapper;

	@Autowired
	ImageUploader imageUploader;

	@Override
	public void save(String contentType, Long contentId, MultipartFile image) throws IOException {
		ImageFile imageFile = imageUploader.uploadImage(image, contentType + StringPool.FORWARD_SLASH + contentId);

		if (null != imageFile) {
			imageFile.setFileType(image.getContentType());
			imageFile.setContentId(contentId);
			imageFile.setContentType(FileContentTypeEnum.getCode(contentType));
		}

		imageFile.preInsert();

		fileMapper.insert(imageFile);
	}

	@Override
	public List<ImageFile> getFiles() {
		return fileMapper.getFiles();
	}

	@Override
	public List<ImageFile> getFilesByContent(String contentType, Long contentId) {
		return fileMapper.getFilesByContent(FileContentTypeEnum.getCode(contentType), contentId);
	}
}