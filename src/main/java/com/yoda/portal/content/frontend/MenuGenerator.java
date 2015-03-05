package com.yoda.portal.content.frontend;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.yoda.portal.content.data.MenuInfo;

public class MenuGenerator {

	public static String generateMenu(
			List<MenuInfo> menuInfos, String styleClassSuffix, Locale locale) {
		StringBuffer menu = generateMenuItem(menuInfos, styleClassSuffix, false);

		return menu.toString();
	}

	private static StringBuffer generateMenuItem(List<MenuInfo> menuInfos, String styleClassSuffix, boolean isChild) {
		StringBuffer sb = new StringBuffer();

		if (isChild) {
			sb.append("<ul class=\"dropdown-menu\" role=\"menu\">");
		}

		for (MenuInfo menuInfo : menuInfos) {
			if (menuInfo.getMenus().size() > 0) {
				sb.append("<li class=\"dropdown\">");
				sb.append("<a href=\"" + menuInfo.getMenuUrl() + "\" " + "class=\"dropdown-toggle\" data-toggle=\"dropdown\" onclick=\"javascrpt:window.open('" + menuInfo.getMenuUrl() + "', " + "'" + menuInfo.getMenuWindowTarget() + "' ");

				if (menuInfo.getMenuWindowMode().trim().length() != 0) {
					sb.append(", '" + menuInfo.getMenuWindowMode() + "'");
				}

				sb.append(");return false;\">");
				sb.append(menuInfo.getMenuTitle());
				sb.append("<span class=\"caret\"></span>");
				sb.append("</a>");

				sb.append(generateMenuItem(menuInfo.getMenus(), styleClassSuffix, true));

				sb.append("</li>");
			}
			else {
				sb.append("<li class=\"page-scroll\">");
				sb.append("<a href=\"" + menuInfo.getMenuUrl() + "\" " + "onclick=\"javascrpt:window.open('" + menuInfo.getMenuUrl() + "', " + "'" + menuInfo.getMenuWindowTarget() + "' ");

				if (menuInfo.getMenuWindowMode().trim().length() != 0) {
					sb.append(", '" + menuInfo.getMenuWindowMode() + "'");
				}

				sb.append(");return false;\">");
				sb.append(menuInfo.getMenuTitle());
				sb.append("</a>");
				sb.append("</li>");
			}
		}

		if (isChild) {
			sb.append("</ul>");
		}

		return sb;
	}

	Logger logger = Logger.getLogger(MenuGenerator.class);
}