package com.taklip.yoda.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Category extends BaseEntity {
    private String description;

    private String name;

    private Long parent;
}