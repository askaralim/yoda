package com.taklip.yoda.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.taklip.yoda.model.Content;

public class ContentEditValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return Content.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {

		Content content = (Content)target;

		if (StringUtils.isBlank(content.getTitle())) {
			errors.rejectValue("title", "content-title-required", "Required");
		}

		if (null == content.getPublishDate()) {
			errors.rejectValue("publishDate", "error.string.required", "Required");
		}

		if (null == content.getExpireDate()) {
			errors.rejectValue("expireDate", "error.string.required", "Required");
		}
	}
}