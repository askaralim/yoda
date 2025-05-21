package com.taklip.yoda.model;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("file")
public class ImageFile extends BaseEntity {
    private String fileName;
    private String filePath;
    private String fileSmallPath;
    private String fileFullPath;
    private String fileType;
    private Integer contentType;
    private Long contentId;
    private String suffix;
    private Long size;
    private Integer width;
    private Integer height;
}