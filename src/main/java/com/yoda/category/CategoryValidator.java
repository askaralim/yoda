package com.yoda.category;

import org.springframework.validation.Errors;

import com.yoda.category.model.Category;
import com.yoda.util.Validator;

public class CategoryValidator {
	public boolean supports(Class<?> clazz) {
		return Category.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		Category category = (Category)target;

		if (Validator.isNull(category.getName())) {
			errors.rejectValue("name", "error.string.required", "Required");
		}
	}
}
