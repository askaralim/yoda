package com.taklip.yoda.model;

import java.util.List;

import lombok.Data;

@Data
public class MenuInfo {
    private String menuTitle;
    private String menuName;
    private long seqNo;
    private String menuAnchor;
    private String menuWindowTarget;
    private String menuWindowMode;
    private String menuUrl;
    private List<MenuInfo> menus;
}
