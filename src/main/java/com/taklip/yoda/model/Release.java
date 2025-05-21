package com.taklip.yoda.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Release extends BaseEntity {
    private LocalDateTime modifiedDate;

    private int buildNumber;

    private boolean verified;
}