package com.taklip.yoda.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext appContext = null;

	public static Object getBean(String name) {
		return appContext.getBean(name);

	}

	public static <T> T getBean(Class<T> clazz) {
		return appContext.getBean(clazz);
	}

	public static <T> T getBean(String name, Class<T> clazz) {
		return appContext.getBean(name, clazz);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (appContext == null) {
			appContext = applicationContext;
		}
	}
}
