package com.taklip.yoda.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class Site extends BaseEntity {
    private String siteName;

    private String title;

    private String logoPath;

    private String logoContentType;

    private boolean active;

    private String publicPort;

    private String securePort;

    private String domainName;

    private String googleAnalyticsId;

    private boolean secureConnectionEnabled;

    private String footer;

    private String listingPageSize;

    private String sectionPageSize;

    private Integer themeId;
}