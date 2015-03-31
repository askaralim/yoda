package com.yoda.portal;

import org.springframework.validation.Errors;

import com.yoda.content.model.Content;
import com.yoda.util.Format;

public class FrontendContentEditValidator {
	public boolean supports(Class<?> clazz) {
		return Content.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {

		Content content = (Content)target;

		if (Format.isNullOrEmpty(content.getTitle())) {
			errors.rejectValue("title", "content-title-required", "Required");
		}
	}
}