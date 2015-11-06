package com.yoda.content.service;

import com.yoda.content.model.ContentImage;

public interface ContentImageService {
	void deleteContentImage(ContentImage contentImage);

	void deleteContentImage(Long contentImageId);

	ContentImage getContentImage(Long contentImageId);

	ContentImage getContentImage(int siteId, Long contentImageId);

	ContentImage addContentImage(int siteId, String imageName, String contentType, byte[] imageValue, int height, int width);
}
