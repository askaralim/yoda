package com.taklip.yoda.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
public class RedisService {
	private static Logger logger = LoggerFactory.getLogger(RedisService.class);

	private static final int DEFAULT_TIMEOUT_SECONDS = 3600;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Resource(name="redisTemplate")
	private ValueOperations<String, String> valueOps;

	@Resource(name="redisTemplate")
	private ListOperations<String, String> listOps;

	@Resource(name="redisTemplate")
	private SetOperations<String, String> setOps;

	@Resource(name="redisTemplate")
	private HashOperations<String, String, String> hashOps;

	public String get(String key) {
		if (null == key) {
			return StringUtils.EMPTY;
		}

		return valueOps.get(key);
	}

	public void set(String key, String value) {
		this.set(key, value, DEFAULT_TIMEOUT_SECONDS);
	}

	public void set(String key, String value, long timeout) {
		if (null == key) {
			return ;
		}

		if (timeout <= 0) timeout = DEFAULT_TIMEOUT_SECONDS;

		valueOps.set(key, value, timeout, TimeUnit.SECONDS);
	}

	public long incr(String key) {
		if (null == key) {
			return 0;
		}

		return valueOps.increment(key, 1);
	}

	public long decr(String key) {
		if (null == key) {
			return 0;
		}

		return valueOps.increment(key, -1);
	}

	public List<String> getList(String key) {
		if (null == key) {
			return null;
		}

		return getList(key, 0, -1);
	}

	public List<String> getList(String key, int start, int end) {
		if (null == key) {
			return null;
		}

		List<String> value = listOps.range(key, start, end);

		if (logger.isDebugEnabled()) {
			logger.debug("getList {} = {}", key, value);
		}

		return value;
	}

	public long setList(String key, List<String> values) {
		return this.setList(key, values, DEFAULT_TIMEOUT_SECONDS);
	}

	public long setList(String key, List<String> values, int timeout) {
		if (null == key || null == values || values.isEmpty()) {
			return 0;
		}

		long result = listOps.rightPushAll(key, values);

		if (timeout != 0) {
			listOps.getOperations().expire(key, timeout, TimeUnit.SECONDS);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setList {} = {}", key, values.toArray(new String[0]));
		}

		return result;
	}

	public long listRightPushAll(String key, String... values) {
		if (null == key) {
			return 0;
		}

		long result = listOps.rightPushAll(key, values);

		if (logger.isDebugEnabled()) {
			logger.debug("listAdd {} = {}", key, values);
		}

		return result;
	}

	public long listLeftPushAll(String key, String... values) {
		if (null == key) {
			return 0;
		}

		long result = listOps.leftPushAll(key, values);

		if (logger.isDebugEnabled()) {
			logger.debug("listAdd {} = {}", key, values);
		}

		return result;
	}

	public Map<String, String> getMap(String key) {
		if (null == key) {
			return null;
		}

		Map<String, String> value = hashOps.entries(key);

		if (logger.isDebugEnabled()) {
			logger.debug("getMap {} = {}", key, value);
		}

		return value;
	}

	public String getMap(String key, String hashKey) {
		if (null == key || null == hashKey) {
			return StringUtils.EMPTY;
		}

		String value = hashOps.get(key, hashKey);

		value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;

		if (logger.isDebugEnabled()) {
			logger.debug("getMap {} = {}", key, value);
		}

		return value;
	}

	public void setMap(String key, Map<String, String> m) {
		setMap(key, m, DEFAULT_TIMEOUT_SECONDS);
	}

	public void setMap(String key, Map<String, String> m, int timeout) {
		if (null == key || null == m) {
			return;
		}

		hashOps.putAll(key, m);

		if (timeout > 0) {
			hashOps.getOperations().expire(key, timeout, TimeUnit.SECONDS);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("setMap {} = {}", key, m);
		}
	}

	public long deleteMap(String mapKey, String hashKeys) {
		long result = hashOps.delete(mapKey, hashKeys);

		if (logger.isDebugEnabled()) {
			logger.debug("deleteMap {} {}", mapKey, hashKeys);
		}

		return result;
	}

	public boolean mapExists(String mapKey, String hashKey) {
		boolean result = hashOps.hasKey(mapKey, hashKey);

		if (logger.isDebugEnabled()) {
			logger.debug("mapExists {}  {}", mapKey, hashKey);
		}

		return result;
	}

	public void delete(String key) {
		redisTemplate.delete(key);

		if (logger.isDebugEnabled()) {
			logger.debug("del {}", key);
		}
	}
}