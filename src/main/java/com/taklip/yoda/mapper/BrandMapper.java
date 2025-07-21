package com.taklip.yoda.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taklip.yoda.model.Brand;

/**
 * @author askar
 */
public interface BrandMapper extends BaseMapper<Brand> {
    @Update("UPDATE brand SET hit_counter = hit_counter + 1 WHERE id = #{brandId}")
    void increaseHitCounter(@Param("brandId") long brandId);
}