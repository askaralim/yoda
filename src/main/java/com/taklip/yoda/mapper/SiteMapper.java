package com.taklip.yoda.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taklip.yoda.model.Site;

public interface SiteMapper extends BaseMapper<Site> {
    List<Site> getSites();

    List<Site> search(Site site);
}