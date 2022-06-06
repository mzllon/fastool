package tech.fastool.core.date;

import tech.fastool.core.exceptions.DateRuntimeException;
import tech.fastool.core.lang.CharUtil;
import tech.fastool.core.lang.NumberUtil;
import tech.fastool.core.lang.ObjectUtil;
import tech.fastool.core.lang.StringUtil;
import tech.fastool.core.lang.regex.Patterns;
import tech.fastool.core.lang.regex.RegexUtil;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * 老的日期工具类，即{@linkplain Date}
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class DateUtil {

    private DateUtil() {
        throw new AssertionError("Cannot create instance!");
    }

    /**
     * java.util.Date EEE MMM zzz 缩写数组
     */
    public static final String[] WTB = {
            "sun", "mon", "tue", "wed", "thu", "fri", "sat", // 星期
            "jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec", // 月份
            "gmt", "ut", "utc", "est", "edt", "cst", "cdt", "mst", "mdt", "pst", "pdt" //时区
    };

    /**
     * 返回此时此刻的时间
     *
     * @return 此时此刻时间
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 规范化日期，以空格区分日期和时间，空格前为日期，空格后为时间<br>
     * 将以下字符串会替换为"-"
     * <pre>
     *     "."
     *     "/"
     *     "年"
     *     "月"
     * </pre>
     * <p>
     * 删除"日"字符
     * <p>
     * 将以下字符替换为":"
     * <pre>
     * "时"
     * "分"
     * "秒"
     * </pre>
     * 当末位是":"时去除之（不存在毫秒时）
     *
     * @param dateStr 日期时间字符串
     * @return 格式化后的日期字符串
     */
    public static String normalize(CharSequence dateStr) {
        if (StringUtil.isBlank(dateStr)) {
            return StringUtil.str(dateStr);
        }
        String str = StringUtil.trim(dateStr);
        int index = str.indexOf('日');
        if (index > 0) {
            if (str.charAt(index + 1) == CharUtil.SPACE) {
                str = StringUtil.delete(str, index, index + 1);
            } else {
                str = StringUtil.replace(str, index, index + 1, CharUtil.SPACE);
            }
        }
        List<String> resultList = StringUtil.splitToList(str, CharUtil.SPACE, true, true);
        if (resultList.size() > 2) {
            // 无法规范化的日期格式，按照空格分开日期和时间
            return dateStr.toString();
        }

        // 日期部分（"\"、"/"、"."、"年"、"月"都替换为"-"）
        String datePart = StringUtil.replace(resultList.get(0), new char[]{'.', '/', '年', '月'}, StringUtil.DASH);

        if (resultList.size() == 2) {
            String timePart = StringUtil.replace(resultList.get(1), new char[]{'时', '分', '秒'}, ":");
            timePart = StringUtil.deleteSuffix(timePart, ":");
            return datePart + " " + timePart;
        }
        return datePart;
    }

    //region format date method

    /**
     * 格式化当前时间，格式化规则为: yyyy-MM-dd HH:mm:ss
     *
     * @return 格式化后的日期字符串
     * @see Jdk8DateUtil#format()
     */
    public static String format() {
        return format((String) null);
    }

    /**
     * 格式化当前时间，格式化规则为{@code pattern}
     *
     * @param pattern 格式化规则，为空时默认采用格式：yyyy-MM-dd HH:mm:ss
     * @return 日期字符串
     */
    public static String format(String pattern) {
        return Jdk8DateUtil.format(pattern);
    }

    /**
     * 将时间戳格式化，格式化规则为: yyyy-MM-dd HH:mm:ss
     *
     * @param epochMilli 日期毫秒数
     * @return 日期字符串
     */
    public static String format(final long epochMilli) {
        return format(epochMilli, DatePattern.NORMAL_DATETIME_PATTERN);
    }

    /**
     * 将时间戳格式化，格式化规则为{@code pattern}
     *
     * @param epochMilli 毫秒数
     * @param pattern    格式化规则
     * @return 日期字符串
     */
    public static String format(final long epochMilli, final String pattern) {
        ObjectUtil.isTrue(epochMilli >= 0, "epochMilli >= 0");
        String strPattern;
        if (StringUtil.isBlank(pattern)) {
            strPattern = DatePattern.NORMAL_DATETIME_PATTERN;
        } else {
            strPattern = pattern;
        }
        return ThreadSafeDateParse.format(epochMilli, strPattern);
    }

    /**
     * 格式化指定{@code date}，格式化规则为: yyyy-MM-dd HH:mm:ss
     *
     * @param date 日期对象
     * @return 日期字符串
     */
    public static String format(Date date) {
        return format(date, DatePattern.NORMAL_DATETIME_PATTERN);
    }

    /**
     * 格式化指定{@code date}，格式化规则为{@code pattern}
     *
     * @param date    日期对象
     * @param pattern 格式化规则
     * @return 日期字符串
     */
    public static String format(final Date date, final String pattern) {
        if (date == null) {
            return null;
        }
        String strPattern;
        if (StringUtil.isBlank(pattern)) {
            strPattern = DatePattern.NORMAL_DATETIME_PATTERN;
        } else {
            strPattern = pattern;
        }
        return ThreadSafeDateParse.format(date, strPattern);
    }

    /**
     * 转换日期格式,从{@code srcPattern}转为{@code dstPattern}<br>
     * 如将2013/01/01转为2013-01-01
     *
     * @param text        日期字符串
     * @param srcPattern  原始的格式化规则
     * @param destPattern 转换的日期格式化规则
     * @return 转换后的日期字符串
     */
    public static String swapFormat(CharSequence text, String srcPattern, String destPattern) {
        if (StringUtil.isAnyEmpty(text, srcPattern, destPattern)) {
            return null;
        }
        if (srcPattern.length() > text.length()) {
            throw new DateRuntimeException("SrcPattern '" + srcPattern + "' is long than text '" + text);
        }
        String newText = text.toString();
        if (srcPattern.length() < text.length()) {
            // 尝试截取，但不保证截取正确
            newText = newText.substring(0, srcPattern.length());
        }
        try {
            Date theDate = ThreadSafeDateParse.parse(newText, srcPattern);
            return ThreadSafeDateParse.format(theDate, destPattern);
        } catch (ParseException e) {
            throw new DateRuntimeException(e);
        }
    }

    //endregion


    //region parse method

    /**
     * 会尽量解析日期字符串，适用于不太确定输入的日期格式，支持如下格式：<br>
     * <ol>
     *     <li>yyyy-MM-dd HH:mm:ss.SSS</li>
     *     <li>yyyy-MM-dd HH:mm:ss</li>
     *     <li>yyyy-MM-dd HH:mm</li>
     *     <li>yyyy/MM/dd HH:mm:ss</li>
     *     <li>yyyy.MM.dd HH:mm:ss</li>
     *     <li>yyyy年MM月dd日 HH时mm分ss秒</li>
     *     <li>yyyy-MM-dd</li>
     *     <li>yyyy/MM/dd</li>
     *     <li>yyyy.MM.dd</li>
     *     <li>yyyyMMddHHmmss</li>
     *     <li>yyyyMMddHHmmssSSS</li>
     *     <li>yyyyMMdd</li>
     *     <li>yyyy-MM-dd'T'HH:mm:ss'Z'</li>
     *     <li>yyyy-MM-dd'T'HH:mm:ss.SSS'Z'</li>
     *     <li>yyyy-MM-dd'T'HH:mm:ssZ</li>
     *     <li>yyyy-MM-dd'T'HH:mm:ss.SSSZ</li>
     * </ol>
     *
     * @param dateCse 日期字符串
     * @return 日期
     */
    public static Date tryParse(CharSequence dateCse) {
        if (StringUtil.isBlank(dateCse)) {
            return null;
        }
        String dateStr = StringUtil.trim(dateCse);
        int len = dateStr.length();

        if (NumberUtil.isDigits(dateStr)) {
            if (len == DatePattern.PURE_DATETIME_MS_PATTERN.length()) {
                return parse(dateStr, DatePattern.PURE_DATETIME_MS_PATTERN);
            } else if (len == DatePattern.PURE_DATETIME_PATTERN.length()) {
                return parse(dateStr, DatePattern.PURE_DATETIME_PATTERN);
            } else if (len == DatePattern.PURE_DATE_PATTERN.length()) {
                return parse(dateStr, DatePattern.PURE_DATE_PATTERN);
            } else if (len == DatePattern.PURE_TIME_PATTERN.length()) {
                return parse(dateStr, DatePattern.PURE_TIME_PATTERN);
            }
        } else if (RegexUtil.isMatch(Patterns.TIME, dateStr)) {
            // HH:mm:ss 或者 HH:mm 时间格式匹配单独解析
            return parseTimeAsToday(dateStr);
        } else if (StringUtil.containsAnyIgnoreCase(dateStr, WTB)) {
            // JDK的Date对象toString默认格式，类似于：
            // Tue Jun 4 16:25:15 +0800 2021
            // Thu May 16 17:57:18 GMT+08:00 2021
            // Wed Aug 01 00:00:00 CST 2021
            return parseCST(dateStr);
        } else if (StringUtil.contains(dateStr, 'T')) {
            // UTC时间
            return parseUTC(dateStr);
        }

        //规范化日期格式（包括单个数字的日期时间）
        dateStr = normalize(dateStr);
        if (RegexUtil.isMatch(DatePattern.REGEX_NORM, dateStr)) {
            int colonCount = StringUtil.count(dateStr, CharUtil.COLON);
            if (colonCount == 0) {
                // yyyy-MM-dd
                return parse(dateStr, DatePattern.NORMAL_DATE_PATTERN);
            } else if (colonCount == 1) {
                // yyyy-MM-dd HH:mm
                return parse(dateStr, DatePattern.NORMAL_DATETIME_MINUTE_PATTERN);
            } else if (colonCount == 2) {
                if (StringUtil.contains(dateStr, CharUtil.DOT)) {
                    // yyyy-MM-dd HH:mm:ss.SSS
                    return parse(dateStr, DatePattern.NORMAL_DATETIME_MS_PATTERN);
                } else {
                    // yyyy-MM-dd HH:mm:ss
                    return parse(dateStr, DatePattern.NORMAL_DATETIME_PATTERN);
                }
            }
        }

        return null;
    }

    /**
     * 解析日期时间字符串
     *
     * @param dateCse 日期时间字符串
     * @param pattern 格式
     * @return {@linkplain Date}
     */
    public static Date parse(CharSequence dateCse, String pattern) {
        if (ObjectUtil.isAnyEmpty(dateCse, pattern)) {
            return null;
        }
        String dateStr = StringUtil.trim(dateCse);
        try {
            return ThreadSafeDateParse.parse(dateStr, pattern, Locale.US);
        } catch (ParseException e) {
            throw new DateRuntimeException(e);
        }
    }

    /**
     * 解析日期字符串,解析规则: yyyy-MM-dd HH:mm:ss
     *
     * @param dateCse 日期字符串
     * @return 解析后的日期
     */
    public static Date parse(CharSequence dateCse) {
        return parse(dateCse, DatePattern.NORMAL_DATETIME_PATTERN);
    }

    /**
     * 解析时间，格式HH:mm 或 HH:mm:ss，日期默认为今天
     *
     * @param cse 标准的时间字符串
     * @return {@linkplain Date}
     */
    public static Date parseTimeAsToday(CharSequence cse) {
        if (StringUtil.isBlank(cse)) {
            return null;
        }
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.parse(cse, DatePattern.SMART_NORMAL_TIME_FORMATTER);
//        if (StringUtil.count(cse, ':') == 1) {
//            localTime = LocalTime.parse(cse, DatePattern.ofPattern("HH:mm"));
//        } else {
//            localTime = LocalTime.parse(cse, DatePattern.NORMAL_TIME_FORMATTER);
//        }
        return Jdk8DateUtil.toDate(LocalDateTime.of(localDate, localTime));
    }

    /**
     * 解析CST时间，格式：
     * <ol>
     * <li>EEE MMM dd HH:mm:ss z yyyy（例如：Wed Aug 01 00:00:00 CST 2012）</li>
     * </ol>
     *
     * @param cse CST时间
     * @return {@linkplain Date}
     */
    public static Date parseCST(CharSequence cse) {
        return parse(cse, DatePattern.JDK_DATETIME_PATTERN);
    }

    /**
     * 解析UTC时间，格式：
     * <ol>
     * <li>yyyy-MM-dd'T'HH:mm:ss'Z'</li>
     * <li>yyyy-MM-dd'T'HH:mm:ss.SSS'Z'</li>
     * <li>yyyy-MM-dd'T'HH:mm:ssZ</li>
     * <li>yyyy-MM-dd'T'HH:mm:ss.SSSZ</li>
     * </ol>
     *
     * @param cse UTC时间
     * @return {@linkplain Date}
     */
    public static Date parseUTC(CharSequence cse) {
        if (StringUtil.isBlank(cse)) {
            return null;
        }
        final int length = cse.length();
        final String utcStr = StringUtil.str(cse);
        if (StringUtil.contains(utcStr, 'Z')) {
            if (length == DatePattern.UTC_PATTERN.length() - 4) {
                // 格式类似：2018-09-13T05:34:31Z，-4表示减去4个单引号的长度
                return parse(utcStr, DatePattern.UTC_PATTERN);
            }

            final int patternLen = DatePattern.UTC_MS_PATTERN.length();
            // 格式类似：2018-09-13T05:34:31.999Z，-4表示减去4个单引号的长度
            // -4 ~ -6范围表示匹配毫秒1~3位的情况
            if (length >= patternLen - 6 && length <= patternLen - 4) {
                return parse(utcStr, DatePattern.UTC_MS_PATTERN);
            }
        } else if (StringUtil.contains(utcStr, '+')) {
            if (length == DatePattern.UTC_WITH_ZONE_OFFSET_PATTERN.length() + 2 ||
                    length == DatePattern.UTC_WITH_ZONE_OFFSET_PATTERN.length() + 3) {
                // 格式类似：2018-09-13T05:34:31+0800 或 2018-09-13T05:34:31+08:00
                try {
                    return ThreadSafeDateParse.parse(utcStr, DatePattern.UTC_WITH_ZONE_OFFSET_PATTERN, TimeZone.getTimeZone("UTC"));
                } catch (ParseException e) {
                    throw new DateRuntimeException(e);
                }
            } else if (length == DatePattern.UTC_MS_WITH_ZONE_OFFSET_PATTERN.length() + 2 ||
                    length == DatePattern.UTC_MS_WITH_ZONE_OFFSET_PATTERN.length() + 3) {
                // 格式类似：2018-09-13T05:34:31.999+0800 或 2018-09-13T05:34:31.999+08:00
                return parse(utcStr, DatePattern.UTC_MS_WITH_ZONE_OFFSET_PATTERN);
            }
        } else if (length == DatePattern.UTC_SIMPLE_PATTERN.length() - 2) {
            // 格式类似：2018-09-13T05:34:31
            return parse(utcStr, DatePattern.UTC_SIMPLE_PATTERN);
        } else if (StringUtil.contains(utcStr, CharUtil.DOT)) {
            // 可能为：  2021-03-17T06:31:33.99
            return parse(utcStr, DatePattern.UTC_MS_SIMPLE_PATTERN);
        }
        throw new DateRuntimeException("No formatter for date string:" + utcStr);
    }

    // endregion

    // region Of Method

    /**
     * 根据年月日创建一个{@linkplain Date}对象
     *
     * @param year       年
     * @param month      月
     * @param dayOfMonth 日
     * @return 日期对象
     */
    public static Date ofUtilDate(final int year, final int month, final int dayOfMonth) {
        return ofUtilDate(year, month, dayOfMonth, 0);
    }

    /**
     * 根据年月日时创建一个{@linkplain Date}对象
     *
     * @param year       年
     * @param month      月
     * @param dayOfMonth 日
     * @param hour       小时
     * @return 日期对象
     */
    public static Date ofUtilDate(final int year, final int month, final int dayOfMonth, final int hour) {
        return ofUtilDate(year, month, dayOfMonth, hour, 0);
    }

    /**
     * 根据年月日时分创建一个{@linkplain Date}对象
     *
     * @param year       年
     * @param month      月
     * @param dayOfMonth 日
     * @param hour       小时
     * @param minute     分
     * @return 日期对象
     */
    public static Date ofUtilDate(final int year, final int month, final int dayOfMonth,
                                  final int hour, final int minute) {
        return ofUtilDate(year, month, dayOfMonth, hour, minute, 0);
    }

    /**
     * 根据年月日时分秒创建一个{@linkplain Date}对象
     *
     * @param year       年
     * @param month      月
     * @param dayOfMonth 日
     * @param hour       小时
     * @param minute     分
     * @param second     秒
     * @return 日期对象
     */
    public static Date ofUtilDate(final int year, final int month, final int dayOfMonth,
                                  final int hour, final int minute, final int second) {
        return Jdk8DateUtil.toDate(LocalDateTime.of(year, month, dayOfMonth, hour, minute, second));
    }

    //endregion

    /**
     * 计算两个日期之间的相差天数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 相差的天数
     */
    public static int betweenDays(Date start, Date end) {
        ObjectUtil.requireNonNull(start, "start");
        ObjectUtil.requireNonNull(end, "end");
        if (start.after(end)) {
            Date temp = start;
            start = end;
            end = temp;
        }
        return Jdk8DateUtil.betweenDays(start.toInstant(), end.toInstant());
    }

    /**
     * 判断是否是闰年，闰年规则：<a href="http://zh.wikipedia.org/wiki/%E9%97%B0%E5%B9%B4">闰年查看</a>
     * <pre>
     *     比如时间2014-05-12 22:10:00  DateUtil.isLeapYear(date); false
     * </pre>
     *
     * @param date 日期对象
     * @return 是否为闰年
     */
    public static boolean isLeapYear(final Date date) {
        return isLeapYear(getYear(date));
    }

    /**
     * 判断是否为闰年
     *
     * @param year 年,从1900年开始
     * @return 是否为闰年
     */
    public static boolean isLeapYear(final int year) {
        if (year < 1900) {
            return false;
        }

        //世纪闰年:能被400整除的为世纪闰年
        if (year % 400 == 0) {
            return true;
        }
        //普通闰年:能被4整除但不能被100整除的年份为普通闰年。
        return year % 4 == 0 && year % 100 != 0;
    }

    /**
     * 从日期中获取年份
     * <pre>
     *     比如时间2014-05-12 12:10:00  DateUtil.getYear(date); 2014
     * </pre>
     *
     * @param date 日期对象
     * @return 年份
     */
    public static int getYear(Date date) {
        ObjectUtil.requireNonNull(date, "date");
        return Jdk8DateUtil.of(date).getYear();
    }

    /**
     * 从日期中获取月份
     * <pre>
     *     比如时间2014-05-12 12:10:00  DateUtil.getMonth(date); 5
     * </pre>
     *
     * @param date 日期对象
     * @return 月份
     */
    public static int getMonth(Date date) {
        ObjectUtil.requireNonNull(date, "date");
        return Jdk8DateUtil.of(date).getMonthValue();
    }

    /**
     * 从日期中获取天
     * <pre>
     *     比如时间2014-05-12 12:10:00  DateUtil.getDay(date); 12
     * </pre>
     *
     * @param date 日期对象
     * @return 天
     */
    public static int getDay(Date date) {
        ObjectUtil.requireNonNull(date, "date");
        return Jdk8DateUtil.of(date).getDayOfMonth();
    }

    /**
     * 从日期中获取小时（24制）
     * <pre>
     *     比如时间2014-05-12 22:10:00  DateUtil.get24Hour(date); 22
     * </pre>
     *
     * @param date 日期对象
     * @return 小时（24制）
     */
    public static int get24Hour(Date date) {
        ObjectUtil.requireNonNull(date, "date");
        return Jdk8DateUtil.of(date).getHour();
    }

    /**
     * 从日期中获取小时（12制）
     * <pre>
     *     比如时间2014-05-12 22:10:00  DateUtil.get12Hour(date); 10
     * </pre>
     *
     * @param date 日期对象
     * @return 小时（12制）
     */
    public static int get12Hour(Date date) {
        return get24Hour(date) % 12;
    }

    /**
     * 从日期中获取分钟
     * <pre>
     *     比如时间2014-05-12 22:10:00  DateUtil.getMinute(date); 10
     * </pre>
     *
     * @param date 日期对象
     * @return 分钟
     */
    public static int getMinute(Date date) {
        ObjectUtil.requireNonNull(date, "date");
        return Jdk8DateUtil.of(date).getMinute();
    }

    /**
     * 从日期中获取秒数
     * <pre>
     *     比如时间2014-05-12 22:10:10  DateUtil.getSecond(date); 10
     * </pre>
     *
     * @param date 日期对象
     * @return 秒数
     */
    public static int getSecond(Date date) {
        ObjectUtil.requireNonNull(date, "date");
        return Jdk8DateUtil.of(date).getSecond();
    }

    /**
     * 从日期中获取秒数
     * <pre>
     *     比如时间2014-05-12 22:10:00.333  DateUtil.getSecond(date); 333
     * </pre>
     *
     * @param date 日期对象
     * @return 秒数
     */
    public static int getMilliSecond(Date date) {
        ObjectUtil.requireNonNull(date, "date");
        return (int) (date.getTime() % 1000L);
    }

    /**
     * 根据出生日期计算年龄,根据当前时间计算
     * <p>默认的日期格式{@code yyyyMMdd}</p>
     *
     * @param birthday 出生日期
     * @return 年龄
     */
    public static int age(String birthday) {
        return age(birthday, DatePattern.PURE_DATE_PATTERN);
    }

    /**
     * 根据出生日期计算年龄,根据当前时间计算
     *
     * @param birthday 出生日期
     * @param pattern  出生日期格式化
     * @return 年龄
     */
    public static int age(String birthday, String pattern) {
        return age(birthday, pattern, null);
    }

    /**
     * 根据出生日期和给定的日期时间计算年龄
     *
     * @param birthday     出生日期
     * @param pattern      出生日期格式化
     * @param comparedDate 给定的日期
     * @return 年龄
     */
    public static int age(String birthday, String pattern, Date comparedDate) {
        String fmt = StringUtil.getIfEmpty(pattern, DatePattern.PURE_DATE_PATTERN);
        return age(parse(birthday, fmt), comparedDate);
    }

    /**
     * 计算年龄,根据给定的日期
     *
     * @param birthday     出生日期
     * @param comparedDate 给定的日期
     * @return 年龄
     */
    public static int age(Date birthday, Date comparedDate) {
        Date endDate = ObjectUtil.getIfNull(comparedDate, now());
        if (birthday.after(comparedDate)) {
            throw new IllegalArgumentException("Birthday is after comparedDate");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        calendar.setTime(birthday);
        int age = year - calendar.get(Calendar.YEAR);
        int birthdayMonth = calendar.get(Calendar.MONTH);
        int birthdayDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        if (month < birthdayMonth) {
            age--;
        } else if (month == birthdayMonth) {
            if (dayOfMonth < birthdayDayOfMonth) {
                age--;
            }
        }
        return age;
    }

}
