package com.yoda.content.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.yoda.content.dao.ContentImageDAO;
import com.yoda.content.model.ContentImage;
import com.yoda.kernal.util.PortalUtil;

@Deprecated
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

	public ContentImage getContentImage(int siteId, Long contentImageId) {
		return contentImageDAO.getContentImageBySId_Id(siteId, contentImageId);
	}

	public ContentImage addContentImage(
			int siteId, String imageName, String contentType,
			byte[] imageValue, int height, int width) {
		ContentImage contentImage = new ContentImage();

		contentImage.setSiteId(siteId);
		contentImage.setImageName(imageName);
		contentImage.setContentType(contentType);
		contentImage.setImageValue(imageValue);
		contentImage.setHeight(height);
		contentImage.setWidth(width);
		contentImage.setUpdateBy(PortalUtil.getAuthenticatedUser());
		contentImage.setUpdateDate(new Date());
		contentImage.setCreateBy(PortalUtil.getAuthenticatedUser());
		contentImage.setCreateDate(new Date());

		contentImageDAO.save(contentImage);

		return contentImage;
	}
}