package com.yoda.menu;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.yoda.util.Format;

public class MenuSaveValidator implements Validator {
	public boolean supports(Class<?> clazz) {
		return MenuEditCommand.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {

		MenuEditCommand command = (MenuEditCommand)target;

		if (Format.isNullOrEmpty(command.getMenuTitle())) {
			errors.rejectValue("menuTitle", "error.string.required", "Required");
		}

		if (Format.isNullOrEmpty(command.getMenuName())) {
			errors.rejectValue("menuName", "error.string.required", "Required");
		}
	}
}