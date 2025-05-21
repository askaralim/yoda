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
    private String featuredImage;
    private String pageTitle;
    private String naturalKey;
    private Boolean published;
    private Boolean featureData;
    private Boolean homePage;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime publishDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime expireDate;
    private Integer hitCounter;
    private Integer score;
    private UserDTO createBy;
    private UserDTO updateBy;
    private List<ContentBrandDTO> brands;
    private List<ContentContributorDTO> contributors;
    private CategoryDTO category;
    private List<ItemDTO> items;
    private List<CommentDTO> comments;

    /**
     * For list views (e.g., content listing pages)
     */
    // @Data
    // public static class Brief {
    // private Long id;
    // private String title;
    // private String shortDescription;
    // private String featuredImage;
    // private Boolean published;
    // private LocalDateTime publishDate;
    // private Integer hitCounter;
    // private Integer score;
    // private String categoryName; // Only category name for brief view
    // }

    // /**
    // * For detailed content view
    // */
    // @Data
    // public static class Detail {
    // private Long id;
    // private String title;
    // private String shortDescription;
    // private String description;
    // private String featuredImage;
    // private String pageTitle;
    // private String naturalKey;
    // private Boolean published;
    // private Boolean featureData;
    // private Boolean homePage;
    // private LocalDateTime publishDate;
    // private LocalDateTime expireDate;
    // private Integer hitCounter;
    // private Integer score;
    // private Integer siteId;

    // // Related entities
    // private CategoryDTO category;
    // private List<ContentContributorDTO> contributors;
    // private List<ContentBrandDTO> brands;
    // private List<ItemDTO> items;
    // private List<CommentDTO> comments;

    // @Data
    // public static class CategoryDTO {
    // private Long id;
    // private String name;
    // private Long parent;
    // }

    // @Data
    // public static class ContentContributorDTO {
    // private Long userId;
    // private String username;
    // private String profilePhotoSmall;
    // private String version;
    // private boolean approved;
    // }

    // @Data
    // public static class ContentBrandDTO {
    // private Long brandId;
    // private String brandName;
    // private String brandLogo;
    // private String description;
    // }
    // }

    // /**
    // * For content creation/update
    // */
    // @Data
    // public static class Create {
    // private String title;
    // private String shortDescription;
    // private String description;
    // private String pageTitle;
    // private Boolean published = true;
    // private Boolean featureData = false;
    // private Boolean homePage = true;
    // private LocalDateTime publishDate;
    // private LocalDateTime expireDate;
    // private Long categoryId;
    // private Integer siteId;
    // }

    // /**
    // * For search results
    // */
    // @Data
    // public static class Search {
    // private Long id;
    // private String title;
    // private String shortDescription;
    // private String featuredImage;
    // private Boolean published;
    // private LocalDateTime publishDate;
    // private LocalDateTime expireDate;
    // private String categoryName;
    // private Integer hitCounter;
    // private Integer score;
    // }

    // /**
    // * For home page featured content
    // */
    // @Data
    // public static class Featured {
    // private Long id;
    // private String title;
    // private String shortDescription;
    // private String featuredImage;
    // private Integer hitCounter;
    // private Integer score;
    // private LocalDateTime publishDate;
    // }

    // /**
    // * For content statistics/analytics
    // */
    // @Data
    // public static class Stats {
    // private Long id;
    // private String title;
    // private Integer hitCounter;
    // private Integer score;
    // private Integer commentCount;
    // private LocalDateTime createTime;
    // private LocalDateTime updateTime;
    // private LocalDateTime publishDate;
    // }
}
