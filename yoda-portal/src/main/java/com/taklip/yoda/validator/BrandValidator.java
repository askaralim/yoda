package com.taklip.yoda.validator;

import com.taklip.yoda.model.Brand;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

public class BrandValidator {
	public void validate(Brand brand, Errors errors) {
		String name = brand.getName();

		// name validation
		if (!StringUtils.hasLength(name)) {
			errors.rejectValue("name", "required", "required");
		}
	}
}
