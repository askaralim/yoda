package com.taklip.yoda.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.taklip.yoda.vo.MenuEditCommand;

public class MenuEditValidator implements Validator {
	public boolean supports(Class<?> clazz) {
		return MenuEditCommand.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {

		MenuEditCommand command = (MenuEditCommand)target;

		if (StringUtils.isBlank(command.getCreateMenuSetName())) {
			errors.rejectValue("createMenuSetName", "error.string.required", "Required");
		}
	}
}
