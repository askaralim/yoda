package com.taklip.yoda.vo;

import java.util.Vector;

import com.taklip.yoda.model.DropDownMenu;

public class DropDownMenuContainer {
    DropDownMenu menuItems[] = new DropDownMenu[0];
    String menuSetName;
    String menuSetKey;

    public void addMenuItems(DropDownMenu menu) {
        Vector<DropDownMenu> vector = new Vector<DropDownMenu>();
        for (int i = 0; i < menuItems.length; i++) {
            vector.add(menuItems[i]);
        }
        vector.add(menu);
        menuItems = new DropDownMenu[vector.size()];
        vector.copyInto(menuItems);
    }

    public DropDownMenu[] getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(DropDownMenu[] menuItems) {
        this.menuItems = menuItems;
    }

    public String getMenuSetName() {
        return menuSetName;
    }

    public void setMenuSetName(String menuSetName) {
        this.menuSetName = menuSetName;
    }

    public String getMenuSetKey() {
        return menuSetKey;
    }

    public void setMenuSetKey(String menuSetKey) {
        this.menuSetKey = menuSetKey;
    }

}
