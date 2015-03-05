package com.yoda.kernal.util;

import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.yoda.util.GetterUtil;
import com.yoda.util.StringUtil;
import com.yoda.util.Validator;

public class SystemProperties {

	public static final String SYSTEM_PROPERTIES_FINAL =
		"system.properties.final";

	public static final String SYSTEM_PROPERTIES_LOAD =
		"system.properties.load";

	public static final String TMP_DIR = "java.io.tmpdir";

	public static String get(String key) {
		String value = _instance._properties.get(key);

		if (value == null) {
			value = System.getProperty(key);
		}

		return value;
	}

	public static String[] getArray(String key) {
		String value = get(key);

		if (value == null) {
			return new String[0];
		}
		else {
			return StringUtil.split(value);
		}
	}

	public static Properties getProperties() {
		return PropertiesUtil.fromMap(_instance._properties);
	}

	public static void set(String key, String value) {
		System.setProperty(key, value);

		_instance._properties.put(key, value);
	}

	private SystemProperties() {
		Properties properties = new Properties();

		Thread currentThread = Thread.currentThread();

		ClassLoader classLoader = currentThread.getContextClassLoader();

		// system.properties

		try {
			URL url = classLoader.getResource("system.properties");

			if (url != null) {
				InputStream inputStream = url.openStream();

				properties.load(inputStream);

				inputStream.close();

				System.out.println("Loading " + url);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// system-ext.properties

//		try {
//			URL url = classLoader.getResource("system-ext.properties");
//
//			if (url != null) {
//				InputStream inputStream = url.openStream();
//
//				properties.load(inputStream);
//
//				inputStream.close();
//
//				System.out.println("Loading " + url);
//			}
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}

		// Set environment properties

		SystemEnv.setProperties(properties);

		// Set system properties

		boolean systemPropertiesLoad = GetterUtil.getBoolean(
			System.getProperty(SYSTEM_PROPERTIES_LOAD), true);

		boolean systemPropertiesFinal = GetterUtil.getBoolean(
			System.getProperty(SYSTEM_PROPERTIES_FINAL), true);

		if (systemPropertiesLoad) {
			Enumeration<String> enu =
				(Enumeration<String>)properties.propertyNames();

			while (enu.hasMoreElements()) {
				String key = enu.nextElement();

				if (systemPropertiesFinal ||
					Validator.isNull(System.getProperty(key))) {

					System.setProperty(key, properties.getProperty(key));
				}
			}
		}

		_properties = new ConcurrentHashMap<String, String>();

		// Use a fast concurrent hash map implementation instead of the slower
		// java.util.Properties

		PropertiesUtil.fromProperties(properties, _properties);
	}

	private static SystemProperties _instance = new SystemProperties();

	private Map<String, String> _properties;

}