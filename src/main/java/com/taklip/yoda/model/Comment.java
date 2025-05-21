package com.taklip.yoda.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Comment extends BaseEntity {
    private Long contentId;

    private int rating;

    private Long siteId;

    private String description;

    private Long userId;
}