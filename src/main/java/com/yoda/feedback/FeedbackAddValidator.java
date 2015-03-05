package com.yoda.feedback;

import org.springframework.validation.Errors;

import com.yoda.feedback.model.Feedback;
import com.yoda.util.Validator;

public class FeedbackAddValidator {
	public boolean supports(Class<?> clazz) {
		return Feedback.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		Feedback feedback = (Feedback)target;

		if (Validator.isNull(feedback.getUsername())) {
			errors.rejectValue("username", "error.string.required", "Required");
		}

		if (Validator.isNull(feedback.getEmail())) {
			errors.rejectValue("email", "error.string.required", "Required");
		}
	}
}
