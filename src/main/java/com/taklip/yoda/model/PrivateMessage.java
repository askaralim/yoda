package com.taklip.yoda.model;

import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PrivateMessage extends BaseEntity {
    private Integer rootId;

    @TableField(exist = false)
    private User messageFrom;

    @TableField(exist = false)
    private User messageTo;

    private String description;
}