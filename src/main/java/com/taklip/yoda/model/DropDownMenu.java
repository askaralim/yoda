package com.taklip.yoda.model;

import java.util.Vector;

public class DropDownMenu {
    long menuKey;
    String menuName;
    DropDownMenu menuItems[] = new DropDownMenu[0];

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

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public long getMenuKey() {
        return menuKey;
    }

    public void setMenuKey(long menuKey) {
        this.menuKey = menuKey;
    }
}