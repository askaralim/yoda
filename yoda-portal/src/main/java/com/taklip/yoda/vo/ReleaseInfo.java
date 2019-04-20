package com.taklip.yoda.vo;

import java.text.DateFormat;
import java.util.Date;

import com.taklip.yoda.util.DateUtil;

public class ReleaseInfo {

	public static final int RELEASE_1_0_01_BUILD_NUMBER = 1001;

	public static final Date getBuildDate() {
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);

		return DateUtil.getDate(_DATE, df);
	}

	public static final int getBuildNumber() {
		return _BUILD_NUMBER;
	}

	public static final String getCodeName() {
		return _CODE_NAME;
	}

	public static final String getName() {
		return _NAME;
	}

	public static final int getParentBuildNumber() {
		return _PARENT_BUILD_NUMBER;
	}

	public static final String getReleaseInfo() {
		if (_releaseInfo == null) {
			_releaseInfo =
				_NAME + " " + _VERSION_DISPLAY_NAME +
					" (" + _CODE_NAME + " / Build " + _BUILD + " / " + _DATE +
						")";
		}

		return _releaseInfo;
	}

	public static final String getServerInfo() {
		if (_serverInfo == null) {
			_serverInfo = _NAME + " / " + _VERSION;
		}

		return _serverInfo;
	}

	public static String getVendor() {
		return _VENDOR;
	}

	public static final String getVersion() {
		return _VERSION;
	}

	private static final String _BUILD = "1001";

	private static final int _BUILD_NUMBER = Integer.valueOf(_BUILD);

	private static final String _CODE_NAME = "Paton";

	private static final String _DATE = "December 17, 2014";

	private static final String _NAME = "Yoda Site";

	private static final int _PARENT_BUILD_NUMBER = _BUILD_NUMBER;

	private static final String _VENDOR = "Yoda, Inc.";

	private static final String _VERSION = "1.0.1";

	private static final String _VERSION_DISPLAY_NAME = "1.0.1";

	private static String _releaseInfo;
	private static String _serverInfo;

}