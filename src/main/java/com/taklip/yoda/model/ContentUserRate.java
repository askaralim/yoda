package com.taklip.yoda.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ContentUserRate extends BaseEntity {
    private Long contentId;

    private Integer score;
}