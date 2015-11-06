package com.yoda.user.persistence;

import com.yoda.kernal.annotation.YodaMyBatisMapper;
import com.yoda.kernal.persistence.BaseMapper;
import com.yoda.user.model.UserAuthority;

@YodaMyBatisMapper
public interface UserAuthorityMapper extends BaseMapper<UserAuthority> {
}