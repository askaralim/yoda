package com.taklip.yoda.vo;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ContactUsEditCommand {
    long contactUsId;
    @NotBlank(message = "required")
    String contactUsName;
    String contactUsEmail;
    String contactUsPhone;
    String contactUsAddressLine1;
    String contactUsAddressLine2;
    String contactUsCityName;
    String contactUsZipCode;
    String contactUsDesc;
    String seqNum;
    boolean active;
}