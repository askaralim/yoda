package com.taklip.yoda.vo;

import lombok.Data;

@Data
public class ContactUsDisplayCommand {
//	boolean remove;
    long contactUsId;
    String contactUsName;
    String contactUsEmail;
    String contactUsPhone;
    String seqNum;
    String active;
}