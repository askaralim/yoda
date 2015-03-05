package com.yoda.kernal.servlet;

import com.yoda.kernal.util.ClassUtil;

public class WebDirDetector {

	public static String getLibDir(ClassLoader classLoader) {
		String libDir = ClassUtil.getParentPath(
			classLoader, "com.yoda.kernal.bean.PortalBeanLocatorUtil");

		if (libDir.endsWith("/WEB-INF/classes/")) {
			libDir = libDir.substring(0, libDir.length() - 8) + "lib/";
		}
		else {
			int pos = libDir.indexOf("/WEB-INF/lib/");

			if (pos != -1) {
				libDir = libDir.substring(0, pos) + "/WEB-INF/lib/";
			}
		}

		return libDir;
	}

	public static String getRootDir(ClassLoader classLoader) {
		return getRootDir(getLibDir(classLoader));
	}

	public static String getRootDir(String libDir) {
		String rootDir = libDir;

		if (rootDir.endsWith("/WEB-INF/lib/")) {
			rootDir = rootDir.substring(0, rootDir.length() - 12);
		}

		return rootDir;
	}

}