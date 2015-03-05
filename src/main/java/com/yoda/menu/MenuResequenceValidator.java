package com.yoda.menu;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.yoda.util.Format;

public class MenuResequenceValidator  implements Validator {
	public boolean supports(Class<?> clazz) {
		return MenuEditCommand.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {

		MenuEditCommand command = (MenuEditCommand)target;

		MenuDisplayCommand childrenMenus[] = command.getChildrenMenus();

		for (int i = 0; i < childrenMenus.length; i++) {
			if (!Format.isInt(childrenMenus[i].getSeqNum())) {
				errors.rejectValue("seqNum_" + i, "error.int.invalid", "Invalid");
			}
		}
	}
}
