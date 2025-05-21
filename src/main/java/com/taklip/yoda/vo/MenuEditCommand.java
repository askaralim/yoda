package com.taklip.yoda.vo;

import com.taklip.yoda.model.DropDownMenu;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class MenuEditCommand {
    DropDownMenu menuList[];
    long menuId;
    long menuParentId;
    String menuTitle;
    String menuName;
    String seqNum;
    String menuUrl;
    String menuType;
    String menuWindowTarget;
    String menuWindowMode;
    String menuPosition;
    boolean published;
    String mode;
    String createMode;
    MenuDisplayCommand childrenMenus[];
    String menuSetName;
    @NotBlank(message = "error.string.required")
    String createMenuSetName;
    long removeMenuId;
    long createMenuId;
    boolean isSequence;
    long itemId;
    String itemNum;
    String itemShortDesc;
    long contentId;
    String contentTitle;
    long sectionId;
    String sectionShortTitle;
}
