package com.taklip.yoda.service;

import com.github.pagehelper.PageInfo;
import com.taklip.yoda.model.Solution;
import com.taklip.yoda.model.SolutionItem;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author askar
 */
public interface SolutionService {
    Solution getSolution(Long id);

    Solution save(Solution solution);

    Solution updateSolutionImage(Long id, MultipartFile file);

    void delete(Long id);

    List<Solution> getSolutions();

    PageInfo<Solution> getSolutions(Integer offset, Integer limit);

    SolutionItem getSolutionItemsById(Long id);

    List<SolutionItem> getSolutionItems();

    List<SolutionItem> getSolutionItemsBySolutionId(Long solutionId);

    void saveSolutionItem(SolutionItem solutionItem);
}