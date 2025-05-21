package com.taklip.yoda.vo;

import lombok.Data;

@Data
public class MenuDisplayCommand {
    boolean remove;
    long menuId;
    String menuName;
    String seqNum;
    String menuUrlOrContent;
    String menuWindowTarget;
    String menuWindowMode;
    String published;
}
