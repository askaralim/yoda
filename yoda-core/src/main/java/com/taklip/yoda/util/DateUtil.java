package com.taklip.yoda.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat fullDatetimeformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	static public Date HIGHDATE;
	static public Date LOWDATE;

	static {
		dateformat.setLenient(false);
		fullDatetimeformat.setLenient(false);

		try {
			LOWDATE = dateformat.parse("01-01-2000");
			HIGHDATE = dateformat.parse("2999-12-31");
		}
		catch (Exception e) {
		}
	}

	public static Date getHighDate() {
		Date highDate = new Date();
		try {
			highDate = dateformat.parse("2999-12-31");
		}
		catch (ParseException e) {
			logger.debug("DateUtil#getHighDate: " + e.getMessage());
		}

		return highDate;
	}

	static public boolean isDate(String input) {
		try {
			dateformat.parse(input);
		}
		catch (ParseException e) {
			logger.debug("DateUtil#isDate failed for input:" + input + ", detail: " + e.getMessage());
		}

		return true;
	}

	static public Date getDate(String input, DateFormat df) {
		Date date = null;

		try {
			date = df.parse(input);
		}
		catch (ParseException e) {
			logger.debug("DateUtil#getDate failed for input:" + input + ", detail: " + e.getMessage());
		}

		return date;
	}

	static public Date getDate(String input) {
		Date date = null;
		try {
			date = dateformat.parse(input);
		}
		catch (ParseException e) {
			logger.debug("DateUtil#getDate failed for input:" + input + ", detail: " + e.getMessage());
		}
		return date;
	}

	static public Date getFullDatetime(String input) {
		Date date = null;
		try {
			date = fullDatetimeformat.parse(input);
		}
		catch (ParseException e) {
			logger.debug("DateUtil#getFullDatetime failed for input:" + input + ", detail: " + e.getMessage());
		}
		return date;
	}

	static public String getDate(Date date) {
		if (date == null) {
			return null;
		}
		return dateformat.format(date);
	}

	static public String getFullDatetime(Date date) {
		return fullDatetimeformat.format(date);
	}
}