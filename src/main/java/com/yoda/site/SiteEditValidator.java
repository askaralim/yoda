package com.yoda.site;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SiteEditValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return SiteEditCommand.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {

		SiteEditCommand command = (SiteEditCommand)target;
	}
}