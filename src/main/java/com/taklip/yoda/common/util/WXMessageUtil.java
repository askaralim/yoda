package com.taklip.yoda.common.util;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import jakarta.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WXMessageUtil {
    private static Logger logger = LoggerFactory.getLogger(WXMessageUtil.class);

    public static final String MESSAGE_TYPE_TEXT = "text";
    public static final String MESSAGE_TYPE_IMAGE = "image";
    public static final String MESSAGE_TYPE_VOICE = "voice";
    public static final String MESSAGE_TYPE_VIDEO = "video";
    public static final String MESSAGE_TYPE_SHORTVIDEO = "shortvideo";
    public static final String MESSAGE_TYPE_POSOTION = "location";
    public static final String MESSAGE_TYPE_LINK = "link";
    public static final String MESSAGE_TYPE_MUSIC = "music";
    public static final String MESSAGE_TYPE_NEWS = "news";
    public static final String MESSAGE_TYPE_EVENT = "event";
    public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
    public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
    public static final String EVENT_TYPE_SCAN = "scan";
    public static final String EVENT_TYPE_LOCATION = "location";
    public static final String EVENT_TYPE_CLICK = "click";

    public static final String RESPONSE_MESSAGE_TYPE_TEXT = "text";
    public static final String RESPONSE_MESSAGE_TYPE_IMAGE = "image";
    public static final String RESPONSE_MESSAGE_TYPE_VOICE = "voice";
    public static final String RESPONSE_MESSAGE_TYPE_VIDEO = "video";
    public static final String RESPONSE_MESSAGE_TYPE_MUSIC = "music";
    public static final String RESPONSE_MESSAGE_TYPE_NEWS = "news";

    private static final String PREFIX_XML = "<xml>";

    private static final String SUFFIX_XML = "</xml>";

    private static final String PREFIX_CDATA = "<![CDATA[";

    private static final String SUFFIX_CDATA = "]]>";

    public static Map<String, String> xmlToMap(HttpServletRequest request) throws Exception {
        Map<String, String> map = new HashMap<>();

        SAXReader reader = new SAXReader();

        InputStream ins = null;

        try {
            ins = request.getInputStream();

            Document doc = reader.read(ins);

            Element root = doc.getRootElement();

            List<Element> list = root.elements();

            for (Element e : list) {
                map.put(e.getName(), e.getText());
            }

            return map;
        } catch (DocumentException e) {
            logger.error(e.getMessage());
        } finally {
            ins.close();
        }

        return null;
    }

    public static String transferMapToXML(Map<String, String> map) {
        StringBuilder builder = new StringBuilder(PREFIX_XML);

        if (!CollectionUtils.isEmpty(map)) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builder.append("<").append(entry.getKey()).append(">");
                builder.append(PREFIX_CDATA);

                if (StringUtils.isNotEmpty(entry.getValue())) {
                    builder.append(entry.getValue());
                }

                builder.append(SUFFIX_CDATA);
                builder.append("</").append(entry.getKey()).append(">");
            }
        }

        return builder.append(SUFFIX_XML).toString();
    }
}
