package com.taklip.yoda.vo;

import java.util.List;
import java.util.Map;

import com.taklip.yoda.model.Site;

import lombok.Data;

@Data
public class ControlPanelHomeCommand {
    String tabName;
    long userId;
    String userName;
    String lastLoginDatetime;
    String password;
    String verifyPassword;
    String addressLine1;
    String addressLine2;
    String cityName;
    String siteName;
    String stateName;
    String stateCode;
    String countryName;
    String countryCode;
    String zipCode;
    String phone;
    String email;
    String cmd;
    Map<String, String> serverStats;
    Map<String, String> threadStats;
    Map<String, String> jvmStats;
    long siteId;
    List<Site> sites;
}
