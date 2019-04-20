package com.taklip.yoda.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.taklip.yoda.model.User;

public class UserEditValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		User user = (User)target;

		if (StringUtils.isBlank(user.getUsername())) {
			errors.rejectValue("username", "error.string.required", "Required");
		}

		if (StringUtils.isNotBlank(user.getUsername())) {
			if (!user.getPassword().equals(user.getVerifyPassword())) {
				errors.rejectValue("password", "error.password.nomatch");
			}
			if (!com.taklip.yoda.util.Validator.isValidPassword(user.getPassword())) {
				errors.rejectValue("password", "error.password.invalidRule");
			}
		}
	}
}
