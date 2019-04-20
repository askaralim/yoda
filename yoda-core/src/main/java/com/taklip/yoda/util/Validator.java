package com.taklip.yoda.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class Validator {
	private static Pattern _emailAddressPattern = Pattern.compile(
			"[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@" +
			"(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");

	private static Pattern _ipAddressPattern = Pattern.compile("\\b"
			+ "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\."
			+ "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\."
			+ "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\."
			+ "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])" + "\\b");

	private static final int _CHAR_BEGIN = 65;

	private static final int _CHAR_END = 122;

	private static final int _DIGIT_BEGIN = 48;

	private static final int _DIGIT_END = 57;

	public static boolean isIPAddress(String ipAddress) {
		Matcher matcher = _ipAddressPattern.matcher(ipAddress);

		return matcher.matches();
	}

	public static boolean isEmailAddress(String emailAddress) {
		Matcher matcher = _emailAddressPattern.matcher(emailAddress);

		return matcher.matches();
	}

	static public boolean isValidPassword(String password) {
		if (password.length() < 4 || password.length() > 12) {
			return false;
		}

		for (char c : password.toCharArray()) {
			if (!isChar(c) && !isDigit(c)) {
				return false;
			}
		}

		return true;
	}

	public static boolean isUrl(String url) {
		if (StringUtils.isNotBlank(url)) {
			try {
				new URL(url);

				return true;
			}
			catch (MalformedURLException murle) {
			}
		}

		return false;
	}

	public static boolean isDigit(char c) {
		int x = c;

		if ((x >= _DIGIT_BEGIN) && (x <= _DIGIT_END)) {
			return true;
		}

		return false;
	}

	public static boolean isChar(char c) {
		int x = c;

		if ((x >= _CHAR_BEGIN) && (x <= _CHAR_END)) {
			return true;
		}

		return false;
	}
}
