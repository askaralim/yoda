package com.taklip.yoda.mapper;

import com.taklip.yoda.model.Solution;

import java.util.List;

public interface SolutionMapper extends BaseMapper<Solution> {
	List<Solution> getSolutions();
}