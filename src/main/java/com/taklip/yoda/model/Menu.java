package com.taklip.yoda.model;

import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Menu extends BaseEntity {
    private long siteId;

    private String setName;

    private String title;

    private String name;

    private long seqNum;

    private long parentId;

    private String menuType;

    private String menuUrl;

    private String menuWindowTarget;

    private String menuWindowMode;

    private boolean published;

//	private Item item;

    @TableField(exist = false)
    private Content content;
}