package com.yoda.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Format {
	public static String SITE_ENGINE_URL = "/content/frontend/contentAction.do";

	static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat fullDateFormat = new SimpleDateFormat(
			"EEE, d MMM yyyy");
	static SimpleDateFormat fullDatetimeformat = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss");
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

	static DecimalFormat intformat = new DecimalFormat("###,###,##0");
	static DecimalFormat doubleformat = new DecimalFormat("###,###,##0.00");
	static DecimalFormat doubleformat4 = new DecimalFormat("###,###,##0.0000");
	static DecimalFormat simpleIntformat = new DecimalFormat("########0");
	static DecimalFormat simpleDoubleformat = new DecimalFormat("########0.00");
	static DecimalFormat simpleDoubleformat4 = new DecimalFormat(
			"########0.0000");

	public static Date getHighDate() {
		Date highDate = new Date();
		try {
			highDate = dateformat.parse("2999-12-31");
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
		
		return highDate;
	}
//
//	public static void setHighDate(Date highDate) {
//		highDate = highDate;
//	}

	static public boolean isNullOrEmpty(String input) {
		if (input == null || input.trim().length() == 0) {
			return true;
		}
		return false;
	}

	static public boolean isDate(String input) {
		try {
			dateformat.parse(input);
		} catch (ParseException exception) {
			return false;
		}
		return true;
	}

	static public Date getDate(String input) throws ParseException {
		return dateformat.parse(input);
	}

	static public String getDate(Date date) {
		if (date == null) {
			return null;
		}
		return dateformat.format(date);
	}

	static public String getFullDate(Date date) {
		return fullDateFormat.format(date);
	}

	static public String getFullDatetime(Date date) {
		return fullDatetimeformat.format(date);
	}

	static public int getInt(String input) throws RuntimeException {
		try {
			return simpleIntformat.parse(input).intValue();
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	static public Integer getIntObj(String input) {
		if (isNullOrEmpty(input)) {
			return null;
		}
		return Integer.valueOf(input);
	}

	static public char getChar(String input) {
		if (input.length() == 0) {
			return ' ';
		} else {
			return input.charAt(0);
		}
	}

	static public String getInt(int input) {
		return intformat.format(input);
	}

	static public String getSimpleInt(int input) {
		return simpleIntformat.format(input);
	}

	static public String getIntObj(Integer input) {
		if (input == null) {
			return "";
		}
		return getInt(input.intValue());
	}

	static public String getSimpleIntObj(Integer input) {
		if (input == null) {
			return "";
		}
		return getSimpleInt(input.intValue());
	}

	static public boolean isInt(String input) {
		try {
			simpleIntformat.parse(input);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	static public Long getLong(String input) {
		return new Long(input);
	}

	static public String getLong(Long input) {
		return input.toString();
	}

	static public String getFloat(float input) {
		return getDouble(input);
	}

	static public String getFloat4(float input) {
		return getDouble4(input);
	}

	static public String getSimpleFloat(float input) {
		return getSimpleDouble(input);
	}

	static public String getSimpleFloat4(float input) {
		return getSimpleDouble4(input);
	}

	static public float getFloat(String input) {
		return (float) getDouble(input);
	}

	static public Float getFloatObj(String input) {
		if (isNullOrEmpty(input)) {
			return null;
		}
		return new Float((float) getDouble(input));
	}

	static public String getFloatObj(Float input) {
		if (input == null) {
			return "";
		}
		return getFloat(input.floatValue());
	}

	static public String getFloatObj4(Float input) {
		if (input == null) {
			return "";
		}
		return getFloat4(input.floatValue());
	}

	static public boolean isFloat(String input) {
		return isDouble(input);
	}

	static public String getDouble(double input) {
		return doubleformat.format(input);
	}

	static public String getDouble4(double input) {
		return doubleformat4.format(input);
	}

	static public String getSimpleDouble(double input) {
		return simpleDoubleformat.format(input);
	}

	static public String getSimpleDouble4(double input) {
		return simpleDoubleformat4.format(input);
	}

	static public double getDouble(String input) throws RuntimeException {
		try {
			return doubleformat.parse(input).doubleValue();
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	static public Double getDoubleObj(String input) {
		if (isNullOrEmpty(input)) {
			return null;
		}
		return new Double(getDouble(input));
	}

	static public String getDouble(Double input) {
		if (input == null) {
			return null;
		}
		return getDouble(input.doubleValue());
	}

	static public boolean isDouble(String input) {
		try {
			doubleformat.parse(input);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	static public String getString(String input) {
		if (input == null) {
			return "";
		}
		return input;
	}

	static public String formatCurrency(String currencyCode, Float input) {
		// TODO Currency should be formatted via locale and currenyCode.
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
		return numberFormat.format(input);
		/*
		 * java.util.Currency currency =
		 * java.util.Currency.getInstance(currencyCode); NumberFormat
		 * numberFormat = NumberFormat.getCurrencyInstance();
		 * numberFormat.setCurrency(currency); return
		 * numberFormat.format(input);
		 */
	}

	static public String formatCustomerName(String firstName,
			String middleName, String lastName) {
		String name = firstName;
		if (middleName.trim().length() > 0) {
			name += " " + middleName;
		}
		name += " " + lastName;
		return name;
	}
}
