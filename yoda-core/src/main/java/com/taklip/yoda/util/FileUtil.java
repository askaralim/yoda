package com.taklip.yoda.util;

import com.taklip.yoda.tool.Constants;

public class FileUtil {
	static public boolean isImage(String filename) {
		int index = filename.lastIndexOf('.');

		if (index == -1) {
			return false;
		}

		if (filename.length() - 1 == index) {
			return false;
		}

		String extension = filename.substring(index + 1);

		for (int i = 0; i < Constants.IMAGE_MIME.length; i++) {
			if (extension.toLowerCase().trim().equals(Constants.IMAGE_MIME[i])) {
				return true;
			}
		}

		return false;
	}
}