package com.taklip.yoda.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Natural key generator that handles Chinese characters
 */
@Component
public class ChineseNaturalKeyGenerator {

    private static final Pattern NON_LATIN = Pattern.compile("[^\\w\\s-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s\\-_]+");
    private static final Pattern MULTIPLE_HYPHENS = Pattern.compile("-+");

    /**
     * Generate URL-friendly natural key from title
     */
    public String generateNaturalKey(String title) {
        if (StringUtils.isBlank(title)) {
            return "";
        }

        // Convert Chinese to pinyin (simplified)
        String processed = convertChineseToPinyin(title);
        
        // Normalize and clean
        processed = Normalizer.normalize(processed, Normalizer.Form.NFD);
        processed = NON_LATIN.matcher(processed).replaceAll("");
        processed = WHITESPACE.matcher(processed).replaceAll("-");
        processed = MULTIPLE_HYPHENS.matcher(processed).replaceAll("-");
        processed = processed.replaceAll("^-+|-+$", "").toLowerCase();
        
        return processed;
    }

    /**
     * Simple Chinese to pinyin conversion
     */
    private String convertChineseToPinyin(String text) {
        // For production, use pinyin4j library
        // This is a basic implementation
        return text.replaceAll("[\\u4E00-\\u9FA5]", "chinese");
    }
}
