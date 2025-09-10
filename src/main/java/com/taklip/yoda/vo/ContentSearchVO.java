package com.taklip.yoda.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContentSearchVO {
    private String keyword;
    private String title;
    private String description;
    private String contentType;
    private String status;
    private String tags;
    private Long categoryId;
    private Boolean published;
    private Boolean featureData;
    private String language;
    private String sortBy = "createDate";
    private String sortOrder = "desc";
    private Integer page = 1;
    private Integer size = 20;
    private LocalDateTime publishDateFrom;
    private LocalDateTime publishDateTo;
    private LocalDateTime createDateFrom;
    private LocalDateTime createDateTo;
}
