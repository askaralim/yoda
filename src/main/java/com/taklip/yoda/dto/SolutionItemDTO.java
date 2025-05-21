package com.taklip.yoda.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SolutionItemDTO {
    private Long id;
    private Long solutionId;
    private Long itemId;
    private String description;
    private ItemDTO item;
}
