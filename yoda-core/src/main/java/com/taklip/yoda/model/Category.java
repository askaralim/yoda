package com.taklip.yoda.model;

import javax.validation.constraints.NotEmpty;

public class Category extends BaseEntity {
	private Long id;

	private String description;

	@NotEmpty(message = "error.string.required")
	private String name;

	private Long parent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isNew() {
		return (this.id == null);
	}
}