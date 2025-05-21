package com.taklip.yoda.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ContactUs extends BaseEntity {
    private Long siteId;

    private String name;

    private String addressLine1;

    private String addressLine2;

    private String cityName;

    private String zipCode;

    private String email;

    private String phone;

    private String description;

    private int seqNum;

    private boolean active;
}