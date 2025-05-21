package com.taklip.yoda.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.TableField;
import com.taklip.yoda.common.util.DateUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Content extends BaseEntity {
    private boolean featureData = false;

    private boolean published = true;

    @TableField(exist = false)
    private boolean homePage = true;

    // @TableField(exist = false)
    // private Category category;

    private Long categoryId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime publishDate = LocalDateTime.now();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime expireDate = DateUtil.getHighDate();

    private Integer hitCounter = 0;

    private Integer score = 0;

    private Integer siteId;

    private String naturalKey;

    private String title;

    private String shortDescription;

    private String description;

    private String pageTitle;

    private String featuredImage;

    // @TableField(exist = false)
    // private List<ContentContributor> contentContributors = new ArrayList<>();

    // @TableField(exist = false)
    // private List<ContentBrand> contentBrands = new ArrayList<>();

    // @TableField(exist = false)
    // private List<Item> items = new ArrayList<>();

    // @TableField(exist = false)
    // private List<Comment> comments = new ArrayList<>();

    // @TableField(exist = false)
    // private List<Menu> menus = new ArrayList<>();
}