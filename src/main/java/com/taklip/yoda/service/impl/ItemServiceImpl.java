package com.taklip.yoda.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taklip.yoda.common.tools.ImageUploader;
import com.taklip.yoda.common.util.ExtraFieldUtil;
import com.taklip.yoda.convertor.ModelConvertor;
import com.taklip.yoda.dto.ItemDTO;
import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.mapper.ItemMapper;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.service.BrandService;
import com.taklip.yoda.service.CategoryService;
import com.taklip.yoda.service.FileService;
import com.taklip.yoda.service.ItemService;
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
    private ImageUploader imageUpload;

    @Autowired
    private FileService fileService;

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
        Page<ItemDTO> dtoPage =
                new Page<>(itemPage.getCurrent(), itemPage.getSize(), itemPage.getTotal());

        List<ItemDTO> records = itemPage.getRecords().stream().map(item -> {
            ItemDTO dto = modelConvertor.convertToItemDTO(item);
            dto.setBrand(brandService.getBrandDetail(item.getBrandId()));
            dto.setCategory(categoryService.getCategoryDetail(item.getCategoryId()));
            return dto;
        }).collect(Collectors.toList());

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
    public Page<ItemDTO> getItemsByBrandId(Long brandId, Integer offset, Integer limit) {
        Page<Item> itemPage = this.page(new Page<>(offset, limit),
                new LambdaQueryWrapper<Item>().eq(Item::getBrandId, brandId));
        Page<ItemDTO> dtoPage =
                new Page<>(itemPage.getCurrent(), itemPage.getSize(), itemPage.getTotal());
        List<ItemDTO> records = itemPage.getRecords().stream().map(item -> {
            return modelConvertor.convertToItemDTO(item);
        }).collect(Collectors.toList());

        dtoPage.setRecords(records);

        return dtoPage;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> getItemsByContentIdAndBrandId(Long contentId, Long brandId) {
        return this.list(new LambdaQueryWrapper<Item>().eq(Item::getContentId, contentId)
                .eq(Item::getBrandId, brandId));
    }

    @Override
    public List<ItemDTO> getItemsTopViewed(int count) {
        List<Item> items = baseMapper.getItemsTopViewed(count);
        return items.stream().map(modelConvertor::convertToItemDTO).collect(Collectors.toList());
    }

    @Override
    public List<Item> search(int siteId, String itemNum, String itemUpcCd, String itemShortDesc) {
        return null;
    }

    @Override
    public ItemDTO create(ItemDTO itemDto) {
        Item item = new Item();
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setPrice(itemDto.getPrice());
        item.setCategoryId(itemDto.getCategory().getId());
        item.setBrandId(itemDto.getBrand().getId());
        item.setContentId(itemDto.getContentId());
        item.setSiteId(itemDto.getSiteId());
        item.setHitCounter(itemDto.getHitCounter());
        item.setRating(itemDto.getRating());
        item.setImagePath(itemDto.getImagePath());
        item.setLevel(itemDto.getLevel());
        item.setBuyLinks(ExtraFieldUtil.setBuyLinks(itemDto.getBuyLinkList()));
        item.setExtraFields(ExtraFieldUtil.setExtraFields(itemDto.getExtraFieldList()));

        this.save(item);

        return modelConvertor.convertToItemDTO(item);
    }

    @Override
    public ItemDTO update(ItemDTO itemDto) {
        Item item = new Item();
        item.setId(itemDto.getId());
        item.setName(itemDto.getName());
        item.setDescription(itemDto.getDescription());
        item.setPrice(itemDto.getPrice());
        item.setCategoryId(itemDto.getCategory().getId());
        item.setBrandId(itemDto.getBrand().getId());
        item.setContentId(itemDto.getContentId());
        item.setSiteId(itemDto.getSiteId());
        item.setHitCounter(itemDto.getHitCounter());
        item.setRating(itemDto.getRating());
        item.setImagePath(itemDto.getImagePath());
        item.setLevel(itemDto.getLevel());
        item.setBuyLinks(ExtraFieldUtil.setBuyLinks(itemDto.getBuyLinkList()));
        item.setExtraFields(ExtraFieldUtil.setExtraFields(itemDto.getExtraFieldList()));

        this.updateById(item);

        return modelConvertor.convertToItemDTO(item);
    }

    @Override
    public void increaseItemHitCounter(Long itemId) {
        baseMapper.increaseHitCounter(itemId);
    }

    @Override
    public void updateItemRating(Long itemId, int rating) {
        baseMapper.updateRating(itemId, rating);
    }

    @Override
    public Item updateItemImage(Long id, MultipartFile file) {
        try {
            if (file.getBytes().length <= 0) {
                throw new RuntimeException("Image is empty");
            }

            if (StringUtils.isBlank(file.getName())) {
                throw new RuntimeException("Image name is empty");
            }
        } catch (IOException e) {
            log.error("update item image error: {}", e.getMessage());
            throw new RuntimeException("Failed to update item image", e);
        }

        Item item = this.getById(id);

        imageUpload.deleteImage(item.getImagePath());

        try {
            String imagePath = fileService.save(ContentTypeEnum.ITEM.getType(), id, file);

            item.setImagePath(imagePath);
        } catch (IOException e) {
            log.warn("update item image error: {}", e.getMessage());
        }

        this.updateById(item);

        return item;
    }

    @Override
    public void remove(Long itemId) {
        this.removeById(itemId);
    }

    @Override
    public int getItemHitCounter(Long itemId) {
        Item item = this.getById(itemId);

        if (Objects.isNull(item)) {
            return 0;
        }

        return item.getHitCounter();
    }
}
