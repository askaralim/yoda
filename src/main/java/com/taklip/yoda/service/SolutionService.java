package com.taklip.yoda.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.taklip.yoda.dto.SolutionDTO;
import com.taklip.yoda.model.Solution;
import com.taklip.yoda.model.SolutionItem;

/**
 * @author askar
 */
public interface SolutionService extends IService<Solution> {
    boolean add(Solution solution);

    void delete(Long id);

    SolutionDTO getSolutionDetail(Long id);

    SolutionItem getSolutionItemsById(Long id);

    List<SolutionItem> getSolutionItems();

    List<SolutionItem> getSolutionItemsBySolutionId(Long solutionId);

    List<Solution> getSolutions();

    Page<Solution> getSolutions(Integer offset, Integer limit);

    void saveSolutionItem(SolutionItem solutionItem);

    boolean update(Solution solution);

    boolean updateSolutionImage(Long id, MultipartFile file);
}