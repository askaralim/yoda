package com.taklip.yoda.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.taklip.yoda.model.Item;

public interface ItemMapper extends BaseMapper<Item> {
    // Item getItemById(long id);

    // List<Item> getItems();

    // Page<Item> getItems(Page<Item> page);

    // List<Item> getItemsByBrandId(long brandId);

    // List<Item> getItemsByContentId(long contentId);

    // List<Item> getItemsByContentIdAndBrandId(long contentId, long brandId);

    @Select("SELECT id, name, price, description, hit_counter FROM item WHERE deleted = 0 ORDER BY hit_counter DESC LIMIT #{limit}")
    List<Item> getItemsTopViewed(@Param("limit") Integer limit);

    @Update("UPDATE item SET hit_counter = hit_counter + 1 WHERE id = #{itemId}")
    void increaseHitCounter(@Param("itemId") long itemId);

    @Update("UPDATE item SET rating = #{rating} WHERE id = #{itemId}")
    void updateRating(@Param("itemId") long itemId, @Param("rating") int rating);

    List<Item> search(String title);
}