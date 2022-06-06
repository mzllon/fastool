package tech.fastool.core.date;

import tech.fastool.core.lang.SimpleCache;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 解决老的日期格式化{@linkplain SimpleDateFormat}线程不安全问题
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class ThreadSafeDateParse {

    /**
     * 线程
     */
    private static final ThreadLocal<Map<String, DateFormat>> OLD_DATE_FORMAT = ThreadLocal.withInitial(HashMap::new);

    /**
     * 日期格式化缓存
     */
    private static final SimpleCache<String, DateTimeFormatter> NEW_FORMATTER_CACHE = new SimpleCache<>(32);

    /**
     * 创建Formatter并且缓存
     *
     * @param pattern 日期格式字符串
     * @return 返回Formatter
     */
    static DateTimeFormatter getOrCreateFormatter(String pattern) {
        DateTimeFormatter formatter;
        synchronized (NEW_FORMATTER_CACHE) {
            formatter = NEW_FORMATTER_CACHE.get(pattern);
        }
        if (formatter == null) {
            formatter = DateTimeFormatter.ofPattern(pattern);
            synchronized (NEW_FORMATTER_CACHE) {
                NEW_FORMATTER_CACHE.put(pattern, formatter);
            }
        }
        return formatter;
    }

    /**
     * 得到日期格式化类
     *
     * @param pattern 日期格式化风格
     * @return {@linkplain DateFormat}
     */
    private static DateFormat getParser(String pattern) {
        Map<String, DateFormat> parserMap = OLD_DATE_FORMAT.get();
        DateFormat df = parserMap.get(pattern);
        if (df == null) {
            df = new SimpleDateFormat(pattern);
            parserMap.put(pattern, df);
        }
        return df;
    }

    /**
     * 解析日期字符串
     *
     * @param text    日期字符串
     * @param pattern 解析格式
     * @return 日期对象
     * @throws ParseException 解析异常
     */
    public static Date parse(String text, String pattern) throws ParseException {
        return getParser(pattern).parse(text);
    }

    /**
     * 解析日期字符串
     *
     * @param text     日期字符串
     * @param pattern  解析格式
     * @param timeZone 时区
     * @return 日期对象
     * @throws ParseException 解析异常
     */
    public static Date parse(String text, String pattern, TimeZone timeZone) throws ParseException {
        DateFormat dateFormat = getParser(pattern);
        dateFormat.setTimeZone(timeZone);
        return dateFormat.parse(text);
    }

    /**
     * 解析日期字符串
     *
     * @param text    日期字符串
     * @param pattern 解析格式
     * @param locale  时区
     * @return 日期对象
     * @throws ParseException 解析异常
     */
    public static Date parse(String text, String pattern, Locale locale) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
        return sdf.parse(text);
    }

    /**
     * 解析日期字符串
     *
     * @param text    日期字符串
     * @param pattern 解析格式
     * @return 日期
     * @throws ParseException 解析异常
     */
    public static long parseLongDate(String text, String pattern) throws ParseException {
        return parse(text, pattern).getTime();
    }

    /**
     * 格式化日期，转为字符串
     *
     * @param theDate 日期
     * @param pattern 格式化规则
     * @return 格式化后的日期字符串
     */
    public static String format(Date theDate, String pattern) {
        return getParser(pattern).format(theDate);
    }

    /**
     * 格式化日期，转为字符串
     *
     * @param epochMilli 从1970-01-01 00:00:00开始的毫秒数
     * @param pattern    格式化规则
     * @return 格式化后的日期字符串
     */
    public static String format(long epochMilli, String pattern) {
        return getParser(pattern).format(new Date(epochMilli));
    }

}
