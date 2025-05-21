package com.taklip.yoda.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat fullDatetimeformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static DateTimeFormatter localDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    static public Date HIGHDATE;
    static public Date LOWDATE;

    static {
        dateformat.setLenient(false);
        fullDatetimeformat.setLenient(false);

        try {
            LOWDATE = dateformat.parse("2000-01-01");
            HIGHDATE = dateformat.parse("2999-12-31");
        }
        catch (Exception e) {
        }
    }

    public static LocalDateTime getHighDate() {
        LocalDateTime highDate = LocalDateTime.now();
        try {
            highDate = LocalDateTime.of(2999, 12, 31, 0, 0, 0);
        }
        catch (Exception e) {
            logger.debug("DateUtil#getHighDate: " + e.getMessage());
        }

        return highDate;
    }

    static public boolean isDate(String input) {
        if (StringUtils.isBlank(input)) {
            return false;
        }

        try {
            dateformat.parse(input);
        }
        catch (ParseException e) {
            logger.debug("DateUtil#isDate failed for input:" + input + ", detail: " + e.getMessage());
        }

        return true;
    }

    static public Date getDate(String input, DateFormat df) {
        Date date = null;

        if (StringUtils.isBlank(input)) {
            return date;
        }

        try {
            date = df.parse(input);
        }
        catch (ParseException e) {
            logger.debug("DateUtil#getDate failed for input:" + input + ", detail: " + e.getMessage());
        }

        return date;
    }

    static public Date getDate(String input) {
        Date date = null;

        if (StringUtils.isBlank(input)) {
            return date;
        }

        try {
            date = dateformat.parse(input);
        }
        catch (ParseException e) {
            logger.debug("DateUtil#getDate failed for input:" + input + ", detail: " + e.getMessage());
        }
        return date;
    }

    static public Date getFullDatetime(String input) {
        Date date = null;

        if (StringUtils.isBlank(input)) {
            return date;
        }

        try {
            date = fullDatetimeformat.parse(input);
        }
        catch (ParseException e) {
            logger.debug("DateUtil#getFullDatetime failed for input:" + input + ", detail: " + e.getMessage());
        }

        return date;
    }

    static public String getDate(Date date) {
        if (date == null) {
            return null;
        }

        return dateformat.format(date);
    }

    static public String getFullDatetime(Date date) {
        if (date == null) {
            return null;
        }

        return fullDatetimeformat.format(date);
    }

    // New methods for LocalDateTime
    static public LocalDateTime getLocalDateTime(String input) {
        if (StringUtils.isBlank(input)) {
            return null;
        }

        try {
            return LocalDateTime.parse(input, localDateTimeFormatter);
        }
        catch (Exception e) {
            logger.debug("DateUtil#getLocalDateTime failed for input:" + input + ", detail: " + e.getMessage());
            return null;
        }
    }

    static public String getDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    static public String getFullDatetime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.format(localDateTimeFormatter);
    }

    // Conversion methods
    static public LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }

        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    static public Date toDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}