package com.yoda.content.dao;

import org.springframework.stereotype.Repository;

import com.yoda.BaseDAO;
import com.yoda.content.model.ContentImage;

@Repository
public class ContentImageDAO extends BaseDAO<ContentImage> {
	public ContentImage getContentImageBySId_Id(long siteId, long imageId) {
		ContentImage contentImage = getById(imageId);

		if (contentImage.getSiteId() != siteId) {
			throw new SecurityException();
		}

		return contentImage;
	}

	public ContentImage getContentImageById(long imageId){
		return getById(imageId);
	}
}
