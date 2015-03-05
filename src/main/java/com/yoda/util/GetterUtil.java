package com.yoda.util;

import java.text.DateFormat;
import java.util.Date;

public class GetterUtil {

	public static final String[] BOOLEANS = {"true", "t", "y", "on", "1"};

	public static final boolean DEFAULT_BOOLEAN = false;

	public static final boolean[] DEFAULT_BOOLEAN_VALUES = new boolean[0];

	public static final byte DEFAULT_BYTE = 0;

	public static final byte[] DEFAULT_BYTE_VALUES = new byte[0];

	public static final Date[] DEFAULT_DATE_VALUES = new Date[0];

	public static final double DEFAULT_DOUBLE = 0.0;

	public static final double[] DEFAULT_DOUBLE_VALUES = new double[0];

	public static final float DEFAULT_FLOAT = 0;

	public static final float[] DEFAULT_FLOAT_VALUES = new float[0];

	public static final int DEFAULT_INTEGER = 0;

	public static final int[] DEFAULT_INTEGER_VALUES = new int[0];

	public static final long DEFAULT_LONG = 0;

	public static final long[] DEFAULT_LONG_VALUES = new long[0];

	public static final Number DEFAULT_NUMBER = 0;

	public static final Number[] DEFAULT_NUMBER_VALUES = new Number[0];

	public static final Number DEFAULT_OBJECT = null;

	public static final short DEFAULT_SHORT = 0;

	public static final short[] DEFAULT_SHORT_VALUES = new short[0];

	public static final String DEFAULT_STRING = StringPool.BLANK;

	public static boolean get(Object value, boolean defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		if (value instanceof String) {
			return get((String)value, defaultValue);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isAssignableFrom(Boolean.class)) {
			return (Boolean)value;
		}

		return defaultValue;
	}

	public static Date get(
		Object value, DateFormat dateFormat, Date defaultValue) {

		if (value == null) {
			return defaultValue;
		}

		if (value instanceof String) {
			return get((String)value, dateFormat, defaultValue);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isAssignableFrom(Date.class)) {
			return (Date)value;
		}

		return defaultValue;
	}

	public static double get(Object value, double defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		if (value instanceof String) {
			return get((String)value, defaultValue);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isAssignableFrom(Double.class)) {
			return (Double)value;
		}

		if (value instanceof Number) {
			Number number = (Number)value;

			return number.doubleValue();
		}

		return defaultValue;
	}

	public static float get(Object value, float defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		if (value instanceof String) {
			return get((String)value, defaultValue);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isAssignableFrom(Float.class)) {
			return (Float)value;
		}

		if (value instanceof Number) {
			Number number = (Number)value;

			return number.floatValue();
		}

		return defaultValue;
	}

	public static int get(Object value, int defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		if (value instanceof String) {
			return get((String)value, defaultValue);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isAssignableFrom(Integer.class)) {
			return (Integer)value;
		}

		if (value instanceof Number) {
			Number number = (Number)value;

			return number.intValue();
		}

		return defaultValue;
	}

	public static long get(Object value, long defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		if (value instanceof String) {
			return get((String)value, defaultValue);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isAssignableFrom(Long.class)) {
			return (Long)value;
		}

		if (value instanceof Number) {
			Number number = (Number)value;

			return number.longValue();
		}

		return defaultValue;
	}

	public static Number get(Object value, Number defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		if (value instanceof String) {
			if (Validator.isNull(value)) {
				return defaultValue;
			}

			if (getFloat(value) == getInteger(value)) {
				return getInteger(value);
			}
			else {
				return getFloat(value);
			}
		}

		Class<?> clazz = value.getClass();

		if (clazz.isAssignableFrom(Byte.class)) {
			return (Byte)value;
		}
		else if (clazz.isAssignableFrom(Double.class)) {
			return (Double)value;
		}
		else if (clazz.isAssignableFrom(Float.class)) {
			return (Float)value;
		}
		else if (clazz.isAssignableFrom(Integer.class)) {
			return (Integer)value;
		}
		else if (clazz.isAssignableFrom(Long.class)) {
			return (Long)value;
		}
		else if (clazz.isAssignableFrom(Short.class)) {
			return (Short)value;
		}

		if (value instanceof Number) {
			return (Number)value;
		}

		return defaultValue;
	}

	public static short get(Object value, short defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		if (value instanceof String) {
			return get((String)value, defaultValue);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isAssignableFrom(Short.class)) {
			return (Short)value;
		}

		if (value instanceof Number) {
			Number number = (Number)value;

			return number.shortValue();
		}

		return defaultValue;
	}

	public static String get(Object value, String defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		if (value instanceof String) {
			return get((String)value, defaultValue);
		}

		return defaultValue;
	}

	public static boolean get(String value, boolean defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		try {
			value = value.trim().toLowerCase();

			if (value.equals(BOOLEANS[0]) || value.equals(BOOLEANS[1]) ||
				value.equals(BOOLEANS[2]) || value.equals(BOOLEANS[3]) ||
				value.equals(BOOLEANS[4])) {

				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
		}

		return defaultValue;
	}

	public static Date get(
		String value, DateFormat dateFormat, Date defaultValue) {

		if (value == null) {
			return defaultValue;
		}

		try {
			Date date = dateFormat.parse(value.trim());

			if (date != null) {
				return date;
			}
		}
		catch (Exception e) {
		}

		return defaultValue;
	}

	public static double get(String value, double defaultValue) {
		if (value != null) {
			try {
				return Double.parseDouble(_trim(value));
			}
			catch (Exception e) {
			}
		}

		return defaultValue;
	}

	public static float get(String value, float defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		try {
			return Float.parseFloat(_trim(value));
		}
		catch (Exception e) {
		}

		return defaultValue;
	}

	public static int get(String value, int defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		return _parseInt(_trim(value), defaultValue);
	}

	public static long get(String value, long defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		return _parseLong(_trim(value), defaultValue);
	}

	public static short get(String value, short defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		return _parseShort(_trim(value), defaultValue);
	}

//	public static String get(String value, String defaultValue) {
//		if (value == null) {
//			return defaultValue;
//		}
//
//		value = value.trim();
//
//		if (value.indexOf(CharPool.RETURN) != -1) {
//			value = StringUtil.replace(
//				value, StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE);
//		}
//
//		return value;
//	}

	public static boolean getBoolean(Object value) {
		return getBoolean(value, DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(Object value, boolean defaultValue) {
		return get(value, defaultValue);
	}

	public static boolean getBoolean(String value) {
		return getBoolean(value, DEFAULT_BOOLEAN);
	}

	public static boolean getBoolean(String value, boolean defaultValue) {
		return get(value, defaultValue);
	}

	public static boolean[] getBooleanValues(Object value) {
		return getBooleanValues(value, DEFAULT_BOOLEAN_VALUES);
	}

	public static boolean[] getBooleanValues(
		Object value, boolean[] defaultValue) {

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			Class<?> componentType = clazz.getComponentType();

			if (componentType.isAssignableFrom(String.class)) {
				return getBooleanValues((String[])value, defaultValue);
			}
			else if (componentType.isAssignableFrom(Boolean.class)) {
				return (boolean[])value;
			}
		}

		return defaultValue;
	}

	public static boolean[] getBooleanValues(String[] values) {
		return getBooleanValues(values, DEFAULT_BOOLEAN_VALUES);
	}

	public static boolean[] getBooleanValues(
		String[] values, boolean[] defaultValue) {

		if (values == null) {
			return defaultValue;
		}

		boolean[] booleanValues = new boolean[values.length];

		for (int i = 0; i < values.length; i++) {
			booleanValues[i] = getBoolean(values[i]);
		}

		return booleanValues;
	}

	public static Date getDate(Object value, DateFormat dateFormat) {
		return getDate(value, dateFormat, new Date());
	}

	public static Date getDate(
		Object value, DateFormat dateFormat, Date defaultValue) {

		return get(value, dateFormat, defaultValue);
	}

	public static Date getDate(String value, DateFormat dateFormat) {
		return getDate(value, dateFormat, new Date());
	}

	public static Date getDate(
		String value, DateFormat dateFormat, Date defaultValue) {

		return get(value, dateFormat, defaultValue);
	}

	public static Date[] getDateValues(Object value, DateFormat dateFormat) {
		return getDateValues(value, dateFormat, DEFAULT_DATE_VALUES);
	}

	public static Date[] getDateValues(
		Object value, DateFormat dateFormat, Date[] defaultValue) {

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			Class<?> componentType = clazz.getComponentType();

			if (componentType.isAssignableFrom(String.class)) {
				return getDateValues((String[])value, dateFormat, defaultValue);
			}
			else if (componentType.isAssignableFrom(Date.class)) {
				return (Date[])value;
			}
		}

		return defaultValue;
	}

	public static Date[] getDateValues(String[] values, DateFormat dateFormat) {
		return getDateValues(values, dateFormat, DEFAULT_DATE_VALUES);
	}

	public static Date[] getDateValues(
		String[] values, DateFormat dateFormat, Date[] defaultValue) {

		if (values == null) {
			return defaultValue;
		}

		Date[] dateValues = new Date[values.length];

		for (int i = 0; i < values.length; i++) {
			dateValues[i] = getDate(values[i], dateFormat);
		}

		return dateValues;
	}

	public static double getDouble(Object value) {
		return getDouble(value, DEFAULT_DOUBLE);
	}

	public static double getDouble(Object value, double defaultValue) {
		return get(value, defaultValue);
	}

	public static double getDouble(String value) {
		return getDouble(value, DEFAULT_DOUBLE);
	}

	public static double getDouble(String value, double defaultValue) {
		return get(value, defaultValue);
	}

	public static double[] getDoubleValues(Object value) {
		return getDoubleValues(value, DEFAULT_DOUBLE_VALUES);
	}

	public static double[] getDoubleValues(
		Object value, double[] defaultValue) {

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			Class<?> componentType = clazz.getComponentType();

			if (componentType.isAssignableFrom(String.class)) {
				return getDoubleValues((String[])value, defaultValue);
			}
			else if (componentType.isAssignableFrom(Double.class)) {
				return (double[])value;
			}
		}

		return defaultValue;
	}

	public static double[] getDoubleValues(String[] values) {
		return getDoubleValues(values, DEFAULT_DOUBLE_VALUES);
	}

	public static double[] getDoubleValues(
		String[] values, double[] defaultValue) {

		if (values == null) {
			return defaultValue;
		}

		double[] doubleValues = new double[values.length];

		for (int i = 0; i < values.length; i++) {
			doubleValues[i] = getDouble(values[i]);
		}

		return doubleValues;
	}

	public static float getFloat(Object value) {
		return getFloat(value, DEFAULT_FLOAT);
	}

	public static float getFloat(Object value, float defaultValue) {
		return get(value, defaultValue);
	}

	public static float getFloat(String value) {
		return getFloat(value, DEFAULT_FLOAT);
	}

	public static float getFloat(String value, float defaultValue) {
		return get(value, defaultValue);
	}

	public static float[] getFloatValues(Object value) {
		return getFloatValues(value, DEFAULT_FLOAT_VALUES);
	}

	public static float[] getFloatValues(Object value, float[] defaultValue) {
		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			Class<?> componentType = clazz.getComponentType();

			if (componentType.isAssignableFrom(String.class)) {
				return getFloatValues((String[])value, defaultValue);
			}
			else if (componentType.isAssignableFrom(Float.class)) {
				return (float[])value;
			}
		}

		return defaultValue;
	}

	public static float[] getFloatValues(String[] values) {
		return getFloatValues(values, DEFAULT_FLOAT_VALUES);
	}

	public static float[] getFloatValues(
		String[] values, float[] defaultValue) {

		if (values == null) {
			return defaultValue;
		}

		float[] floatValues = new float[values.length];

		for (int i = 0; i < values.length; i++) {
			floatValues[i] = getFloat(values[i]);
		}

		return floatValues;
	}

	public static int getInteger(Object value) {
		return getInteger(value, DEFAULT_INTEGER);
	}

	public static int getInteger(Object value, int defaultValue) {
		return get(value, defaultValue);
	}

	public static int getInteger(String value) {
		return getInteger(value, DEFAULT_INTEGER);
	}

	public static int getInteger(String value, int defaultValue) {
		return get(value, defaultValue);
	}

	public static int[] getIntegerValues(Object value) {
		return getIntegerValues(value, DEFAULT_INTEGER_VALUES);
	}

	public static int[] getIntegerValues(Object value, int[] defaultValue) {
		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			Class<?> componentType = clazz.getComponentType();

			if (componentType.isAssignableFrom(String.class)) {
				return getIntegerValues((String[])value, defaultValue);
			}
			else if (componentType.isAssignableFrom(Integer.class)) {
				return (int[])value;
			}
		}

		return defaultValue;
	}

	public static int[] getIntegerValues(String[] values) {
		return getIntegerValues(values, DEFAULT_INTEGER_VALUES);
	}

	public static int[] getIntegerValues(String[] values, int[] defaultValue) {
		if (values == null) {
			return defaultValue;
		}

		int[] intValues = new int[values.length];

		for (int i = 0; i < values.length; i++) {
			intValues[i] = getInteger(values[i]);
		}

		return intValues;
	}

	public static long getLong(Object value) {
		return getLong(value, DEFAULT_LONG);
	}

	public static long getLong(Object value, long defaultValue) {
		return get(value, defaultValue);
	}

	public static long getLong(String value) {
		return getLong(value, DEFAULT_LONG);
	}

	public static long getLong(String value, long defaultValue) {
		return get(value, defaultValue);
	}

	public static long[] getLongValues(Object value) {
		return getLongValues(value, DEFAULT_LONG_VALUES);
	}

	public static long[] getLongValues(Object value, long[] defaultValue) {
		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			Class<?> componentType = clazz.getComponentType();

			if (componentType.isAssignableFrom(String.class)) {
				return getLongValues((String[])value, defaultValue);
			}
			else if (componentType.isAssignableFrom(Long.class)) {
				return (long[])value;
			}
			else if (Number.class.isAssignableFrom(componentType)) {
				Number[] numbers = (Number[])value;

				long[] values = new long[numbers.length];

				for (int i = 0; i < values.length; i++) {
					values[i] = numbers[i].longValue();
				}

				return values;
			}
		}

		return defaultValue;
	}

	public static long[] getLongValues(String[] values) {
		return getLongValues(values, DEFAULT_LONG_VALUES);
	}

	public static long[] getLongValues(String[] values, long[] defaultValue) {
		if (values == null) {
			return defaultValue;
		}

		long[] longValues = new long[values.length];

		for (int i = 0; i < values.length; i++) {
			longValues[i] = getLong(values[i]);
		}

		return longValues;
	}

	public static Number getNumber(Object value) {
		return getNumber(value, DEFAULT_NUMBER);
	}

	public static Number getNumber(Object value, Number defaultValue) {
		return get(value, defaultValue);
	}

	public static Number getNumber(String value) {
		return getNumber(value, DEFAULT_NUMBER);
	}

	public static Number getNumber(String value, Number defaultValue) {
		return get(value, defaultValue);
	}

	public static Number[] getNumberValues(Object value) {
		return getNumberValues(value, DEFAULT_NUMBER_VALUES);
	}

	public static Number[] getNumberValues(
		Object value, Number[] defaultValue) {

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			Class<?> componentType = clazz.getComponentType();

			if (componentType.isAssignableFrom(String.class)) {
				return getNumberValues((String[])value, defaultValue);
			}
			else if (componentType.isAssignableFrom(Number.class)) {
				return (Number[])value;
			}
		}

		return defaultValue;
	}

	public static Number[] getNumberValues(String[] values) {
		return getNumberValues(values, DEFAULT_NUMBER_VALUES);
	}

	public static Number[] getNumberValues(
		String[] values, Number[] defaultValue) {

		if (values == null) {
			return defaultValue;
		}

		Number[] numberValues = new Number[values.length];

		for (int i = 0; i < values.length; i++) {
			numberValues[i] = getNumber(values[i]);
		}

		return numberValues;
	}

	public static Object getObject(Object value) {
		return getObject(value, DEFAULT_OBJECT);
	}

	public static Object getObject(Object value, Object defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		return value;
	}

	public static short getShort(Object value) {
		return getShort(value, DEFAULT_SHORT);
	}

	public static short getShort(Object value, short defaultValue) {
		return get(value, defaultValue);
	}

	public static short getShort(String value) {
		return getShort(value, DEFAULT_SHORT);
	}

	public static short getShort(String value, short defaultValue) {
		return get(value, defaultValue);
	}

	public static short[] getShortValues(Object value) {
		return getShortValues(value, DEFAULT_SHORT_VALUES);
	}

	public static short[] getShortValues(Object value, short[] defaultValue) {
		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			Class<?> componentType = clazz.getComponentType();

			if (componentType.isAssignableFrom(String.class)) {
				return getShortValues((String[])value, defaultValue);
			}
			else if (componentType.isAssignableFrom(Short.class)) {
				return (short[])value;
			}
		}

		return defaultValue;
	}

	public static short[] getShortValues(String[] values) {
		return getShortValues(values, DEFAULT_SHORT_VALUES);
	}

	public static short[] getShortValues(
		String[] values, short[] defaultValue) {

		if (values == null) {
			return defaultValue;
		}

		short[] shortValues = new short[values.length];

		for (int i = 0; i < values.length; i++) {
			shortValues[i] = getShort(values[i]);
		}

		return shortValues;
	}

	public static String getString(Object value) {
		return getString(value, DEFAULT_STRING);
	}

	public static String getString(Object value, String defaultValue) {
		return get(value, defaultValue);
	}

	public static String getString(String value) {
		return getString(value, DEFAULT_STRING);
	}

	public static String getString(String value, String defaultValue) {
		return get(value, defaultValue);
	}

	private static int _parseInt(String value, int defaultValue) {
		int length = value.length();

		if (length <= 0) {
			return defaultValue;
		}

		int pos = 0;
		int limit = -Integer.MAX_VALUE;
		boolean negative = false;

		char c = value.charAt(0);

		if (c < CharPool.NUMBER_0) {
			if (c == CharPool.MINUS) {
				limit = Integer.MIN_VALUE;
				negative = true;
			}
			else if (c != CharPool.PLUS) {
				return defaultValue;
			}

			if (length == 1) {
				return defaultValue;
			}

			pos++;
		}

		int smallLimit = limit / 10;

		int result = 0;

		while (pos < length) {
			if (result < smallLimit) {
				return defaultValue;
			}

			c = value.charAt(pos++);

			if ((c < CharPool.NUMBER_0) || (c > CharPool.NUMBER_9)) {
				return defaultValue;
			}

			int number = c - CharPool.NUMBER_0;

			result *= 10;

			if (result < (limit + number)) {
				return defaultValue;
			}

			result -= number;
		}

		if (negative) {
			return result;
		}
		else {
			return -result;
		}
	}

	private static long _parseLong(String value, long defaultValue) {
		if (_useJDKParseLong == null) {
			if (OSDetector.isAIX() && ServerDetector.isWebSphere() &&
				JavaDetector.isIBM() && JavaDetector.is64bit()) {

				_useJDKParseLong = Boolean.TRUE;
			}
			else {
				_useJDKParseLong = Boolean.FALSE;
			}
		}

		if (_useJDKParseLong) {
			try {
				return Long.parseLong(value);
			}
			catch (NumberFormatException nfe) {
				return defaultValue;
			}
		}

		int length = value.length();

		if (length <= 0) {
			return defaultValue;
		}

		int pos = 0;
		long limit = -Long.MAX_VALUE;
		boolean negative = false;

		char c = value.charAt(0);

		if (c < CharPool.NUMBER_0) {
			if (c == CharPool.MINUS) {
				limit = Long.MIN_VALUE;
				negative = true;
			}
			else if (c != CharPool.PLUS) {
				return defaultValue;
			}

			if (length == 1) {
				return defaultValue;
			}

			pos++;
		}

		long smallLimit = limit / 10;

		long result = 0;

		while (pos < length) {
			if (result < smallLimit) {
				return defaultValue;
			}

			c = value.charAt(pos++);

			if ((c < CharPool.NUMBER_0) || (c > CharPool.NUMBER_9)) {
				return defaultValue;
			}

			int number = c - CharPool.NUMBER_0;

			result *= 10;

			if (result < (limit + number)) {
				return defaultValue;
			}

			result -= number;
		}

		if (negative) {
			return result;
		}
		else {
			return -result;
		}
	}

	private static short _parseShort(String value, short defaultValue) {
		int i = _parseInt(value, defaultValue);

		if ((i < Short.MIN_VALUE) || (i > Short.MAX_VALUE)) {
			return defaultValue;
		}

		return (short)i;
	}

	private static String _trim(String value) {
		value = value.trim();

		int length = value.length();

		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			char c = value.charAt(i);

			if (Character.isDigit(c) ||
				((c == CharPool.DASH) &&
				 ((i == 0) || (value.charAt(i - 1) == CharPool.UPPER_CASE_E) ||
				  (value.charAt(i - 1) == CharPool.LOWER_CASE_E))) ||
				(c == CharPool.PERIOD) || (c == CharPool.UPPER_CASE_E) ||
				(c == CharPool.LOWER_CASE_E)) {

				sb.append(c);
			}
		}

		return sb.toString();
	}

	private static Boolean _useJDKParseLong;

}