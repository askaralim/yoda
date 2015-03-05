package com.yoda;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.yoda.util.Format;

public class SystemStatistics {

	static String STATISTICS_SERVER_UPTIME = "statistics.server.uptime";
	static String STATISTICS_SERVER_TOTAL_MEMORY = "statistics.server.total.memory";
	static String STATISTICS_SERVER_FREE_MEMORY = "statistics.server.free.memory";
	static String STATISTICS_THREAD_COUNT = "statistics.thread.count";

	static long uptime = System.currentTimeMillis();

	public Map<String, String> getServerStats() {
		Map<String, String> serverStats = new HashMap<String, String>();

		serverStats.put(STATISTICS_SERVER_UPTIME,
			Format.getFullDatetime(new Date(uptime)));
		serverStats.put(STATISTICS_SERVER_TOTAL_MEMORY,
			Format.getLong(Runtime.getRuntime().totalMemory()) + " bytes");
		serverStats.put(STATISTICS_SERVER_FREE_MEMORY,
			Format.getLong(Runtime.getRuntime().freeMemory()) + " bytes");

		return serverStats;
	}

	public Map<String, String> getThreadStats() {
		Map<String, String> threadStats = new HashMap<String, String>();

		threadStats.put(
			STATISTICS_THREAD_COUNT, Format.getInt(Thread.activeCount()));

		Thread thread[] = new Thread[Thread.activeCount()];

		Thread.enumerate(thread);

		for (int i = 0; i < thread.length; i++) {
			threadStats.put("Thread [" + i + "]", thread[i].toString());
		}

		return threadStats;
	}
	
	public Map<String, String> getJvmStats() {
		Map<String, String> jvmStats = new HashMap<String, String>();

		Properties properties = System.getProperties();

		Enumeration enumeration = properties.propertyNames();

		int i = 0;

		while (enumeration.hasMoreElements()) {
			String name = (String)enumeration.nextElement();
			String value = (String)properties.getProperty(name);
			jvmStats.put(name, value);
		}

		return jvmStats;
	}
}