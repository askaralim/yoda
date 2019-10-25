package com.taklip.yoda.mapper;

import com.taklip.yoda.model.SolutionItem;

import java.util.List;

public interface SolutionItemMapper extends BaseMapper<SolutionItem> {
	List<SolutionItem> getSolutionItems();

	List<SolutionItem> getSolutionItemsBySolutionId(Long solutionId);

	List<SolutionItem> getSolutionItemsByItemId(Long itemId);
}
