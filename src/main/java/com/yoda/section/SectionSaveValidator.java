package com.yoda.section;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.yoda.util.Format;

public class SectionSaveValidator implements Validator {
	public boolean supports(Class<?> clazz) {
		return SectionEditCommand.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {

		SectionEditCommand command = (SectionEditCommand)target;

		if (Format.isNullOrEmpty(command.getSectionShortTitle())) {
			errors.rejectValue("sectionShortTitle", "error.string.required", "Required");
		}
	}
}