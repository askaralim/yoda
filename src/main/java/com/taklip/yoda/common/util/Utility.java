package com.taklip.yoda.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Utility {
	public static String getStackTrace(Throwable t) {
		StringWriter string = new StringWriter();
		PrintWriter print = new PrintWriter(string, true);
		t.printStackTrace(print);
		print.flush();
		string.flush();
		return string.toString();
	}
}
