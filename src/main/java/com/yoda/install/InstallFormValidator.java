package com.yoda.install;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.yoda.install.controller.Installer;
import com.yoda.util.Format;

public class InstallFormValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return InstallationCommand.class.equals(clazz);
	}

	public void validate(Object target, Errors errors) {

		InstallationCommand info = (InstallationCommand)target;

		try {
//			if (Installer.isInstallCompleted(info.getWorkingDirectory())) {
			if (Installer.isInstallCompleted()) {
//				if (Installer.isInstallCompleted(Utility.getServletLocation(this.getServlet().getServletContext()))) {
				info.setInstallCompleted(true);
				errors.reject("error.install.completed");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		if (Format.isNullOrEmpty(info.getDriver())) {
			errors.rejectValue("driver", "error.string.required", "Required");
		}

		if (Format.isNullOrEmpty(info.getDbHost())) {
			errors.rejectValue("dbHost", "error.string.required", "Required");
		}

		if (Format.isNullOrEmpty(info.getDbPort())) {
			errors.rejectValue("dbPort", "error.string.required", "Required");
		}

		if (Format.isNullOrEmpty(info.getUsername())) {
			errors.rejectValue("username", "error.string.required", "Required");
		}

		if (Format.isNullOrEmpty(info.getPassword())) {
			errors.rejectValue("password", "error.string.required", "Required");
		}

		if (Format.isNullOrEmpty(info.getDbName())) {
			errors.rejectValue("dbName", "error.string.required", "Required");
		}

		if (Format.isNullOrEmpty(info.getWorkingDirectory())) {
			errors.rejectValue("workingDirectory", "error.string.required", "Required");
		}
		else if (!Installer.isValidDirectory(info.getWorkingDirectory())) {
			errors.rejectValue("workingDirectory", "error.directory.notwritable", "Not writable");
		}

		if (Format.isNullOrEmpty(info.getLog4jDirectory())) {
			errors.rejectValue("log4jDirectory", "error.string.required", "Required");
		}
		else if (!Installer.isValidDirectory(info.getLog4jDirectory())) {
			errors.rejectValue("log4jDirectory", "error.directory.notwritable", "Not writable");
		}
	}
}
