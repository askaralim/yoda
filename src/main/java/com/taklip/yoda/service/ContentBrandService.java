package com.taklip.yoda.service;

import java.util.List;

import com.taklip.yoda.model.ContentBrand;

public interface ContentBrandService {
    void create(ContentBrand contentBrand);

    void update(ContentBrand contentBrand);

    void delete(Long id);

    ContentBrand getById(long contentBrandId);

    List<ContentBrand> getContentBrands();

    List<ContentBrand> getContentBrandByBrandId(long brandId);

    List<ContentBrand> getContentBrandsByContentId(long contentId);
}
