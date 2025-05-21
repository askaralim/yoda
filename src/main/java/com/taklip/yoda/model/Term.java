package com.taklip.yoda.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Term extends BaseEntity {
    private String title;
    private String description;
    private Long contentId;
    private Long categoryId;
    private Integer hitCounter;
}