package com.taklip.yoda.jediorder.timer;

import java.util.Date;

import org.springframework.beans.factory.InitializingBean;

public interface TimeService extends InitializingBean {
	Date translateTime(long time);

	long generateTime();

	long tillNextTimeUnit(long lastTimestamp);

	void validateTimestamp(long lastTimestamp, long timestamp);
}