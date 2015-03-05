package com.yoda.util;

import java.io.File;

import com.yoda.util.StringPool;

public class OSDetector {

	public static String getBitmode() {
		if (_bitMode == null) {
			_bitMode = System.getProperty("sun.arch.data.model");

			if (Validator.isNull(_bitMode)) {
				_bitMode = System.getProperty("com.ibm.vm.bitmode");
			}

			if (Validator.isNull(_bitMode)) {
				String arch = System.getProperty("os.arch");

				arch = arch.toLowerCase();

				if (arch.equals("amd64") || arch.equals("x86_64")) {
					_bitMode = "64";
				}
				else if (arch.equals("i386") || arch.equals("i686") ||
						 arch.equals("x86")) {

					_bitMode = "32";
				}
			}
		}

		return _bitMode;
	}

	public static boolean isAIX() {
		if (_aix != null) {
			return _aix.booleanValue();
		}

		String osName = System.getProperty("os.name");

		osName = osName.toLowerCase();

		if (osName.equals("aix")) {
			_aix = Boolean.TRUE;
		}
		else {
			_aix = Boolean.FALSE;
		}

		return _aix.booleanValue();
	}

	public static boolean isApple() {
		if (_apple != null) {
			return _apple.booleanValue();
		}

		String osName = System.getProperty("os.name");

		osName = osName.toLowerCase();

		if (osName.contains("darwin") || osName.contains("mac")) {
			_apple = Boolean.TRUE;
		}
		else {
			_apple = Boolean.FALSE;
		}

		return _apple.booleanValue();
	}

	public static boolean isLinux() {
		if (_linux != null) {
			return _linux.booleanValue();
		}

		String osName = System.getProperty("os.name");

		osName = osName.toLowerCase();

		if (osName.contains("linux")) {
			_linux = Boolean.TRUE;
		}
		else {
			_linux = Boolean.FALSE;
		}

		return _linux.booleanValue();
	}

	public static boolean isUnix() {
		if (_unix != null) {
			return _unix.booleanValue();
		}

		if (File.pathSeparator.equals(StringPool.COLON)) {
			_unix = Boolean.TRUE;
		}
		else {
			_unix = Boolean.FALSE;
		}

		return _unix.booleanValue();
	}

	public static boolean isWindows() {
		if (_windows != null) {
			return _windows.booleanValue();
		}

		if (File.pathSeparator.equals(StringPool.SEMICOLON)) {
			_windows = Boolean.TRUE;
		}
		else {
			_windows = Boolean.FALSE;
		}

		return _windows.booleanValue();
	}

	private static Boolean _aix;
	private static Boolean _apple;
	private static String _bitMode;
	private static Boolean _linux;
	private static Boolean _unix;
	private static Boolean _windows;

}