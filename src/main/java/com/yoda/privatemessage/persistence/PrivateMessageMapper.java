package com.yoda.privatemessage.persistence;

import com.yoda.kernal.annotation.YodaMyBatisMapper;
import com.yoda.kernal.persistence.BaseMapper;
import com.yoda.privatemessage.model.PrivateMessage;

@YodaMyBatisMapper
public interface PrivateMessageMapper extends BaseMapper<PrivateMessage> {
}