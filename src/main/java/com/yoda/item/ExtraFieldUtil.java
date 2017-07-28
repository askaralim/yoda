package com.yoda.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

import com.yoda.item.model.ExtraField;
import com.yoda.item.model.Item;

public class ExtraFieldUtil {
	public static final String EXTRA_FIELD_KEY = "extraFieldKey";
	public static final String EXTRA_FIELD_VALUE = "extraFieldValue";

	public static List<ExtraField> getExtraFields(Item item) {
		List<ExtraField> extraFields = new ArrayList<ExtraField>();

		String json = item.getExtraFields();

		if (StringUtils.isEmpty(json)) {
			return extraFields;
		}

		try {
			JSONArray jsonArray = new JSONArray(json);

			if (jsonArray.length() < 1) {
				return extraFields;
			}

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);

				ExtraField extraField = new ExtraField();

				extraField.setIndex(obj.getInt("index"));
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

	public static void setExtraFields(HttpServletRequest request, Item item) {
		try {
			Enumeration<String> names = request.getParameterNames();
			JSONArray jsonArray = new JSONArray();

			while (names.hasMoreElements()) {
				String name = (String) names.nextElement();

				if (name.startsWith(EXTRA_FIELD_KEY)) {
					String extraFieldKey = request.getParameter(name);

					if (StringUtils.isEmpty(extraFieldKey)) {
						continue;
					}

					int index = Integer.valueOf(name.substring(EXTRA_FIELD_KEY.length()));

					String extraFieldvalue = request.getParameter(EXTRA_FIELD_VALUE + index);

					JSONObject json = new JSONObject();

					json.put("index", index);
					json.put("key", extraFieldKey);
					json.put("value", extraFieldvalue);

					jsonArray.put(json);
				}
			}

			item.setExtraFields(jsonArray.toString());
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
	}
}