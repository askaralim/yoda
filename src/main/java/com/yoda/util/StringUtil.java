package com.yoda.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StringUtil {
	public static String extractDigits(String s) {
		if (s == null) {
			return StringPool.BLANK;
		}

		StringBuilder sb = new StringBuilder();

		char[] chars = s.toCharArray();

		for (int i = 0; i < chars.length; i++) {
			if (Validator.isDigit(chars[i])) {
				sb.append(chars[i]);
			}
		}

		return sb.toString();
	}

	/**
	 * Returns the substring of <code>s</code> up to but not including the first
	 * occurrence of the delimiter.
	 *
	 * @param  s the string from which to extract a substring
	 * @param  delimiter the smaller string whose index in the larger string
	 *         marks where to end the substring
	 * @return the substring of <code>s</code> up to but not including the first
	 *         occurrence of the delimiter, <code>null</code> if the string is
	 *         <code>null</code> or the delimiter does not occur in the string
	 */
	public static String extractFirst(String s, String delimiter) {
		if (s == null) {
			return null;
		}

		int index = s.indexOf(delimiter);

		if (index < 0) {
			return null;
		}
		else {
			return s.substring(0, index);
		}
	}

	/**
	 * Replaces all occurrences of the character with the new character.
	 *
	 * @param  s the original string
	 * @param  oldSub the character to be searched for and replaced in the
	 *         original string
	 * @param  newSub the character with which to replace the
	 *         <code>oldSub</code> character
	 * @return a string representing the original string with all occurrences of
	 *         the <code>oldSub</code> character replaced with the
	 *         <code>newSub</code> character, or <code>null</code> if the
	 *         original string is <code>null</code>
	 */
	public static String replace(String s, char oldSub, char newSub) {
		if (s == null) {
			return null;
		}

		return s.replace(oldSub, newSub);
	}

	/**
	 * Replaces all occurrences of the character with the new string.
	 *
	 * @param  s the original string
	 * @param  oldSub the character to be searched for and replaced in the
	 *         original string
	 * @param  newSub the string with which to replace the <code>oldSub</code>
	 *         character
	 * @return a string representing the original string with all occurrences of
	 *         the <code>oldSub</code> character replaced with the string
	 *         <code>newSub</code>, or <code>null</code> if the original string
	 *         is <code>null</code>
	 */
//	public static String replace(String s, char oldSub, String newSub) {
//		if ((s == null) || (newSub == null)) {
//			return null;
//		}
//
//		// The number 5 is arbitrary and is used as extra padding to reduce
//		// buffer expansion
//
//		StringBundler sb = new StringBundler(s.length() + 5 * newSub.length());
//
//		char[] chars = s.toCharArray();
//
//		for (char c : chars) {
//			if (c == oldSub) {
//				sb.append(newSub);
//			}
//			else {
//				sb.append(c);
//			}
//		}
//
//		return sb.toString();
//	}

	/**
	 * Replaces all occurrences of the string with the new string.
	 *
	 * @param  s the original string
	 * @param  oldSub the string to be searched for and replaced in the original
	 *         string
	 * @param  newSub the string with which to replace the <code>oldSub</code>
	 *         string
	 * @return a string representing the original string with all occurrences of
	 *         the <code>oldSub</code> string replaced with the string
	 *         <code>newSub</code>, or <code>null</code> if the original string
	 *         is <code>null</code>
	 */
//	public static String replace(String s, String oldSub, String newSub) {
//		return replace(s, oldSub, newSub, 0);
//	}

	/**
	 * Replaces all occurrences of the string with the new string, starting from
	 * the specified index.
	 *
	 * @param  s the original string
	 * @param  oldSub the string to be searched for and replaced in the original
	 *         string
	 * @param  newSub the string with which to replace the <code>oldSub</code>
	 *         string
	 * @param  fromIndex the index of the original string from which to begin
	 *         searching
	 * @return a string representing the original string with all occurrences of
	 *         the <code>oldSub</code> string occurring after the specified
	 *         index replaced with the string <code>newSub</code>, or
	 *         <code>null</code> if the original string is <code>null</code>
	 */
//	public static String replace(
//		String s, String oldSub, String newSub, int fromIndex) {
//
//		if (s == null) {
//			return null;
//		}
//
//		if ((oldSub == null) || oldSub.equals(StringPool.BLANK)) {
//			return s;
//		}
//
//		if (newSub == null) {
//			newSub = StringPool.BLANK;
//		}
//
//		int y = s.indexOf(oldSub, fromIndex);
//
//		if (y >= 0) {
//			StringBundler sb = new StringBundler();
//
//			int length = oldSub.length();
//			int x = 0;
//
//			while (x <= y) {
//				sb.append(s.substring(x, y));
//				sb.append(newSub);
//
//				x = y + length;
//				y = s.indexOf(oldSub, x);
//			}
//
//			sb.append(s.substring(x));
//
//			return sb.toString();
//		}
//		else {
//			return s;
//		}
//	}
//
//	public static String replace(
//		String s, String begin, String end, Map<String, String> values) {
//
//		StringBundler sb = replaceToStringBundler(s, begin, end, values);
//
//		return sb.toString();
//	}

	/**
	 * Replaces all occurrences of the elements of the string array with the
	 * corresponding elements of the new string array.
	 *
	 * @param  s the original string
	 * @param  oldSubs the strings to be searched for and replaced in the
	 *         original string
	 * @param  newSubs the strings with which to replace the
	 *         <code>oldSubs</code> strings
	 * @return a string representing the original string with all occurrences of
	 *         the <code>oldSubs</code> strings replaced with the corresponding
	 *         <code>newSubs</code> strings, or <code>null</code> if the
	 *         original string, the <code>oldSubs</code> array, or the
	 *         <code>newSubs</code> is <code>null</code>
	 */
//	public static String replace(String s, String[] oldSubs, String[] newSubs) {
//		if ((s == null) || (oldSubs == null) || (newSubs == null)) {
//			return null;
//		}
//
//		if (oldSubs.length != newSubs.length) {
//			return s;
//		}
//
//		for (int i = 0; i < oldSubs.length; i++) {
//			s = replace(s, oldSubs[i], newSubs[i]);
//		}
//
//		return s;
//	}

	/**
	 * Replaces all occurrences of the elements of the string array with the
	 * corresponding elements of the new string array, optionally replacing only
	 * substrings that are surrounded by word boundaries.
	 *
	 * <p>
	 * Examples:
	 * </p>
	 *
	 * <pre>
	 * <code>
	 * replace("redorangeyellow", {"red", "orange", "yellow"}, {"RED","ORANGE", "YELLOW"}, false) returns "REDORANGEYELLOW"
	 * replace("redorangeyellow", {"red", "orange", "yellow"}, {"RED","ORANGE", "YELLOW"}, true) returns "redorangeyellow"
	 * replace("redorange yellow", {"red", "orange", "yellow"}, {"RED","ORANGE", "YELLOW"}, false) returns "REDORANGE YELLOW"
	 * replace("redorange yellow", {"red", "orange", "yellow"}, {"RED","ORANGE", "YELLOW"}, true) returns "redorange YELLOW"
	 * replace("red orange yellow", {"red", "orange", "yellow"}, {"RED","ORANGE", "YELLOW"}, false) returns "RED ORANGE YELLOW"
	 * replace("redorange.yellow", {"red", "orange", "yellow"}, {"RED","ORANGE", * "YELLOW"}, true) returns "redorange.YELLOW"
	 * </code>
	 * </pre>
	 *
	 * @param  s the original string
	 * @param  oldSubs the strings to be searched for and replaced in the
	 *         original string
	 * @param  newSubs the strings with which to replace the
	 *         <code>oldSubs</code> strings
	 * @param  exactMatch whether or not to replace only substrings of
	 *         <code>s</code> that are surrounded by word boundaries
	 * @return if <code>exactMatch</code> is <code>true</code>, a string
	 *         representing the original string with all occurrences of the
	 *         <code>oldSubs</code> strings that are surrounded by word
	 *         boundaries replaced with the corresponding <code>newSubs</code>
	 *         strings, or else a string representing the original string with
	 *         all occurrences of the <code>oldSubs</code> strings replaced with
	 *         the corresponding <code>newSubs</code> strings, or
	 *         <code>null</code> if the original string, the
	 *         <code>oldSubs</code> array, or the <code>newSubs</code is
	 *         <code>null</code>
	 */
//	public static String replace(
//		String s, String[] oldSubs, String[] newSubs, boolean exactMatch) {
//
//		if ((s == null) || (oldSubs == null) || (newSubs == null)) {
//			return null;
//		}
//
//		if (oldSubs.length != newSubs.length) {
//			return s;
//		}
//
//		if (!exactMatch) {
//			return replace(s, oldSubs, newSubs);
//		}
//
//		for (int i = 0; i < oldSubs.length; i++) {
//			s = s.replaceAll("\\b" + oldSubs[i] + "\\b", newSubs[i]);
//		}
//
//		return s;
//	}
//
//	public static StringBundler replaceToStringBundler(
//		String s, String begin, String end, Map<String, String> values) {
//
//		if ((s == null) || (begin == null) || (end == null) ||
//			(values == null) || (values.size() == 0)) {
//
//			return new StringBundler(s);
//		}
//
//		StringBundler sb = new StringBundler(values.size() * 2 + 1);
//
//		int pos = 0;
//
//		while (true) {
//			int x = s.indexOf(begin, pos);
//			int y = s.indexOf(end, x + begin.length());
//
//			if ((x == -1) || (y == -1)) {
//				sb.append(s.substring(pos));
//
//				break;
//			}
//			else {
//				sb.append(s.substring(pos, x));
//
//				String oldValue = s.substring(x + begin.length(), y);
//
//				String newValue = values.get(oldValue);
//
//				if (newValue == null) {
//					newValue = oldValue;
//				}
//
//				sb.append(newValue);
//
//				pos = y + end.length();
//			}
//		}
//
//		return sb;
//	}

	/**
	 * Splits string <code>s</code> around comma characters.
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>
	 * <code>
	 * split("Alice,Bob,Charlie") returns {"Alice", "Bob", "Charlie"}
	 * split("Alice, Bob, Charlie") returns {"Alice", " Bob", " Charlie"}
	 * </code>
	 * </pre>
	 *
	 * @param  s the string to split
	 * @return the array of strings resulting from splitting string
	 *         <code>s</code> around comma characters, or an empty string array
	 *         if <code>s</code> is <code>null</code> or <code>s</code> is empty
	 */
	public static String[] split(String s) {
		return split(s, CharPool.COMMA);
	}

	/**
	 * Splits the string <code>s</code> around comma characters returning the
	 * boolean values of the substrings.
	 *
	 * @param  x the default value to use for a substring in case an exception
	 *         occurs in getting the boolean value for that substring
	 * @return the array of boolean values resulting from splitting string
	 *         <code>s</code> around comma characters, or an empty array if
	 *         <code>s</code> is <code>null</code>
	 */
	public static boolean[] split(String s, boolean x) {
		return split(s, StringPool.COMMA, x);
	}

	/**
	 * Splits the string <code>s</code> around the specified delimiter.
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>
	 * <code>
	 * splitLines("First;Second;Third", ';') returns {"First","Second","Third"}
	 * </code>
	 * </pre>
	 *
	 * @param  s the string to split
	 * @param  delimiter the delimiter
	 * @return the array of strings resulting from splitting string
	 *         <code>s</code> around the specified delimiter character, or an
	 *         empty string array if <code>s</code> is <code>null</code> or if
	 *         <code>s</code> is empty
	 */
	public static String[] split(String s, char delimiter) {
		if (Validator.isNull(s)) {
			return _emptyStringArray;
		}

		s = s.trim();

		if (s.length() == 0) {
			return _emptyStringArray;
		}

		if ((delimiter == CharPool.RETURN) ||
			(delimiter == CharPool.NEW_LINE)) {

			return splitLines(s);
		}

		List<String> nodeValues = new ArrayList<String>();

		int offset = 0;
		int pos = s.indexOf(delimiter, offset);

		while (pos != -1) {
			nodeValues.add(s.substring(offset, pos));

			offset = pos + 1;
			pos = s.indexOf(delimiter, offset);
		}

		if (offset < s.length()) {
			nodeValues.add(s.substring(offset));
		}

		return nodeValues.toArray(new String[nodeValues.size()]);
	}

	/**
	 * Splits the string <code>s</code> around comma characters returning the
	 * double-precision decimal values of the substrings.
	 *
	 * @param  x the default value to use for a substring in case an exception
	 *         occurs in getting the double-precision decimal value for that
	 *         substring
	 * @return the array of double-precision decimal values resulting from
	 *         splitting string <code>s</code> around comma characters, or an
	 *         empty array if <code>s</code> is <code>null</code>
	 */
	public static double[] split(String s, double x) {
		return split(s, StringPool.COMMA, x);
	}

	/**
	 * Splits the string <code>s</code> around comma characters returning the
	 * decimal values of the substrings.
	 *
	 * @param  x the default value to use for a substring in case an exception
	 *         occurs in getting the decimal value for that substring
	 * @return the array of decimal values resulting from splitting string
	 *         <code>s</code> around comma characters, or an empty array if
	 *         <code>s</code> is <code>null</code>
	 */
	public static float[] split(String s, float x) {
		return split(s, StringPool.COMMA, x);
	}

	/**
	 * Splits the string <code>s</code> around comma characters returning the
	 * integer values of the substrings.
	 *
	 * @param  x the default value to use for a substring in case an exception
	 *         occurs in getting the integer value for that substring
	 * @return the array of integer values resulting from splitting string
	 *         <code>s</code> around comma characters, or an empty array if
	 *         <code>s</code> is <code>null</code>
	 */
	public static int[] split(String s, int x) {
		return split(s, StringPool.COMMA, x);
	}

	/**
	 * Splits the string <code>s</code> around comma characters returning the
	 * long integer values of the substrings.
	 *
	 * @param  x the default value to use for a substring in case an exception
	 *         occurs in getting the long integer value for that substring
	 * @return the array of long integer values resulting from splitting string
	 *         <code>s</code> around comma characters, or an empty array if
	 *         <code>s</code> is <code>null</code>
	 */
	public static long[] split(String s, long x) {
		return split(s, StringPool.COMMA, x);
	}

	/**
	 * Splits the string <code>s</code> around comma characters returning the
	 * short integer values of the substrings.
	 *
	 * @param  x the default value to use for a substring in case an exception
	 *         occurs in getting the short integer value for that substring
	 * @return the array of short integer values resulting from splitting string
	 *         <code>s</code> around comma characters, or an empty array if
	 *         <code>s</code> is <code>null</code>
	 */
	public static short[] split(String s, short x) {
		return split(s, StringPool.COMMA, x);
	}

	/**
	 * Splits the string <code>s</code> around the specified delimiter string.
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>
	 * <code>
	 * splitLines("oneandtwoandthreeandfour", "and") returns {"one","two","three","four"}
	 * </code>
	 * </pre>
	 *
	 * @param  s the string to split
	 * @param  delimiter the delimiter
	 * @return the array of strings resulting from splitting string
	 *         <code>s</code> around the specified delimiter string, or an empty
	 *         string array if <code>s</code> is <code>null</code> or equals the
	 *         delimiter
	 */
	public static String[] split(String s, String delimiter) {
		if (Validator.isNull(s) || (delimiter == null) ||
			delimiter.equals(StringPool.BLANK)) {

			return _emptyStringArray;
		}

		s = s.trim();

		if (s.equals(delimiter)) {
			return _emptyStringArray;
		}

		if (delimiter.length() == 1) {
			return split(s, delimiter.charAt(0));
		}

		List<String> nodeValues = new ArrayList<String>();

		int offset = 0;
		int pos = s.indexOf(delimiter, offset);

		while (pos != -1) {
			nodeValues.add(s.substring(offset, pos));

			offset = pos + delimiter.length();
			pos = s.indexOf(delimiter, offset);
		}

		if (offset < s.length()) {
			nodeValues.add(s.substring(offset));
		}

		return nodeValues.toArray(new String[nodeValues.size()]);
	}

	/**
	 * Splits the string <code>s</code> around the specified delimiter returning
	 * the boolean values of the substrings.
	 *
	 * @param  s the string to split
	 * @param  delimiter the delimiter
	 * @param  x the default value to use for a substring in case an exception
	 *         occurs in getting the boolean value for that substring
	 * @return the array of booleans resulting from splitting string
	 *         <code>s</code> around the specified delimiter string, or an empty
	 *         array if <code>s</code> is <code>null</code>
	 */
	public static boolean[] split(String s, String delimiter, boolean x) {
		String[] array = split(s, delimiter);
		boolean[] newArray = new boolean[array.length];

		for (int i = 0; i < array.length; i++) {
			boolean value = x;

			try {
				value = Boolean.valueOf(array[i]).booleanValue();
			}
			catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	/**
	 * Splits the string <code>s</code> around the specified delimiter returning
	 * the double-precision decimal values of the substrings.
	 *
	 * @param  s the string to split
	 * @param  delimiter the delimiter
	 * @param  x the default value to use for a substring in case an exception
	 *         occurs in getting the double-precision decimal value for that
	 *         substring
	 * @return the array of double-precision decimal values resulting from
	 *         splitting string <code>s</code> around the specified delimiter
	 *         string, or an empty array if <code>s</code> is <code>null</code>
	 */
	public static double[] split(String s, String delimiter, double x) {
		String[] array = split(s, delimiter);
		double[] newArray = new double[array.length];

		for (int i = 0; i < array.length; i++) {
			double value = x;

			try {
				value = Double.parseDouble(array[i]);
			}
			catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	/**
	 * Splits the string <code>s</code> around the specified delimiter returning
	 * the decimal values of the substrings.
	 *
	 * @param  s the string to split
	 * @param  delimiter the delimiter
	 * @param  x the default value to use for a substring in case an exception
	 *         occurs in getting the decimal value for that substring
	 * @return the array of decimal values resulting from splitting string
	 *         <code>s</code> around the specified delimiter string, or an empty
	 *         array if <code>s</code> is <code>null</code>
	 */
	public static float[] split(String s, String delimiter, float x) {
		String[] array = split(s, delimiter);
		float[] newArray = new float[array.length];

		for (int i = 0; i < array.length; i++) {
			float value = x;

			try {
				value = Float.parseFloat(array[i]);
			}
			catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	/**
	 * Splits the string <code>s</code> around the specified delimiter returning
	 * the integer values of the substrings.
	 *
	 * @param  s the string to split
	 * @param  delimiter the delimiter
	 * @param  x the default value to use for a substring in case an exception
	 *         occurs in getting the integer value for that substring
	 * @return the array of integer values resulting from splitting string
	 *         <code>s</code> around the specified delimiter string, or an empty
	 *         array if <code>s</code> is <code>null</code>
	 */
	public static int[] split(String s, String delimiter, int x) {
		String[] array = split(s, delimiter);
		int[] newArray = new int[array.length];

		for (int i = 0; i < array.length; i++) {
			int value = x;

			try {
				value = Integer.parseInt(array[i]);
			}
			catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	/**
	 * Splits the string <code>s</code> around the specified delimiter returning
	 * the long integer values of the substrings.
	 *
	 * @param  s the string to split
	 * @param  delimiter the delimiter
	 * @param  x the default value to use for a substring in case an exception
	 *         occurs in getting the long integer value for that substring
	 * @return the array of long integer values resulting from splitting string
	 *         <code>s</code> around the specified delimiter string, or an empty
	 *         array if <code>s</code> is <code>null</code>
	 */
	public static long[] split(String s, String delimiter, long x) {
		String[] array = split(s, delimiter);
		long[] newArray = new long[array.length];

		for (int i = 0; i < array.length; i++) {
			long value = x;

			try {
				value = Long.parseLong(array[i]);
			}
			catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	/**
	 * Splits the string <code>s</code> around the specified delimiter returning
	 * the short integer values of the substrings.
	 *
	 * @param  s the string to split
	 * @param  delimiter the delimiter
	 * @param  x the default value to use for a substring in case an exception
	 *         occurs in getting the short integer value for that substring
	 * @return the array of short integer values resulting from splitting string
	 *         <code>s</code> around the specified delimiter string, or an empty
	 *         array if <code>s</code> is <code>null</code>
	 */
	public static short[] split(String s, String delimiter, short x) {
		String[] array = split(s, delimiter);
		short[] newArray = new short[array.length];

		for (int i = 0; i < array.length; i++) {
			short value = x;

			try {
				value = Short.parseShort(array[i]);
			}
			catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	/**
	 * Splits string <code>s</code> around return and newline characters.
	 *
	 * <p>
	 * Example:
	 * </p>
	 *
	 * <pre>
	 * <code>
	 * splitLines("Red\rBlue\nGreen") returns {"Red","Blue","Green"}
	 * </code>
	 * </pre>
	 *
	 * @param  s the string to split
	 * @return the array of strings resulting from splitting string
	 *         <code>s</code> around return and newline characters, or an empty
	 *         string array if string <code>s</code> is <code>null</code>
	 */
	public static String[] splitLines(String s) {
		if (Validator.isNull(s)) {
			return _emptyStringArray;
		}

		s = s.trim();

		List<String> lines = new ArrayList<String>();

		int lastIndex = 0;

		while (true) {
			int returnIndex = s.indexOf(CharPool.RETURN, lastIndex);
			int newLineIndex = s.indexOf(CharPool.NEW_LINE, lastIndex);

			if ((returnIndex == -1) && (newLineIndex == -1)) {
				break;
			}

			if (returnIndex == -1) {
				lines.add(s.substring(lastIndex, newLineIndex));

				lastIndex = newLineIndex + 1;
			}
			else if (newLineIndex == -1) {
				lines.add(s.substring(lastIndex, returnIndex));

				lastIndex = returnIndex + 1;
			}
			else if (newLineIndex < returnIndex) {
				lines.add(s.substring(lastIndex, newLineIndex));

				lastIndex = newLineIndex + 1;
			}
			else {
				lines.add(s.substring(lastIndex, returnIndex));

				lastIndex = returnIndex + 1;

				if (lastIndex == newLineIndex) {
					lastIndex++;
				}
			}
		}

		if (lastIndex < s.length()) {
			lines.add(s.substring(lastIndex));
		}

		return lines.toArray(new String[lines.size()]);
	}

	private static String[] _emptyStringArray = new String[0];
}