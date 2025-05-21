package com.taklip.yoda.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Solution extends BaseEntity {
    private String title;
    private String description;
    private String imagePath;
    private Long categoryId;
}