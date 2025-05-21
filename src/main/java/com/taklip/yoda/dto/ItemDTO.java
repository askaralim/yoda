package com.taklip.yoda.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.taklip.yoda.model.ExtraField;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTO {
    private long id;
    private int hitCounter;
    private BigDecimal price;
    private int rating;
    private int siteId;
    private BrandDTO brand;
    private CategoryDTO category;
    private Long contentId;
    private String buyLinks;
    private String description;
    private String shortDescription;
    private String extraFields;
    private String imagePath;
    private String level;
    private String name;
    private List<ExtraField> buyLinkList;
    private List<ExtraField> extraFieldList;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    private UserDTO createBy;
    private UserDTO updateBy;
}
