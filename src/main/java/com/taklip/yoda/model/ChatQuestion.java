package com.taklip.yoda.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChatQuestion extends BaseEntity {
    private String openId;

    private String unionId;

    private String answer;

    private String question;

    private String persona;
}