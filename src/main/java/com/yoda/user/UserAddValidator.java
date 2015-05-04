package com.yoda.user;

import org.springframework.validation.Errors;

import com.yoda.user.model.User;
import com.yoda.util.Format;

public class UserAddValidator {
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		User user = (User)target;

		if (Format.isNullOrEmpty(user.getUsername())) {
			errors.rejectValue("username", "error.string.required", "Required");
		}

		if (com.yoda.util.Validator.isNull(user.getPassword())) {
			errors.rejectValue("password", "error.string.required", "Required");
		}

		if (com.yoda.util.Validator.isNotNull(user.getPassword())) {
			if (!user.getPassword().equals(user.getVerifyPassword())) {
				errors.rejectValue("password", "error.password.nomatch");
			}
			if (!com.yoda.util.Validator.isValidPassword(user.getPassword())) {
				errors.rejectValue("password", "error.password.invalidRule");
			}
		}
	}
}
