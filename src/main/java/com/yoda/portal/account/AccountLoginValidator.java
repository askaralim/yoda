package com.yoda.portal.account;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.yoda.util.Format;

public class AccountLoginValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return AccountLoginCommand.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {

		AccountLoginCommand command = (AccountLoginCommand)target;

		if (Format.isNullOrEmpty(command.getCustEmail())) {
			errors.rejectValue("custEmail", "error.string.required");
		}

		if (Format.isNullOrEmpty(command.getCustPassword())) {
			errors.rejectValue("custPassword", "error.string.required");
		}
	}
}