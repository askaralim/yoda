package com.taklip.yoda.model;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class Solution extends BaseEntity {
	private Long id;
	@NotEmpty(message = "required")
	private String title;
	private String description;
	private List<SolutionItem> solutionItems;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<SolutionItem> getSolutionItems() {
		return solutionItems;
	}

	public void setSolutionItems(List<SolutionItem> solutionItems) {
		this.solutionItems = solutionItems;
	}
}