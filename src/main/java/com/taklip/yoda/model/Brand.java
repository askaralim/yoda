package com.taklip.yoda.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Brand extends BaseEntity {
    private String company;

    private String country;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime foundDate;

    private int hitCounter;

    private String kind;

    private String imagePath;

    private String name;

    private int score;
}