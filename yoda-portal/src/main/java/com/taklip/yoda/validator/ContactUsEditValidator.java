package com.taklip.yoda.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.taklip.yoda.vo.ContactUsEditCommand;

public class ContactUsEditValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return ContactUsEditCommand.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {

		ContactUsEditCommand command = (ContactUsEditCommand)target;

		if (StringUtils.isBlank(command.getContactUsName())) {
			errors.rejectValue("contactUsName", "error.string.required", "Required");
		}
	}
}