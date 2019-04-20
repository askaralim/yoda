package com.taklip.yoda.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.taklip.yoda.model.User;

public class UserSettingsValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		User user = (User)target;

		if (StringUtils.isBlank(user.getUsername())) {
			errors.rejectValue("username", "username-required", "Required");
		}

		if (StringUtils.isBlank(user.getEmail())) {
			errors.rejectValue("email", "email-required", "Required");
		}

		if (StringUtils.isNotBlank(user.getPassword())) {
			if (StringUtils.isNotBlank(user.getVerifyPassword())
				&& !user.getPassword().equals(user.getVerifyPassword())) {
				errors.rejectValue("password", "password-not-match");
			}
			if (!com.taklip.yoda.util.Validator.isValidPassword(user.getPassword())) {
				errors.rejectValue("password", "invalid-password");
			}
		}
	}
}