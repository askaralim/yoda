package com.taklip.yoda.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ContentContributor extends BaseEntity {
    private Long contentId;

    private Long userId;

    private String username;

    private String profilePhotoSmall;

    private String version;

    private boolean approved;
}