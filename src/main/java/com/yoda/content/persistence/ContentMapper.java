package com.yoda.content.persistence;

import java.util.List;

import com.yoda.content.model.Content;
import com.yoda.kernal.annotation.YodaMyBatisMapper;
import com.yoda.kernal.persistence.BaseMapper;

@YodaMyBatisMapper
public interface ContentMapper extends BaseMapper<Content> {
	List<Content> getContents();

	List<Content> getContentsBySiteId(Integer siteId);

	List<Content> getContentsByTitle(String title);

	List<Content> getContentsByUserId(Long userId);

	List<Content> search(int siteId, String title, Boolean published, String createBy, String updateBy, String publishDateStart, String publishDateEnd, String expireDateStart, String expireDateEnd);
}