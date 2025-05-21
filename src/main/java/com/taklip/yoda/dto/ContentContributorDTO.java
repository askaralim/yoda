package com.taklip.yoda.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContentContributorDTO {
    private Long id;
    private Long contentId;
    private Long userId;
    private String username;
    private String profilePhotoSmall;
    private String version;
    private boolean approved;
}