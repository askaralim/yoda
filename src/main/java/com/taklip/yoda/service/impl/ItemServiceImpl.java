package com.taklip.yoda.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taklip.yoda.common.contant.Constants;
import com.taklip.yoda.common.tools.ImageUploader;
import com.taklip.yoda.convertor.ModelConvertor;
import com.taklip.yoda.dto.ItemDTO;
import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.mapper.ItemMapper;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.CategoryService;
import com.taklip.yoda.service.FileService;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.service.RedisService;
import com.taklip.yoda.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {
    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ImageUploader imageUpload;

    @Autowired
    private FileService fileService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ModelConvertor modelConvertor;

    @Autowired
    private UserService userService;

    @Override
    @Transactional(readOnly = true)
    public ItemDTO getItemDetail(Long itemId) {
        Item item = this.getById(itemId);

        ItemDTO itemDTO = modelConvertor.convertToItemDTO(item);

        itemDTO.setCategory(categoryService.getCategoryDetail(item.getCategoryId()));
        itemDTO.setBrand(brandService.getBrandDetail(item.getBrandId()));
        itemDTO.setCreateBy(userService.getUserDetail(item.getCreateBy()));
        itemDTO.setUpdateBy(userService.getUserDetail(item.getUpdateBy()));

        return itemDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public Item getItem(Long itemId) {
        return this.getById(itemId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> getItems() {
        return this.list();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ItemDTO> getItems(Integer offset, Integer limit) {
        Page<Item> itemPage = this.page(new Page<>(offset, limit),
                new LambdaQueryWrapper<Item>().orderByDesc(Item::getCreateTime));
        Page<ItemDTO> dtoPage = new Page<>(itemPage.getCurrent(), itemPage.getSize(), itemPage.getTotal());

        List<ItemDTO> records = itemPage.getRecords().stream()
                .map(item -> {
                    ItemDTO dto = modelConvertor.convertToItemDTO(item);
                    dto.setBrand(brandService.getBrandDetail(item.getBrandId()));
                    dto.setCategory(categoryService.getCategoryDetail(item.getCategoryId()));
                    return dto;
                })
                .collect(Collectors.toList());

        dtoPage.setRecords(records);
        return dtoPage;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> getItemsByContentId(Long contentId) {
        return this.list(new LambdaQueryWrapper<Item>().eq(Item::getContentId, contentId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> getItemsByBrandId(Long brandId) {
        return this.list(new LambdaQueryWrapper<Item>().eq(Item::getBrandId, brandId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> getItemsByContentIdAndBrandId(Long contentId, Long brandId) {
        return this
                .list(new LambdaQueryWrapper<Item>().eq(Item::getContentId, contentId).eq(Item::getBrandId, brandId));
    }

    @Override
    public List<Item> getItemsTopViewed(int count) {
        List<String> itemIds = getItemsTopViewedListFromCache(count);
        List<Item> items = new ArrayList<>();

        if (CollectionUtils.isEmpty(itemIds)) {
            items = baseMapper.getItemsTopViewed(count);

            itemIds = new ArrayList<>();

            for (Item item : items) {
                itemIds.add(String.valueOf(item.getId()));
            }

            this.setItemsTopViewedListIntoCache(itemIds);
        } else {
            for (String itemId : itemIds) {
                Item item = this.getItem(Long.valueOf(itemId));

                items.add(item);
            }
        }

        return items;
    }

    @Override
    public List<Item> search(
            int siteId, String itemNum, String itemUpcCd,
            String itemShortDesc) {
        return null;
    }

    @Override
    public Item create(Item item) {
        if (item.getContentId() != null & item.getContentId() != 0) {
            redisService.listRightPushAll(Constants.REDIS_CONTENT_ITEM_LIST + ":" + item.getContentId(),
                    String.valueOf(item.getId()));
        }

        return this.save(item) ? item : null;
    }

    @Override
    public Item update(Item item) {
        deleteItemFromCached(item.getId());

        return this.updateById(item) ? item : null;
    }

    @Override
    public void increaseItemHitCounter(Long itemId) {
        baseMapper.increaseHitCounter(itemId);
    }

    @Override
    public void updateItemRating(Long itemId, int rating) {
        baseMapper.updateRating(itemId, rating);
        redisService.set(Constants.REDIS_ITEM_RATE + ":" + itemId, String.valueOf(rating));
    }

    @Override
    public Item updateItemImage(Long id, MultipartFile file) {
        Item item = this.getById(id);

        imageUpload.deleteImage(item.getImagePath());

        try {
            String imagePath = fileService.save(ContentTypeEnum.ITEM.getType(), id, file);

            item.setImagePath(imagePath);
        } catch (IOException e) {
            log.warn("update item image error: {}", e.getMessage());
        }

        this.updateById(item);

        setItemIntoCached(item);

        return item;
    }

    @Override
    public void remove(Long itemId) {
        this.removeById(itemId);
    }

    private List<String> getItemsTopViewedListFromCache(int count) {
        return redisService.getList(Constants.REDIS_ITEM_TOP_VIEW_LIST, 0, count - 1);
    }

    private long setItemsTopViewedListIntoCache(List<String> ids) {
        return redisService.setList(Constants.REDIS_ITEM_TOP_VIEW_LIST, ids);
    }

    private void deleteItemFromCached(Long itemId) {
        redisService.delete(Constants.REDIS_ITEM + ":" + itemId);
    }

    private Item getItemFromCached(Long itemId) {
        Item item = null;

        String key = Constants.REDIS_ITEM + ":" + itemId;

        Map<String, String> map = redisService.getMap(key);

        if (CollectionUtils.isEmpty(map)) {
            return null;
        }

        try {
            item = objectMapper.convertValue(map, Item.class);

            item.setHitCounter(getItemHitCounter(itemId));
            item.setRating(getItemRateFromCached(itemId));
        } catch (IllegalArgumentException e) {
            log.error("Failed to convert cached data to Item: {}", e.getMessage());
            return null;
        }

        return item;
    }

    private void setItemIntoCached(Item item) {
        if (Objects.isNull(item)) {
            return;
        }

        try {
            Map<String, String> map = objectMapper.convertValue(item, new TypeReference<Map<String, String>>() {
            });

            redisService.setMap(Constants.REDIS_ITEM + ":" + item.getId(), map);

            setItemHitCounterIntoCached(item.getId(), item.getHitCounter());
            setItemRateIntoCached(item.getId(), item.getRating());
        } catch (IllegalArgumentException e) {
            log.error("Failed to cache item {}: {}", item.getId(), e.getMessage());
        }
    }

    @Override
    public int getItemHitCounter(Long itemId) {
        String hit = redisService.get(Constants.REDIS_ITEM_HIT_COUNTER + ":" + itemId);

        if (StringUtils.isNoneBlank(hit) && !"nil".equalsIgnoreCase(hit)) {
            return Integer.parseInt(hit);
        }

        Item item = this.getById(itemId);

        if (Objects.isNull(item)) {
            return 0;
        }

        setItemHitCounterIntoCached(itemId, item.getHitCounter());

        return item.getHitCounter();
    }

    private int getItemRateFromCached(Long itemId) {
        String rating = redisService.get(Constants.REDIS_ITEM_RATE + ":" + itemId);

        if (StringUtils.isNoneBlank(rating) && !"nil".equalsIgnoreCase(rating)) {
            return Integer.parseInt(rating);
        }

        Item item = this.getById(itemId);

        if (Objects.isNull(item)) {
            return 0;
        }

        setItemRateIntoCached(itemId, item.getRating());

        return item.getRating();
    }

    private void setItemHitCounterIntoCached(Long itemId, int hitCounter) {
        redisService.set(Constants.REDIS_ITEM_HIT_COUNTER + ":" + itemId, String.valueOf(hitCounter));
    }

    private void setItemRateIntoCached(Long itemId, int rating) {
        redisService.set(Constants.REDIS_ITEM_RATE + ":" + itemId, String.valueOf(rating));
    }
}