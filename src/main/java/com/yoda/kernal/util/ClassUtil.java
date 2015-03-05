package com.yoda.kernal.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.log4j.Logger;

import com.yoda.util.CharPool;
import com.yoda.util.ServerDetector;
import com.yoda.util.StringPool;
import com.yoda.util.StringUtil;

public class ClassUtil {

	public static String getParentPath(
			ClassLoader classLoader, String className) {

			if (_logger.isDebugEnabled()) {
				_logger.debug("Class name " + className);
			}

			if (!className.endsWith(_CLASS_EXTENSION)) {
				className += _CLASS_EXTENSION;
			}

			className = StringUtil.replace(
				className, CharPool.PERIOD, CharPool.SLASH);

//			className = StringUtil.replace(className, "/class", _CLASS_EXTENSION);

			URL url = classLoader.getResource(className);

			String path = null;

			try {
				path = url.getPath();

				URI uri = new URI(path);

				String scheme = uri.getScheme();

				if (path.contains(StringPool.EXCLAMATION) &&
					((scheme == null) || (scheme.length() <= 1))) {

					if (!path.startsWith(StringPool.SLASH)) {
						path = StringPool.SLASH + path;
					}
				}
				else {
					path = uri.getPath();

					if (path == null) {
						path = url.getFile();
					}
				}
			}
			catch (URISyntaxException urise) {
				path = url.getFile();
			}

			if (ServerDetector.isJBoss()) {
				if (path.startsWith("file:") && !path.startsWith("file:/")) {
					path = path.substring(5);

					path = "file:/".concat(path);

//					path = StringUtil.replace(path, "%5C", StringPool.SLASH);
				}
			}

			if (_logger.isDebugEnabled()) {
				_logger.debug("Path " + path);
			}

			int pos = path.indexOf(className);

			String parentPath = path.substring(0, pos);

			if (parentPath.startsWith("jar:")) {
				parentPath = parentPath.substring(4);
			}

			if (parentPath.startsWith("file:/")) {
				parentPath = parentPath.substring(6);
			}

			if (_logger.isDebugEnabled()) {
				_logger.debug("Parent path " + parentPath);
			}

			return parentPath;
		}

	private static final String _CLASS_EXTENSION = ".class";

	private static Logger _logger = Logger.getLogger(ClassUtil.class);
}