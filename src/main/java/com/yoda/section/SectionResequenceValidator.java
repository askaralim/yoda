package com.yoda.section;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.yoda.util.Format;

public class SectionResequenceValidator implements Validator {
	public boolean supports(Class<?> clazz) {
		return SectionEditCommand.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {
		SectionEditCommand command = (SectionEditCommand)target;

		SectionDisplayCommand childrenSections[] = command.getChildrenSections();

		for (int i = 0; i < childrenSections.length; i++) {
			if (!Format.isInt(String.valueOf(childrenSections[i].getSeqNum()))) {
				errors.rejectValue("seqNum_" + i, "error.int.invalid");
			}
		}
	}
}
