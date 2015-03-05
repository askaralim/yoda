package com.yoda.content.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoda.content.dao.ContentImageDAO;
import com.yoda.content.model.ContentImage;

@Service
public class ContentImageServiceImpl implements ContentImageService {
	@Autowired
	private ContentImageDAO contentImageDAO;

	public void deleteContentImage(ContentImage contentImage) {
		contentImageDAO.delete(contentImage);
	}

	public void deleteContentImage(Long contentImageId) {
		ContentImage contentImage = getContentImage(contentImageId);

		contentImageDAO.delete(contentImage);
	}

	public ContentImage getContentImage(Long contentImageId) {
		return contentImageDAO.getContentImageById(contentImageId);
	}

	public ContentImage getContentImage(Long siteId, Long contentImageId) {
		return contentImageDAO.getContentImageBySId_Id(siteId, contentImageId);
	}

	public ContentImage addContentImage(
			Long siteId, Long userId, String imageName, String contentType,
			byte[] imageValue, int height, int width) {
		ContentImage contentImage = new ContentImage();

		contentImage.setSiteId(siteId);
		contentImage.setImageName(imageName);
		contentImage.setContentType(contentType);
		contentImage.setImageValue(imageValue);
		contentImage.setHeight(height);
		contentImage.setWidth(width);
		contentImage.setUpdateBy(userId);
		contentImage.setUpdateDate(new Date());
		contentImage.setCreateBy(userId);
		contentImage.setCreateDate(new Date());

		contentImageDAO.save(contentImage);

		return contentImage;
	}
}