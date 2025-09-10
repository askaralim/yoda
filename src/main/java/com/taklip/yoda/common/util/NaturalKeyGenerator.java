package com.taklip.yoda.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Utility for generating URL-friendly natural keys from content titles.
 * Handles Chinese characters by converting to pinyin and other special characters.
 */
@Component
public class NaturalKeyGenerator {

    private static final Pattern NON_LATIN = Pattern.compile("[^\\w\\s-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s\\-_]+");
    private static final Pattern MULTIPLE_HYPHENS = Pattern.compile("-+");

    /**
     * Generate a URL-friendly natural key from a title
     */
    public String generateNaturalKey(String title) {
        if (StringUtils.isBlank(title)) {
            return "";
        }

        // Step 1: Convert Chinese characters to pinyin (if available)
        String processed = convertChineseToPinyin(title);
        
        // Step 2: Normalize unicode characters
        processed = Normalizer.normalize(processed, Normalizer.Form.NFD);
        
        // Step 3: Remove non-latin characters except hyphens
        processed = NON_LATIN.matcher(processed).replaceAll("");
        
        // Step 4: Replace whitespace and underscores with hyphens
        processed = WHITESPACE.matcher(processed).replaceAll("-");
        
        // Step 5: Remove multiple consecutive hyphens
        processed = MULTIPLE_HYPHENS.matcher(processed).replaceAll("-");
        
        // Step 6: Remove leading/trailing hyphens and convert to lowercase
        processed = processed.replaceAll("^-+|-+$", "").toLowerCase();
        
        return processed;
    }

    /**
     * Convert Chinese characters to pinyin
     * Note: This is a simplified implementation. For production, consider using a library like pinyin4j
     */
    private String convertChineseToPinyin(String text) {
        // TODO: Implement proper Chinese to pinyin conversion
        // For now, return the original text
        // You can integrate with pinyin4j or similar library:
        // 
        // Example with pinyin4j:
        // HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        // format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        // format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        // 
        // StringBuilder result = new StringBuilder();
        // for (char c : text.toCharArray()) {
        //     if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
        //         // Chinese character
        //         String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, format);
        //         if (pinyinArray != null && pinyinArray.length > 0) {
        //             result.append(pinyinArray[0]);
        //         }
        //     } else {
        //         result.append(c);
        //     }
        // }
        // return result.toString();
        
        return text;
    }

    /**
     * Generate a unique natural key by appending a number if needed
     */
    public String generateUniqueNaturalKey(String title, java.util.function.Predicate<String> existsChecker) {
        String baseKey = generateNaturalKey(title);
        String uniqueKey = baseKey;
        int counter = 1;
        
        while (existsChecker.test(uniqueKey)) {
            uniqueKey = baseKey + "-" + counter;
            counter++;
        }
        
        return uniqueKey;
    }

    /**
     * Validate if a natural key is properly formatted
     */
    public boolean isValidNaturalKey(String naturalKey) {
        if (StringUtils.isBlank(naturalKey)) {
            return false;
        }
        
        // Should only contain lowercase letters, numbers, and hyphens
        // Should not start or end with hyphen
        // Should not contain consecutive hyphens
        return naturalKey.matches("^[a-z0-9]+(-[a-z0-9]+)*$");
    }
}
