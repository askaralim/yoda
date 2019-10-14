package com.taklip.yoda.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.taklip.yoda.model.ExtraField;
import com.taklip.yoda.model.Item;

public class ExtraFieldUtil {
	public static final String EXTRA_FIELD_KEY = "extraFieldKey";
	public static final String EXTRA_FIELD_VALUE = "extraFieldValue";

	public static final String BUY_LINK_KEY = "buyLinkKey";
	public static final String BUY_LINK_VALUE = "buyLinkValue";

	public static List<ExtraField> getBuyLinks(Item item) {
		String json = item.getBuyLinks();

		return getExtraFields(json);
	}

	public static List<ExtraField> getExtraFields(Item item) {
		String json = item.getExtraFields();

		return getExtraFields(json);
	}

	private static List<ExtraField> getExtraFields(String json) {
		List<ExtraField> extraFields = new ArrayList<>();

		if (StringUtils.isEmpty(json)) {
			return extraFields;
		}

		try {
			JSONArray jsonArray = JSONArray.parseArray(json);

			if (jsonArray.isEmpty()) {
				return extraFields;
			}

			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);

				ExtraField extraField = new ExtraField();

				extraField.setIndex(obj.getInteger("index"));
				extraField.setKey(obj.getString("key"));
				extraField.setValue(obj.getString("value"));

				extraFields.add(extraField);
			}

			Collections.sort(extraFields);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}

		return extraFields;
	}

	public static void setBuyLinks(HttpServletRequest request, Item item) {
		String json = setExtraFields(request, item, BUY_LINK_KEY, BUY_LINK_VALUE);

		item.setBuyLinks(json);
	}

	public static void setExtraFields(HttpServletRequest request, Item item) {
		String json = setExtraFields(request, item, EXTRA_FIELD_KEY, EXTRA_FIELD_VALUE);

		item.setExtraFields(json);
	}

	private static String setExtraFields(HttpServletRequest request, Item item, String key, String value) {
		JSONArray jsonArray = new JSONArray();

		try {
			Enumeration<String> names = request.getParameterNames();

			while (names.hasMoreElements()) {
				String name = (String) names.nextElement();

				if (name.startsWith(key)) {
					String extraFieldKey = request.getParameter(name);

					if (StringUtils.isEmpty(extraFieldKey)) {
						continue;
					}

					int index = Integer.valueOf(name.substring(key.length()));

					String extraFieldvalue = request.getParameter(value + index);

					JSONObject json = new JSONObject();

					json.put("index", index);
					json.put("key", extraFieldKey);
					json.put("value", extraFieldvalue);

					jsonArray.add(json);
				}
			}
		}
		catch (JSONException e) {
			e.printStackTrace();
		}

		return jsonArray.toString();
	}
}