package com.taklip.yoda.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taklip.yoda.model.ContentUserRate;

public interface ContentUserRateMapper extends BaseMapper<ContentUserRate> {
    @Select("SELECT SUM(score) FROM content_user_rate WHERE content_id = #{contentId}")
    Integer getContentRateByContentId(@Param("contentId") Long contentId);

    ContentUserRate getContentUserRateByContentIdAndUserId(Long contentId, Long userId);

    List<ContentUserRate> getContentUserRatesByContentId(Long contentId);
}