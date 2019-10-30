package com.taklip.yoda.service;

import com.taklip.yoda.model.Pagination;
import com.taklip.yoda.model.Solution;
import com.taklip.yoda.model.SolutionItem;
import org.apache.ibatis.session.RowBounds;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SolutionService {
	Solution getSolution(Long id);

	Solution save(Solution solution);

	Solution updateSolutionImage(Long id, MultipartFile file);

	void delete(Long id);

	List<Solution> getSolutions();

	Pagination<Solution> getSolutions(RowBounds rowBounds);

	SolutionItem getSolutionItemsById(Long id);

	List<SolutionItem> getSolutionItems();

	List<SolutionItem> getSolutionItemsBySolutionId(Long solutionId);

	void saveSolutionItem(SolutionItem solutionItem);
}