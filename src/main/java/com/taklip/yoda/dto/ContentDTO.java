package com.taklip.yoda.dto;

import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentDTO {
    private Long id;
    private String title;
    private String shortDescription;
    private String description;
    private String pageTitle;
    private String featuredImage;
    private Boolean published;
    private Boolean featureData;
    private Long categoryId;
    private Integer siteId;
    private String naturalKey;
    private Integer hitCounter;
    private Boolean homePage;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireDate;
    private Long createBy;
    private Long updateBy;
    private UserDTO createByUser;
    private UserDTO updateByUser;
    private List<ContentBrandDTO> brands;
    private List<ContentContributorDTO> contributors;
    private CategoryDTO category;
    private List<ItemDTO> items;
    private List<CommentDTO> comments;
    private Integer score;

    // Additional fields for API responses
    // private String contentType;
    // private String status;
    // private String tags;
    // private String metaKeywords;
    // private String metaDescription;
    // private String canonicalUrl;
    // private String seoTitle;
    // private String seoDescription;
    // private String language;
    // private Integer sortOrder;
    // private Boolean allowComments;
    // private String categoryName;
    // private String siteName;
    // private List<String> tagList;
    // private String formattedPublishDate;
    // private String formattedExpireDate;
    // private Long viewCount;
    // private Double rating;
}
