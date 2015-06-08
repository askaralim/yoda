package com.yoda.content;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.yoda.content.model.Content;
import com.yoda.util.Format;

public class ContentEditValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return Content.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {

		Content content = (Content)target;

		if (Format.isNullOrEmpty(content.getTitle())) {
			errors.rejectValue("title", "content-title-required", "Required");
		}

		if (com.yoda.util.Validator.isNull(content.getPublishDate())) {
			errors.rejectValue("publishDate", "error.string.required", "Required");
		}

		if (com.yoda.util.Validator.isNull(content.getExpireDate())) {
			errors.rejectValue("expireDate", "error.string.required", "Required");
		}

//		if (com.yoda.util.Validator.isNotNull(content.getPublishDate()) && !Format.isDate(content.getPublishDate())) {
//			errors.rejectValue("publishDate", "error.date.invalid", "Required");
//		}
//
//		if (com.yoda.util.Validator.isNotNull(content.getExpireDate()) && !Format.isDate(content.getExpireDate())) {
//			errors.rejectValue("expireDate", "error.date.invalid", "Required");
//		}
	}
}