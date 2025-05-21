package com.taklip.yoda.model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class Item extends BaseEntity {
    private Long categoryId;

    private Long contentId;

    private Long brandId;

    private int hitCounter;

    private int rating;

    private int siteId;

    private String description;

    private String imagePath;

    private String level;

    private String name;

    private String buyLinks;

    private String extraFields;

    private BigDecimal price;
}