package com.taklip.yoda.tools;

import javax.servlet.http.HttpServletRequest;

import net.fckeditor.FCKeditor;

public class FckEditorCreator {
	static public String getFckEditor(
			HttpServletRequest request, String id, String width, String height,
			String toolSet, String value) {
		FCKeditor editor;
		String text = value;

		if (text == null) {
			text = "";
		}

		if (text.length() == 0) {
			text = "&nbsp";
		}

		editor = new FCKeditor(request, id, width, height, toolSet, text, null);

		String basePath = request.getContextPath() + "/FCKeditor/";

		editor.setBasePath(basePath);

		return editor.createHtml();
	}
}
