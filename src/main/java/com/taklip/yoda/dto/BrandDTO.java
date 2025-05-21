package com.taklip.yoda.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BrandDTO {
    private long id;
    private String company;
    private String country;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime foundDate;
    private int hitCounter;
    private String imagePath;
    private List<ItemDTO> items;
    private String kind;
    private String name;
    private int score;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
