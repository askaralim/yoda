package com.taklip.yoda.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import com.taklip.yoda.model.ExtraField;
import com.taklip.yoda.model.Item;

import jakarta.servlet.http.HttpServletRequest;

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
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return extraFields;
    }

    public static String setBuyLinks(List<ExtraField> extraFields) {
        return setExtraFields(extraFields, BUY_LINK_KEY, BUY_LINK_VALUE);
    }

    public static String setExtraFields(List<ExtraField> extraFields) {
        return setExtraFields(extraFields, EXTRA_FIELD_KEY, EXTRA_FIELD_VALUE);
    }

    private static String setExtraFields(List<ExtraField> extraFields, String key, String value) {
        JSONArray jsonArray = new JSONArray();

        try {
            for (ExtraField extraField : extraFields) {

                if (extraField.getKey().startsWith(key)) {
                    String extraFieldKey = extraField.getKey();

                    if (StringUtils.isEmpty(extraFieldKey)) {
                        continue;
                    }

                    int index = Integer.valueOf(extraField.getKey().substring(key.length()));

                    String extraFieldvalue = extraField.getValue();

                    JSONObject json = new JSONObject();

                    json.put("index", index);
                    json.put("key", extraFieldKey);
                    json.put("value", extraFieldvalue);

                    jsonArray.add(json);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray.toString();
    }
}
