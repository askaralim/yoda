package com.taklip.yoda.model;

import com.baomidou.mybatisplus.annotation.TableField;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class HomePage extends BaseEntity {
    private int siteId;

    private int seqNum;

    private boolean featureData;

    @TableField(exist = false)
    private Content content;
}