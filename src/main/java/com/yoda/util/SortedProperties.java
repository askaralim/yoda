package com.yoda.util;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import com.yoda.util.StringPool;

public class SortedProperties extends Properties {

	public SortedProperties() {
		super();

		_names = new TreeSet<String>();
	}

	public SortedProperties(Properties properties) {
		this();

		Enumeration<String> enu =
			(Enumeration<String>)properties.propertyNames();

		while (enu.hasMoreElements()) {
			String key = enu.nextElement();

			String value = properties.getProperty(key);

			setProperty(key, value);
		}
	}

	@Override
	public void clear() {
		super.clear();

		_names.clear();
	}

	@Override
	public void list(PrintStream out) {
		System.out.println("-- listing properties --");

		Enumeration<String> enu = propertyNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			out.println(name + StringPool.EQUAL + getProperty(name));
		}
	}

	@Override
	public void list(PrintWriter out) {
		System.out.println("-- listing properties --");

		Enumeration<String> enu = propertyNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			out.println(name + StringPool.EQUAL + getProperty(name));
		}
	}

	@Override
	public Enumeration<String> propertyNames() {
		return Collections.enumeration(_names);
	}

	public Object put(String key, String value) {
		if (_names.contains(key)) {
			_names.remove(key);
		}

		_names.add(key);

		return super.put(key, value);
	}

	@Override
	public Object remove(Object key) {
		_names.remove(key);

		return super.remove(key);
	}

	@Override
	public Object setProperty(String key, String value) {
		return put(key, value);
	}

	private Set<String> _names;

}