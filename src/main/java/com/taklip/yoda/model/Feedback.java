package com.taklip.yoda.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Feedback extends BaseEntity {
    private String description;

    private String email;

    private String phone;

    private String username;
}