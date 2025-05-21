package com.taklip.yoda.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ContentBrand extends BaseEntity {
    private Long contentId;

    private Long brandId;

    private String description;

    private String brandName;

    private String brandLogo;
}