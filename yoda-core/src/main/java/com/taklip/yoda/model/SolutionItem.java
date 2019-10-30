package com.taklip.yoda.model;

import javax.validation.constraints.NotEmpty;

public class SolutionItem extends BaseEntity {
	private Long id;
	@NotEmpty(message = "required")
	private Long solutionId;
	@NotEmpty(message = "required")
	private Long itemId;
	private String description;
	private Item item;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(Long solutionId) {
		this.solutionId = solutionId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public boolean isNew() {
		return (this.id == null);
	}
}