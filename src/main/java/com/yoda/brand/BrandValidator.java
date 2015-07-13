package com.yoda.brand;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import com.yoda.brand.model.Brand;

public class BrandValidator {
	public void validate(Brand brand, Errors errors) {
		String name = brand.getName();

		// name validaation
		if (!StringUtils.hasLength(name)) {
			errors.rejectValue("name", "required", "required");
		}
	}
}
