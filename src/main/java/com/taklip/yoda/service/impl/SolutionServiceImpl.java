package com.taklip.yoda.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taklip.yoda.enums.ContentTypeEnum;
import com.taklip.yoda.mapper.SolutionItemMapper;
import com.taklip.yoda.mapper.SolutionMapper;
import com.taklip.yoda.model.Item;
import com.taklip.yoda.model.Solution;
import com.taklip.yoda.model.SolutionItem;
import com.taklip.yoda.service.FileService;
import com.taklip.yoda.service.ItemService;
import com.taklip.yoda.service.SolutionService;
import com.taklip.yoda.service.UserService;
import com.taklip.yoda.common.tools.ImageUploader;
import com.taklip.yoda.convertor.ModelConvertor;
import com.taklip.yoda.dto.SolutionDTO;
import com.taklip.yoda.dto.SolutionItemDTO;

import lombok.extern.slf4j.Slf4j;

/**
 * @author askar
 */
@Transactional
@Service
@Slf4j
public class SolutionServiceImpl extends ServiceImpl<SolutionMapper, Solution>
        implements SolutionService {
    @Autowired
    private SolutionItemMapper solutionItemMapper;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ImageUploader imageUpload;

    @Autowired
    private FileService fileService;

    @Autowired
    private ModelConvertor modelConvertor;

    @Autowired
    private UserService userService;

    @Override
    public SolutionDTO getSolutionDetail(Long id) {
        Solution solution = this.getById(id);

        SolutionDTO solutionDTO = modelConvertor.convertToSolutionDTO(solution);

        List<SolutionItem> solutionItems = this.getSolutionItemsBySolutionId(id);

        List<SolutionItemDTO> solutionItemDTOs = solutionItems.stream()
                .map(modelConvertor::convertToSolutionItemDTO).collect(Collectors.toList());

        solutionItemDTOs.parallelStream().forEach(solutionItemDTO -> {
            Item item = itemService.getById(solutionItemDTO.getItemId());
            solutionItemDTO.setItem(modelConvertor.convertToItemDTO(item));
        });

        solutionDTO.setSolutionItems(solutionItemDTOs);
        solutionDTO.setCreateBy(
                modelConvertor.convertToUserDTO(userService.getById(solution.getCreateBy())));
        solutionDTO.setUpdateBy(
                modelConvertor.convertToUserDTO(userService.getById(solution.getUpdateBy())));

        return solutionDTO;
    }

    @Override
    public Solution create(Solution solution) {
        this.save(solution);

        return solution;
    }

    @Override
    public Solution update(Solution solution) {
        this.updateById(solution);

        return solution;
    }

    @Override
    public Solution updateSolutionImage(Long id, MultipartFile file) {
        try {
            if (file.getBytes().length <= 0) {
                throw new RuntimeException("Image is empty");
            }

            if (StringUtils.isBlank(file.getName())) {
                throw new RuntimeException("Image name is empty");
            }
        } catch (IOException e) {
            log.error("update solution image error: {}", e.getMessage());
            throw new RuntimeException("Failed to update solution image", e);
        }

        Solution solution = getById(id);

        imageUpload.deleteImage(solution.getImagePath());

        try {
            String imagePath = fileService.save(ContentTypeEnum.SOLUTION.getType(), id, file);

            solution.setImagePath(imagePath);
        } catch (IOException e) {
            log.warn("update solution image error: {}", e.getMessage());
        }

        this.updateById(solution);

        return solution;
    }

    @Override
    public void delete(Long id) {
        this.removeById(id);
    }

    @Override
    public List<Solution> getSolutions() {
        return this.list();
    }

    @Override
    public Page<Solution> getSolutions(Integer offset, Integer limit) {
        return this.page(new Page<>(offset, limit));
    }

    @Override
    public SolutionItem getSolutionItemsById(Long id) {
        return solutionItemMapper.selectById(id);
    }

    @Override
    public List<SolutionItem> getSolutionItems() {
        return solutionItemMapper.selectList(
                new LambdaQueryWrapper<SolutionItem>().orderByDesc(SolutionItem::getId));
    }

    @Override
    public List<SolutionItem> getSolutionItemsBySolutionId(Long solutionId) {
        return solutionItemMapper.selectList(new LambdaQueryWrapper<SolutionItem>()
                .eq(SolutionItem::getSolutionId, solutionId).orderByDesc(SolutionItem::getId));
    }

    @Override
    public SolutionItem createSolutionItem(SolutionItem solutionItem) {
        solutionItemMapper.insert(solutionItem);
        return solutionItem;
    }

    @Override
    public SolutionItem updateSolutionItem(SolutionItem solutionItem) {
        solutionItemMapper.updateById(solutionItem);

        return solutionItem;
    }
}
