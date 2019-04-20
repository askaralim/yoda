package com.taklip.yoda.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;

import com.taklip.yoda.model.Feedback;

public class FeedbackAddValidator {
	public boolean supports(Class<?> clazz) {
		return Feedback.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		Feedback feedback = (Feedback)target;

		if (StringUtils.isBlank(feedback.getUsername())) {
			errors.rejectValue("username", "error.string.required", "Required");
		}

		if (StringUtils.isBlank(feedback.getEmail())) {
			errors.rejectValue("email", "error.string.required", "Required");
		}
	}
}
