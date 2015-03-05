package com.yoda.item;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import com.yoda.item.model.Item;

public class ItemValidator {
	public void validate(Item item, Errors errors) {
		String name = item.getName();

		// name validaation
		if (!StringUtils.hasLength(name)) {
			errors.rejectValue("name", "required", "required");
		}
	}
}
