package com.yoda.user;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.yoda.util.Format;

public class UserEditValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return UserEditCommand.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {

		UserEditCommand command = (UserEditCommand)target;

//		if (Format.isNullOrEmpty(command.getUserId())) {
//			errors.rejectValue("userId", "error.string.required", "Required");
//		}

		if (Format.isNullOrEmpty(command.getUsername())) {
			errors.rejectValue("username", "error.string.required", "Required");
		}

//		if (com.yoda.util.Validator.isNull(command.getPassword())) {
//			errors.rejectValue("password", "error.string.required", "Required");
//		}

		if (com.yoda.util.Validator.isNotNull(command.getPassword())) {
			if (!command.getPassword().equals(command.getVerifyPassword())) {
				errors.rejectValue("password", "error.password.nomatch");
			}
			if (!com.yoda.util.Validator.isValidPassword(command.getPassword())) {
				errors.rejectValue("password", "error.password.invalidRule");
			}
		}
	}
}
