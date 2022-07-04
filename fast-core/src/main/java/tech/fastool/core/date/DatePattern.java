package tech.fastool.core.date;

import tech.fastool.core.lang.Objects;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * 常见的日期格式化常量
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class DatePattern {

    // region ================ Normal ================

    /**
     * 标准日期格式 yyyy-MM-dd
     */
    public static final String NORMAL_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 标准日期格式 {@link DateTimeFormatter} yyyy-MM-dd
     */
    public static final DateTimeFormatter NORMAL_DATE_FORMATTER = DateTimeFormatter.ofPattern(NORMAL_DATE_PATTERN);

    /**
     * 智能日期格式 yyyy-M-d
     * 可解析如下的字符串:
     * 1990-1-1
     * 1990-1-10
     * 1990-10-1
     * 1990-10-01
     * 1990-10-10
     */
    public static final String SMART_NORMAL_DATE_PATTERN = "yyyy-M-d";

    /**
     * 智能日期格式 yyyy-M-d
     * 可解析如下的字符串:
     * 1990-1-1
     * 1990-1-10
     * 1990-10-1
     * 1990-10-01
     * 1990-10-10
     */
    public static final DateTimeFormatter SMART_NORMAL_DATE_FORMATTER = DateTimeFormatter.ofPattern(SMART_NORMAL_DATE_PATTERN);

    /**
     * 标准时间格式 HH:mm:ss
     */
    public final static String NORMAL_TIME_PATTERN = "HH:mm:ss";

    /**
     * 标准时间格式 {@link DateTimeFormatter} HH:mm:ss
     */
    public final static DateTimeFormatter NORMAL_TIME_FORMATTER = DateTimeFormatter.ofPattern(NORMAL_TIME_PATTERN);

    /**
     * 智能时间格式 H:m[:s]
     * 可解析如下字符串
     * 2:3
     * 2:30
     * 12:30
     * 12:3
     * 12:03
     * 12:03:1
     * 12:03:11
     */
    public final static String SMART_NORMAL_TIME_PATTERN = "H:m[:s]";

    /**
     * 智能时间格式 H:m[:s]
     * 可解析如下字符串
     * 2:3
     * 2:30
     * 12:30
     * 12:3
     * 12:03
     * 12:03:1
     * 12:03:11
     */
    public static final DateTimeFormatter SMART_NORMAL_TIME_FORMATTER = DateTimeFormatter.ofPattern(SMART_NORMAL_TIME_PATTERN);

    /**
     * 标准日期时间格式，精确到分 yyyy-MM-dd HH:mm
     */
    public final static String NORMAL_DATETIME_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";

    /**
     * 标准日期时间格式，精确到分 {@link DateTimeFormatter} yyyy-MM-dd HH:mm
     */
    public final static DateTimeFormatter NORMAL_DATETIME_MINUTE_FORMATTER = DateTimeFormatter.ofPattern(NORMAL_DATETIME_MINUTE_PATTERN);


    /**
     * 标准日期时间格式，精确到秒 yyyy-MM-dd HH:mm:ss
     */
    public final static String NORMAL_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 标准日期时间格式，精确到秒 {@link DateTimeFormatter}：yyyy-MM-dd HH:mm:ss
     */
    public final static DateTimeFormatter NORMAL_DATETIME_FORMATTER = DateTimeFormatter.ofPattern(NORMAL_DATETIME_PATTERN);

    /**
     * 智能的日期时间格式：yyyy-M-d H:m:s
     */
    public static final String SMART_NORMAL_DATETIME_PATTERN = "yyyy-M-d H:m:s";

    /**
     * 智能的日期时间格式：yyyy-M-d H:m:s
     */
    public static final DateTimeFormatter SMART_NORMAL_DATETIME_FORMATTER = DateTimeFormatter.ofPattern(SMART_NORMAL_DATETIME_PATTERN);

    /**
     * 标准日期时间格式，精确到毫秒 yyyy-MM-dd HH:mm:ss.SSS
     */
    public final static String NORMAL_DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 标准日期时间格式，精确到毫秒 {@link DateTimeFormatter}：yyyy-MM-dd HH:mm:ss.SSS
     */
    public final static DateTimeFormatter NORMAL_DATETIME_MS_FORMATTER = DateTimeFormatter.ofPattern(NORMAL_DATETIME_MS_PATTERN);

    /**
     * 智能的日期时间（含毫秒）格式
     *
     * @see #SMART_NORMAL_DATE_PATTERN
     * @see #SMART_NORMAL_TIME_PATTERN
     */
    public static final String SMART_NORMAL_PATTERN = "yyyy-M-d[ H][:m][:s][.SSS]";

    /**
     * 智能的日期时间（含毫秒）格式
     *
     * @see #SMART_NORMAL_DATE_FORMATTER
     * @see #SMART_NORMAL_TIME_FORMATTER
     */
    public static final DateTimeFormatter SMART_NORMAL_FORMATTER = DateTimeFormatter.ofPattern(SMART_NORMAL_PATTERN);

    // endregion

    // region ================ Pure ================

    /**
     * 标准日期格式 yyyyMMdd
     */
    public final static String PURE_DATE_PATTERN = "yyyyMMdd";

    /**
     * 标准日期格式 {@link DateTimeFormatter} yyyyMMdd
     */
    public final static DateTimeFormatter PURE_DATE_FORMATTER = DateTimeFormatter.ofPattern(PURE_DATE_PATTERN);

    /**
     * 标准日期格式：HHmmss
     */
    public final static String PURE_TIME_PATTERN = "HHmmss";

    /**
     * 标准日期格式 {@link DateTimeFormatter}：HHmmss
     */
    public final static DateTimeFormatter PURE_TIME_FORMATTER = DateTimeFormatter.ofPattern(PURE_TIME_PATTERN);

    /**
     * 标准日期格式：yyyyMMddHHmmss
     */
    public final static String PURE_DATETIME_PATTERN = "yyyyMMddHHmmss";

    /**
     * 标准日期格式 {@link DateTimeFormatter}：yyyyMMddHHmmss
     */
    public final static DateTimeFormatter PURE_DATETIME_FORMATTER = DateTimeFormatter.ofPattern(PURE_DATETIME_PATTERN);

    /**
     * 标准日期格式：yyyyMMddHHmmssSSS
     */
    public final static String PURE_DATETIME_MS_PATTERN = "yyyyMMddHHmmssSSS";

    /**
     * 标准日期格式 {@link DateTimeFormatter}：yyyyMMddHHmmssSSS
     */
    public final static DateTimeFormatter PURE_DATETIME_MS_FORMATTER = DateTimeFormatter.ofPattern(PURE_DATETIME_MS_PATTERN);

    // endregion

    // region ================ 中文日期格式 ================

    /**
     * 中文日期格式：yyyy年MM月dd日
     */
    public final static String CHINESE_DATE_PATTERN = "yyyy年MM月dd日";

    /**
     * 中文日期格式：yyyy年MM月dd日
     */
    public final static DateTimeFormatter CHINESE_DATE_FORMATTER = DateTimeFormatter.ofPattern(CHINESE_DATE_PATTERN);

    // endregion

    // region ================ UTC And Zone ================

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss
     */
    public static final String UTC_SIMPLE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss.SSS
     */
    public static final String UTC_MS_SIMPLE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss'Z'
     */
    public static final String UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     */
    public static final String UTC_MS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ssZ
     */
    public final static String UTC_WITH_ZONE_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ssZ
     */
    public final static DateTimeFormatter UTC_WITH_ZONE_OFFSET_FORMATTER;

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss.SSSZ
     */
    public final static String UTC_MS_WITH_ZONE_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss.SSSZ
     */
    public final static DateTimeFormatter UTC_MS_WITH_ZONE_OFFSET_FORMATTER;

    /**
     * DatePattern.UTC_MS_WITH_ZONE_OFFSET_FORMATTER
     * RFC3339标准格式：yyyy-MM-dd'T'HH:mm:ss.SSS+TIMEZONE
     * 如：2020-04-12 17:00:00.120+09:00
     */
    public static final DateTimeFormatter RFC_3339_FORMATTER;

    // endregion

    // region ================ Others ================

    /**
     * JDK中日期时间格式：EEE MMM dd HH:mm:ss zzz yyyy
     */
    public static final String JDK_DATETIME_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";

    /**
     * 标准日期时间正则，每个字段支持单个数字或2个数字，包括：
     * <pre>
     *     yyyy-MM-dd HH:mm:ss.SSS
     *     yyyy-MM-dd HH:mm:ss
     *     yyyy-MM-dd HH:mm
     *     yyyy-MM-dd
     * </pre>
     */
    public static final Pattern REGEX_NORM = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}(\\s\\d{1,2}:\\d{1,2}(:\\d{1,2})?)?(.\\d{1,3})?");

    // endregion

    static {
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.appendPattern(UTC_WITH_ZONE_OFFSET_PATTERN)
                .appendOffsetId();
        UTC_WITH_ZONE_OFFSET_FORMATTER = builder.toFormatter();

        builder = new DateTimeFormatterBuilder();
        builder.appendPattern(UTC_MS_WITH_ZONE_OFFSET_PATTERN)
                .appendOffsetId();
        UTC_MS_WITH_ZONE_OFFSET_FORMATTER = builder.toFormatter();
        RFC_3339_FORMATTER = UTC_MS_WITH_ZONE_OFFSET_FORMATTER;
    }

    private static final Map<String, DateTimeFormatter> BUILTIN;

    static {
        BUILTIN = new ConcurrentHashMap<>(32);

        // normal
        BUILTIN.put(NORMAL_DATE_PATTERN, NORMAL_DATE_FORMATTER);
        BUILTIN.put(SMART_NORMAL_DATE_PATTERN, SMART_NORMAL_DATE_FORMATTER);
        BUILTIN.put(NORMAL_TIME_PATTERN, NORMAL_TIME_FORMATTER);
        BUILTIN.put(NORMAL_DATETIME_MINUTE_PATTERN, NORMAL_DATETIME_MINUTE_FORMATTER);
        BUILTIN.put(NORMAL_DATETIME_PATTERN, NORMAL_DATETIME_FORMATTER);
        BUILTIN.put(SMART_NORMAL_DATETIME_PATTERN, SMART_NORMAL_DATETIME_FORMATTER);
        BUILTIN.put(NORMAL_DATETIME_MS_PATTERN, NORMAL_DATETIME_MS_FORMATTER);
        // pure
        BUILTIN.put(PURE_DATE_PATTERN, PURE_DATE_FORMATTER);
        BUILTIN.put(PURE_TIME_PATTERN, PURE_TIME_FORMATTER);
        BUILTIN.put(PURE_DATETIME_PATTERN, PURE_DATETIME_FORMATTER);
        BUILTIN.put(PURE_DATETIME_MS_PATTERN, PURE_DATETIME_MS_FORMATTER);
        // chinese
        BUILTIN.put(CHINESE_DATE_PATTERN, CHINESE_DATE_FORMATTER);
        // utc
        BUILTIN.put(UTC_WITH_ZONE_OFFSET_PATTERN, UTC_WITH_ZONE_OFFSET_FORMATTER);
        BUILTIN.put(UTC_MS_WITH_ZONE_OFFSET_PATTERN, UTC_MS_WITH_ZONE_OFFSET_FORMATTER);
    }

    /**
     * 获取格式化器
     *
     * @param pattern 日期格式
     * @return {@linkplain DateTimeFormatter}格式化器
     */
    public static DateTimeFormatter ofPattern(String pattern) {
        Objects.requireNotEmpty(pattern, "pattern must not be null or empty");
        DateTimeFormatter result = BUILTIN.get(pattern);
        if (result != null) {
            return result;
        }
        return ThreadSafeDateParse.getOrCreateFormatter(pattern);
    }

}
