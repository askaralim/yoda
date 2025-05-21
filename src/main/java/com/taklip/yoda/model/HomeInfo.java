package com.taklip.yoda.model;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taklip.yoda.dto.ContentDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
/**
 * @author askar
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class HomeInfo extends DataInfo {
    private List<ContentDTO> homePageDatas;
    private ContentDTO homePageFeatureData;
    private String pageTitle;
    private Page<ContentDTO> page;
}
