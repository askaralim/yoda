package com.yoda.content;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.yoda.util.Format;

public class ContentEditValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return ContentEditCommand.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {

		ContentEditCommand command = (ContentEditCommand)target;

		if (Format.isNullOrEmpty(command.getTitle())) {
			errors.rejectValue("title", "content-title-required", "Required");
		}

		if (Format.isNullOrEmpty(command.getPublishDate())) {
			errors.rejectValue("publishDate", "error.string.required", "Required");
		}

		if (Format.isNullOrEmpty(command.getExpireDate())) {
			errors.rejectValue("expireDate", "error.string.required", "Required");
		}

		if (com.yoda.util.Validator.isNotNull(command.getPublishDate()) && !Format.isDate(command.getPublishDate())) {
			errors.rejectValue("publishDate", "error.date.invalid", "Required");
		}

		if (com.yoda.util.Validator.isNotNull(command.getExpireDate()) && !Format.isDate(command.getExpireDate())) {
			errors.rejectValue("expireDate", "error.date.invalid", "Required");
		}
	}
}