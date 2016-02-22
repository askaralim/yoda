package com.yoda.portal.account;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.yoda.user.model.User;
import com.yoda.util.Format;

public class UserSettingsValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		User user = (User)target;

		if (Format.isNullOrEmpty(user.getUsername())) {
			errors.rejectValue("username", "username-required", "Required");
		}

		if (com.yoda.util.Validator.isNull(user.getEmail())) {
			errors.rejectValue("email", "email-required", "Required");
		}

		if (com.yoda.util.Validator.isNotNull(user.getPassword())) {
			if (com.yoda.util.Validator.isNotNull(user.getVerifyPassword())
				&& !user.getPassword().equals(user.getVerifyPassword())) {
				errors.rejectValue("password", "password-not-match");
			}
			if (!com.yoda.util.Validator.isValidPassword(user.getPassword())) {
				errors.rejectValue("password", "invalid-password");
			}
		}
	}
}