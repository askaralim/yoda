package com.taklip.yoda.model;

import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SolutionItem extends BaseEntity {
    private Long solutionId;
    private Long itemId;
    private String description;
    @TableField(exist = false)
    private Item item;
}