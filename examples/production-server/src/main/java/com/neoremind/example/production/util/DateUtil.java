package com.neoremind.example.production.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date and time utility
 *
 * @author xu.zhang
 */
public class DateUtil {

    /*
     * ========================================================================== ==
     */
    /* 定义时间常量，毫秒为单位。 */
    /*
     * ========================================================================== ==
     */
    /**
     * 一秒
     */
    public static final long SECOND = 1000;

    /**
     * 一分钟
     */
    public static final long MINUTE = SECOND * 60;

    /**
     * 一小时
     */
    public static final long HOUR = MINUTE * 60;

    /**
     * 一天
     */
    public static final long DAY = 24 * HOUR;

    /**
     * 一天的起始时间
     */
    public static final String TIME_BEGIN = " 00:00:00";

    /**
     * 一天的结束时间
     */
    public static final String TIME_END = " 23:59:59";

    /*
     * ========================================================================== ==
     */
    /* 定义日期格式。 */
    /*
     * ========================================================================== ==
     */

    /**
     * 年月 <code>yyyy-MM</code>
     */
    public static final String MONTH_PATTERN = "yyyy-MM";

    /**
     * 年月日 <code>yyyyMMdd</code>
     */
    public static final String DEFAULT_PATTERN = "yyyyMMdd";

    /**
     * 年月日 <code>yyyyMMdd</code>
     */
    public static final String DOT_PATTERN = "yyyy.MM.dd";

    /**
     * 年月日时分秒 <code>yyyyMMddHHmmss</code>
     */
    public static final String FULL_PATTERN = "yyyyMMddHHmmss";

    /**
     * 标准格式的年月日时分秒 <code>yyyyMMdd HH:mm:ss</code>
     */
    public static final String FULL_STANDARD_PATTERN = "yyyyMMdd HH:mm:ss";

    /**
     * 中文格式的年月日格式 <code>yyyy-MM-dd</code>
     */
    public static final String CHINESE_PATTERN = "yyyy-MM-dd";

    /**
     * 中文格式的年月日时分秒格式 <code>yyyy-MM-dd HH:mm:ss</code>
     */
    public static final String FULL_CHINESE_PATTERN = "yyyy-MM-dd HH:mm:ss";


    // ==========================================================================
    // Date to String。
    // ==========================================================================

    /**
     * 将日期转换为 <code>yyyyMMdd</code> 的字符串格式
     *
     * @param date 日期 @see Date
     * @return 格式化后的日期字符串
     */
    public static String formatDate(final Date date) {
        return formatDate(date, DEFAULT_PATTERN);
    }

    /**
     * 将日期转换为指定的字符串格式
     *
     * @param date   日期 @see Date
     * @param format 日期格式
     * @return 格式化后的日期字符串，如果<code>date</code>为<code>null</code>或者 <code>format</code>为空，则返回<code>null</code>。
     */
    public static String formatDate(final Date date, String format) {
        if (null == date || StringUtils.isBlank(format)) {
            return null;
        }

        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 将当前日期转换为指定的字符串格式
     *
     * @param format 日期格式
     * @return 格式化后的日期字符串
     */
    public static String formatDate(String format) {
        return formatDate(new Date(), format);
    }

    // ==========================================================================
    // String to Date。
    // ==========================================================================

    /**
     * 将<code>yyyyMMdd<code>格式的字符串转变为日期对象
     *
     * @param sDate 日期字符串
     * @return 日期
     */
    public static Date parseDate(String sDate) {
        return parseDate(sDate, DEFAULT_PATTERN, null);
    }

    /**
     * 将字符串转换撑日期对象
     *
     * @param sDate  日期字符串
     * @param format 日期格式 @see DateFormat
     * @return 日期对象 @see Date
     */
    public static Date parseDate(String sDate, String format) {
        return parseDate(sDate, format, null);
    }

    /**
     * 将字符串转换成日期对象
     *
     * @param sDate        日期字符串
     * @param format       日期格式 @see DateFormat
     * @param defaultValue 默认值
     * @return 日期对象，如果格式化失败则返回默认值<code>defaultValue</code>
     */
    public static Date parseDate(String sDate, String format, Date defaultValue) {
        if (StringUtils.isBlank(sDate) || StringUtils.isBlank(format)) {
            return defaultValue;
        }

        DateFormat formatter = new SimpleDateFormat(format);
        try {
            return formatter.parse(sDate);
        } catch (ParseException e) {
            return defaultValue;
        }

    }

}
