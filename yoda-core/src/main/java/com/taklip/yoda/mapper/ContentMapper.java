package com.taklip.yoda.mapper;

import java.util.List;

import com.taklip.yoda.model.Content;

public interface ContentMapper extends BaseMapper<Content> {
	List<Content> getContents();

	List<Content> getContentsByTitle(String title);

	List<Content> getContentsByUserId(Long userId);

	List<Content> getContentsByFeatureData(boolean featureData);

	List<Content> search(int siteId, String title, Boolean published, String createBy, String updateBy, String publishDateStart, String publishDateEnd, String expireDateStart, String expireDateEnd);
}