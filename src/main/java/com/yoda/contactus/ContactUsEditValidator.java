package com.yoda.contactus;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.yoda.util.Format;

public class ContactUsEditValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return ContactUsEditCommand.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {

		ContactUsEditCommand command = (ContactUsEditCommand)target;

		if (Format.isNullOrEmpty(command.getContactUsName())) {
			errors.rejectValue("contactUsName", "error.string.required", "Required");
		}
	}
}