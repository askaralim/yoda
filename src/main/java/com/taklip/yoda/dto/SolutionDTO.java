package com.taklip.yoda.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SolutionDTO {
    private Long id;
    private String title;
    private String description;
    private String imagePath;
    private Long categoryId;
    private List<SolutionItemDTO> solutionItems;
    private UserDTO createBy;
    private UserDTO updateBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}