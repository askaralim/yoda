package com.taklip.yoda.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;

import com.taklip.yoda.model.Category;

public class CategoryValidator {
	public boolean supports(Class<?> clazz) {
		return Category.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		Category category = (Category)target;

		if (StringUtils.isBlank(category.getName())) {
			errors.rejectValue("name", "error.string.required", "Required");
		}
	}
}
