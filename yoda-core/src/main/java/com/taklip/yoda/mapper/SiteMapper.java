package com.taklip.yoda.mapper;

import java.util.List;

import com.taklip.yoda.annotation.YodaMyBatisMapperAnnotation;
import com.taklip.yoda.model.Site;

@YodaMyBatisMapperAnnotation
public interface SiteMapper extends BaseMapper<Site> {
	List<Site> getSites();

	List<Site> search(Site site);
}