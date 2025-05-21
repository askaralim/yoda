package com.taklip.yoda.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PageView extends BaseEntity {
    private Long userId;

    private String username;

    private String userIpAddress;

    private Integer pageType;

    private Long pageId;

    private String pageName;

    private String pageUrl;

    private String operate;
}