package tech.fastool.core.date;

import tech.fastool.core.lang.Objects;
import tech.fastool.core.lang.Strings;
import tech.fastool.core.lang.regex.Regexes;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.*;
import java.util.Date;

/**
 * JDK8以上的日期工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class Jdk8DateUtil {

    // region ============ Create LocalDateTime ============

    /**
     * 当前日期时间，默认时区
     *
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * 当前日期，默认时区
     *
     * @return {@link LocalDate}
     */
    public static LocalDate nowDate() {
        return LocalDate.now();
    }

    /**
     * 当前时间，默认时区
     *
     * @return {@link LocalTime}
     */
    public static LocalTime nowTime() {
        return LocalTime.now();
    }

    /**
     * {@link Date}转{@link LocalDateTime}，使用默认时区
     *
     * @param date Date对象
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime of(Date date) {
        if (date == null) {
            return null;
        }
        return of(date.toInstant());
    }

    /**
     * {@link Instant}转{@link LocalDateTime}，使用默认时区
     *
     * @param instant {@link Instant}
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime of(Instant instant) {
        return of(instant, ZoneConstant.SYSTEM_DEFAULT);
    }

    /**
     * {@link Instant}转{@link LocalDateTime}
     *
     * @param instant {@link Instant}
     * @param zoneId  时区
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime of(Instant instant, ZoneId zoneId) {
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, Objects.getIfNull(zoneId, ZoneId.systemDefault()));
    }

    /**
     * 毫秒数转日期时间对象{@linkplain LocalDateTime}
     *
     * @param epochMilli 毫秒数，从1970-01-01 00:00:00开始
     * @return 日期时间对象
     */
    public static LocalDateTime of(final long epochMilli) {
        Objects.isTrue(epochMilli >= 0, "epochMilli > 0");
        return of(Instant.ofEpochMilli(epochMilli));
    }

    /**
     * {@link TemporalAccessor}转{@link LocalDateTime}，使用默认时区
     *
     * @param temporalAccessor {@link TemporalAccessor}
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime of(TemporalAccessor temporalAccessor) {
        if (null == temporalAccessor) {
            return null;
        } else if (temporalAccessor instanceof LocalDate) {
            return ((LocalDate) temporalAccessor).atStartOfDay();
        } else if (temporalAccessor instanceof LocalDateTime) {
            return (LocalDateTime) temporalAccessor;
        } else {
            return LocalDateTime.of(
                    get(temporalAccessor, ChronoField.YEAR), get(temporalAccessor, ChronoField.MONTH_OF_YEAR),
                    get(temporalAccessor, ChronoField.DAY_OF_MONTH), get(temporalAccessor, ChronoField.HOUR_OF_DAY),
                    get(temporalAccessor, ChronoField.MINUTE_OF_HOUR),
                    get(temporalAccessor, ChronoField.SECOND_OF_MINUTE),
                    get(temporalAccessor, ChronoField.NANO_OF_SECOND));
        }
    }

    // endregion

    // region ============ Parse ============

    /**
     * 解析日期时间字符串为{@link LocalDateTime}，仅支持yyyy-MM-dd'T'HH:mm:ss格式，例如：2007-12-03T10:15:30
     *
     * @param text 日期时间字符串
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime parse(CharSequence text) {
        return parse(text, (DateTimeFormatter) null);
    }

    /**
     * 解析日期时间字符串为{@link LocalDateTime}，格式支持日期时间、日期、时间
     *
     * @param text      日期时间字符串
     * @param formatter 日期格式化器，预定义的格式见：{@link DateTimeFormatter}
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime parse(CharSequence text, DateTimeFormatter formatter) {
        if (Strings.isBlank(text)) {
            return null;
        }
        if (formatter == null) {
            return LocalDateTime.parse(text);
        }
        return of(formatter.parse(text));
    }

    /**
     * 解析日期时间字符串为{@link LocalDate}，格式支持日期时间、日期、时间
     *
     * @param text      日期时间字符串
     * @param formatter 日期格式化器，预定义的格式见：{@link DateTimeFormatter}
     * @return {@link LocalDate}
     */
    public static LocalDate parseToDate(CharSequence text, DateTimeFormatter formatter) {
        LocalDateTime ldt = parse(text, formatter);
        return (null == ldt) ? null : ldt.toLocalDate();
    }

    /**
     * 解析日期时间字符串为{@link LocalDateTime}
     *
     * @param text    日期时间字符串
     * @param pattern 日期格式，类似于yyyy-MM-dd HH:mm:ss
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime parse(CharSequence text, String pattern) {
        if (Strings.isBlank(text)) {
            return null;
        }
        DateTimeFormatter formatter = null;
        if (Strings.hasText(pattern)) {
            // 修复yyyyMMddHHmmssSSS格式不能解析的问题
            //see https://stackoverflow.com/questions/22588051/is-java-time-failing-to-parse-fraction-of-second
            if (Strings.startsWithIgnoreCase(pattern, DatePattern.PURE_DATETIME_PATTERN)) {
                String fraction = Strings.deletePrefix(pattern, DatePattern.PURE_DATETIME_PATTERN);
                if (Regexes.isMatch("[S]{1,2}", fraction)) {
                    //将yyyyMMddHHmmssS、yyyyMMddHHmmssSS的日期统一替换为yyyyMMddHHmmssSSS格式，用0补
                    text += Strings.repeat('0', 3 - fraction.length());
                }
                formatter = new DateTimeFormatterBuilder()
                        .append(DatePattern.PURE_DATETIME_FORMATTER)
                        .appendValue(ChronoField.MILLI_OF_SECOND, 3)
                        .toFormatter();
            } else {
                formatter = DatePattern.ofPattern(pattern);
            }
        }
        return parse(text, formatter);
    }

    /**
     * 解析日期时间字符串为{@link LocalDate}
     *
     * @param text    日期时间字符串
     * @param pattern 日期格式，类似于yyyy-MM-dd
     * @return {@link LocalDate}
     */

    public static LocalDate parseToDate(CharSequence text, String pattern) {
        LocalDateTime ldt = parse(text, pattern);
        return (ldt == null) ? null : ldt.toLocalDate();
    }

    // endregion


    // region ============ Format ============

    /**
     * 对当前日期时间格式化，格式为：yyyy-MM-dd HH:mm:ss
     *
     * @return 格式化后的字符串
     */
    public static String format() {
        return format(LocalDateTime.now());
    }

    /**
     * 对当前日期时间采用指定格式格式化
     *
     * @param pattern 格式，为空时默认采用格式：yyyy-MM-dd HH:mm:ss
     * @return 格式化后的字符串
     */
    public static String format(String pattern) {
        return format(LocalDateTime.now(), pattern);
    }

    /**
     * 对当前日期时间采用指定格式化器格式化
     *
     * @param formatter 格式化器，为空默认采用 yyyy-MM-dd HH:mm:ss
     * @return 格式化后的字符串
     */
    public static String format(DateTimeFormatter formatter) {
        return format(LocalDateTime.now(), formatter);
    }

    /**
     * 格式化日期时间为yyyy-MM-dd HH:mm:ss格式
     *
     * @param localDateTime 日期时间
     * @return 格式化后的字符串或{@code null}
     */
    public static String format(LocalDateTime localDateTime) {
        return format(localDateTime, DatePattern.NORMAL_DATETIME_FORMATTER);
    }

    /**
     * 按指定格式格式化日期
     *
     * @param localDateTime 日期
     * @param pattern       格式，为空时采用yyyy-MM-dd HH:mm:ss
     * @return 格式化后的日期字符串或{@code null}
     */
    public static String format(LocalDateTime localDateTime, String pattern) {
        if (localDateTime == null) {
            return null;
        }
        return format(localDateTime, Strings.isBlank(pattern) ? DatePattern.NORMAL_DATETIME_FORMATTER : DatePattern.ofPattern(pattern));
    }

    /**
     * 按指定格式格式化日期
     *
     * @param localDateTime 日期
     * @param formatter     格式，为空时采用yyyy-MM-dd HH:mm:ss
     * @return 格式化后的日期字符串或{@code null}
     */
    public static String format(LocalDateTime localDateTime, DateTimeFormatter formatter) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.format(Objects.getIfNull(formatter, DatePattern.NORMAL_DATETIME_FORMATTER));
    }

    /**
     * 格式化日期时间为yyyy-MM-dd格式
     *
     * @param localDate 日期
     * @return 格式化后的日期字符串或{@code null}
     */
    public static String format(LocalDate localDate) {
        return format(localDate, DatePattern.NORMAL_DATE_FORMATTER);
    }

    /**
     * 按指定格式格式化日期
     *
     * @param localDate 日期
     * @param pattern   格式，为空时采用yyyy-MM-dd
     * @return 格式化后的日期字符串或{@code null}
     */
    public static String format(LocalDate localDate, String pattern) {
        if (localDate == null) {
            return null;
        }
        return format(localDate, Strings.isBlank(pattern) ? DatePattern.NORMAL_DATE_FORMATTER : DatePattern.ofPattern(pattern));
    }

    /**
     * 按指定格式格式化日期
     *
     * @param localDate 日期
     * @param formatter 格式化器，为空时采用yyyy-MM-dd
     * @return 格式化后的日期字符串或{@code null}
     */
    public static String format(LocalDate localDate, DateTimeFormatter formatter) {
        if (localDate == null) {
            return null;
        }
        return localDate.format(Objects.getIfNull(formatter, DatePattern.NORMAL_DATE_FORMATTER));
    }

    // endregion

    /**
     * 将{@code LocalDateTime}转为时间戳
     *
     * @param dateTime 时间
     * @return 时间戳
     */
    public static long toEpochMilli(LocalDateTime dateTime) {
        return toInstant(dateTime).toEpochMilli();
    }

    /**
     * p
     * 将{@linkplain LocalDateTime}转为{@linkplain Instant}
     * <p>采用北京时区作为转换默认时区</p>
     *
     * @param localDateTime 日期时间
     * @return 时刻
     */
    public static Instant toInstant(LocalDateTime localDateTime) {
        return toInstant(localDateTime, null);
    }

    /**
     * 将{@linkplain LocalDateTime}转为{@linkplain Instant}
     *
     * @param localDateTime 日期时间
     * @param zoneId        指定时区
     * @return 时刻
     */
    public static Instant toInstant(LocalDateTime localDateTime, ZoneId zoneId) {
        if (Objects.isAnyNull(localDateTime, zoneId)) {
            return null;
        }
        return localDateTime.atZone(Objects.getIfNull(zoneId, ZoneConstant.ASIA_SHANGHAI)).toInstant();
    }

    /**
     * 将{@linkplain ZonedDateTime}转为{@linkplain Instant}
     *
     * @param zdt {@linkplain ZonedDateTime}对象
     * @return {@linkplain Instant}对象
     */
    public static Instant toInstant(ZonedDateTime zdt) {
        return zdt.toInstant();
    }

    /**
     * 将{@linkplain TemporalAccessor}转为{@linkplain Instant}
     *
     * @param ta {@linkplain TemporalAccessor}
     * @return {@linkplain Instant}
     */
    public static Instant toInstant(TemporalAccessor ta) {
        if (ta == null) {
            return null;
        } else if (ta instanceof Instant) {
            return (Instant) ta;
        } else if (ta instanceof LocalDateTime) {
            return toInstant((LocalDateTime) ta);
        } else if (ta instanceof ZonedDateTime) {
            return toInstant((ZonedDateTime) ta);
        } else if (ta instanceof OffsetDateTime) {
            return ((OffsetDateTime) ta).toInstant();
        } else if (ta instanceof LocalDate) {
            return toInstant(((LocalDate) ta).atStartOfDay());
        } else if (ta instanceof LocalTime) {
            return toInstant(((LocalTime) ta).atDate(LocalDate.now()));
        } else if (ta instanceof OffsetTime) {
            return ((OffsetTime) ta).atDate(LocalDate.now()).toInstant();
        } else {
            return Instant.from(ta);
        }
    }

    /**
     * 将{@linkplain LocalDateTime}转为{@linkplain Date}，采用默认时区
     *
     * @param localDateTime 日期时间
     * @return {@linkplain Date}
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return toDate(localDateTime, null);
    }

    /**
     * 将{@linkplain LocalDateTime}转为{@linkplain Date}
     *
     * @param localDateTime 日期时间
     * @param zoneOffset    时区
     * @return {@linkplain Date}
     */
    public static Date toDate(LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.toInstant(Objects.getIfNull(zoneOffset, ZoneConstant.DEFAULT_ZONE_OFFSET)));
    }


    /**
     * 判断是否是闰年，闰年规则：<a href="http://zh.wikipedia.org/wiki/%E9%97%B0%E5%B9%B4">闰年查看</a>
     * <pre>
     *     比如时间2021-05-15 22:10:00  LocalDateTimeUtil.isLeapYear(localDateTime); false
     * </pre>
     *
     * @param localDateTime 日期对象
     * @return 是否为闰年
     */
    public static boolean isLeapYear(LocalDateTime localDateTime) {
        return (localDateTime != null && isLeapYear(localDateTime.toLocalDate()));
    }

    /**
     * 判断是否是闰年，闰年规则：<a href="http://zh.wikipedia.org/wiki/%E9%97%B0%E5%B9%B4">闰年查看</a>
     * <pre>
     *     比如时间2021-05-15  LocalDateTimeUtil.isLeapYear(localDate); false
     * </pre>
     *
     * @param localDate 日期对象
     * @return 是否为闰年
     */
    public static boolean isLeapYear(LocalDate localDate) {
        if (localDate == null) {
            return false;
        }
        return localDate.isLeapYear();
    }

    /**
     * 计算两个日期之间的相差天数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 相差的天数
     */
    public static int betweenDays(Temporal start, Temporal end) {
        return (int) ChronoUnit.DAYS.between(start, end);
    }

    /**
     * 判定日期是否在日期指定范围内，起始日期和结束日期可以互换
     *
     * @param date  被检查的日期
     * @param begin 起始日期（包含）
     * @param end   结束日期（包含）
     * @return 是否在范围内
     */
    public static boolean isIn(LocalDateTime date, LocalDateTime begin, LocalDateTime end) {
        if (date == null || begin == null || end == null) {
            return false;
        }
        LocalDateTime start = begin, finish = end;
        if (begin.isAfter(end)) {
            start = end;
            finish = begin;
        }
        return start.isBefore(date) && date.isBefore(finish);
    }

    /**
     * 判定日期是否在日期指定范围内，起始日期和结束日期可以互换
     *
     * @param date  被检查的日期
     * @param begin 起始日期（包含）
     * @param end   结束日期（包含）
     * @return 是否在范围内
     */
    public static boolean isIn(LocalDate date, LocalDate begin, LocalDate end) {
        if (date == null || begin == null || end == null) {
            return false;
        }
        LocalDate start = begin, finish = end;
        if (begin.isAfter(end)) {
            start = end;
            finish = begin;
        }
        return start.isBefore(date) && date.isBefore(finish);
    }

    /**
     * 判定日期是否在日期指定范围内，起始日期和结束日期可以互换
     *
     * @param date  被检查的日期
     * @param begin 起始日期（包含）
     * @param end   结束日期（包含）
     * @return 是否在范围内
     */
    public static boolean isIn(LocalTime date, LocalTime begin, LocalTime end) {
        if (date == null || begin == null || end == null) {
            return false;
        }
        LocalTime start = begin, finish = end;
        if (begin.isAfter(end)) {
            start = end;
            finish = begin;
        }
        return start.isBefore(date) && date.isBefore(finish);
    }

    /**
     * 安全获取时间的某个属性，属性不存在返回0
     *
     * @param accessor 需要获取的时间对象
     * @param field    需要获取的属性
     * @return 时间的值，如果无法获取则默认为 0
     */
    public static int get(TemporalAccessor accessor, TemporalField field) {
        return accessor.isSupported(field) ? accessor.get(field) : ((int) field.range().getMinimum());
    }

}
