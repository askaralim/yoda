package com.taklip.yoda.validator;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import com.taklip.yoda.model.Item;

public class ItemValidator {
	public void validate(Item item, Errors errors) {
		String name = item.getName();

		if (!StringUtils.hasLength(name)) {
			errors.rejectValue("name", "required", "required");
		}
	}
}
