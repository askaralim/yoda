package com.yoda.kernal.util;

import java.util.Map;
import java.util.Properties;

public class SystemEnv {

	public static void setProperties(Properties properties) {
		Map<String, String> env = System.getenv();

		for (Map.Entry<String, String> entry : env.entrySet()) {
			properties.setProperty("env." + entry.getKey(), entry.getValue());
		}
	}

}