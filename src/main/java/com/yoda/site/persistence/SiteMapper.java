package com.yoda.site.persistence;

import java.util.List;

import com.yoda.kernal.annotation.YodaMyBatisMapper;
import com.yoda.kernal.persistence.BaseMapper;
import com.yoda.site.model.Site;

@YodaMyBatisMapper
public interface SiteMapper extends BaseMapper<Site> {
	List<Site> getSites();

	List<Site> search(Site site);
}