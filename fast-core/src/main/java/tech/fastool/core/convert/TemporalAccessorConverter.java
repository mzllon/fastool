package tech.fastool.core.convert;

import tech.fastool.core.date.DatePattern;
import tech.fastool.core.date.Dates;
import tech.fastool.core.date.Jdk8DateUtil;
import tech.fastool.core.date.ZoneConstant;
import tech.fastool.core.lang.Objects;
import tech.fastool.core.lang.Strings;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;

/**
 * JDK8中日期类的转换器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class TemporalAccessorConverter extends AbstractConverter<TemporalAccessor> {

    /**
     * use serialVersionUID from JDK 1.0.2 for interoperability
     */
    private static final long serialVersionUID = 2021L;

    /**
     * 日期格式化
     */
    private String format;

    /**
     * 目标类型
     */
    private final Class<? extends TemporalAccessor> targetClass;

    public TemporalAccessorConverter(Class<? extends TemporalAccessor> targetClass) {
        this.targetClass = targetClass;
    }

    public TemporalAccessorConverter(Class<? extends TemporalAccessor> targetClass, String format) {
        this(targetClass);
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * 内部转换器，被 {@link AbstractConverter#handleInternal(Object)} 调用，实现基本转换逻辑<br>
     * 内部转换器转换后如果转换失败可以做如下操作，处理结果都为返回默认值：
     *
     * <pre>
     * 1、返回{@code null}
     * 2、抛出一个{@link RuntimeException}异常
     * </pre>
     *
     * @param value 值
     * @return 转换后的类型
     */
    @Override
    protected TemporalAccessor handleInternal(Object value) {
        if (value instanceof Long) {
            return parseFromInstant(Instant.ofEpochMilli((Long) value), null);
        } else if (value instanceof TemporalAccessor) {
            return parseByTemporalAccessor(((TemporalAccessor) value));
        } else if (value instanceof Date) {
            return parseFromInstant(((Date) value).toInstant(), null);
        } else if (value instanceof Calendar) {
            Calendar calendar = (Calendar) value;
            return parseFromInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId());
        } else {
            return parseFromStr(execToStr(value));
        }
    }

    private TemporalAccessor parseFromStr(String str) {
        if (Strings.isEmpty(str)) {
            return null;
        }
        Instant instant;
        ZoneId zoneId;
        if (this.format != null) {
            DateTimeFormatter dtf = DatePattern.ofPattern(this.format);
            instant = dtf.parse(str, Instant::from);
            zoneId = dtf.getZone();
        } else {
            Dates.parse(str);
        }
        //TODO 暂未实现
        return null;
    }

    /**
     * 将{@linkplain Instant}转为{@linkplain TemporalAccessor}
     *
     * @param instant {@code Instant}实例对象
     * @param zoneId  时区ID
     * @return 目标时间对象
     */
    private TemporalAccessor parseFromInstant(Instant instant, ZoneId zoneId) {
        if (targetClass == Instant.class) {
            return instant;
        }
        zoneId = Objects.getIfNull(zoneId, ZoneConstant.DEFAULT_ZONE_OFFSET);
        if (targetClass == LocalDateTime.class) {
            return instant.atZone(zoneId).toLocalDateTime();
        } else if (targetClass == LocalDate.class) {
            return instant.atZone(zoneId).toLocalDate();
        } else if (targetClass == LocalTime.class) {
            return instant.atZone(zoneId).toLocalTime();
        } else if (targetClass == ZonedDateTime.class) {
            return instant.atZone(zoneId);
        } else if (targetClass == OffsetDateTime.class) {
            return instant.atZone(zoneId).toOffsetDateTime();
        } else if (targetClass == OffsetTime.class) {
            return OffsetTime.ofInstant(instant, zoneId);
        } else {
            return null;
        }
    }

    /**
     * 将{@linkplain TemporalAccessor}转为{@linkplain TemporalAccessor}
     *
     * @param sourceTa 原时间对象
     * @return 目标时间对象
     */
    private TemporalAccessor parseByTemporalAccessor(TemporalAccessor sourceTa) {
        if (sourceTa instanceof LocalDateTime) {
            return parseFromLocalDateTime((LocalDateTime) sourceTa);
        } else if (sourceTa instanceof ZonedDateTime) {
            return parseFromZonedDateTime((ZonedDateTime) sourceTa);
        }
        return parseFromInstant(Jdk8DateUtil.toInstant(sourceTa), null);
    }

    /**
     * 将{@linkplain LocalDateTime}转为{@linkplain TemporalAccessor}时间对象
     *
     * @param ldt {@linkplain LocalDateTime}对象
     * @return 目标时间对象
     */
    private TemporalAccessor parseFromLocalDateTime(LocalDateTime ldt) {
        if (targetClass == Instant.class) {
            return Jdk8DateUtil.toInstant(ldt);
        } else if (targetClass == LocalDate.class) {
            return ldt.toLocalDate();
        } else if (targetClass == LocalTime.class) {
            return ldt.toLocalTime();
        } else if (targetClass == LocalDateTime.class) {
            return ldt;
        } else if (targetClass == ZonedDateTime.class) {
            return ldt.atZone(ZoneConstant.DEFAULT_ZONE_OFFSET);
        } else if (targetClass == OffsetDateTime.class) {
            return ldt.atZone(ZoneConstant.DEFAULT_ZONE_OFFSET).toOffsetDateTime();
        } else if (targetClass == OffsetTime.class) {
            return ldt.atZone(ZoneConstant.DEFAULT_ZONE_OFFSET).toOffsetDateTime().toOffsetTime();
        }
        return null;
    }

    /**
     * 将{@linkplain ZonedDateTime}转为{@linkplain TemporalAccessor}
     *
     * @param zdt {@linkplain ZonedDateTime}对象
     * @return {@linkplain TemporalAccessor}时间对象
     */
    private TemporalAccessor parseFromZonedDateTime(ZonedDateTime zdt) {
        if (targetClass == Instant.class) {
            return Jdk8DateUtil.toInstant(zdt);
        } else if (targetClass == LocalDate.class) {
            return zdt.toLocalDate();
        } else if (targetClass == LocalTime.class) {
            return zdt.toLocalTime();
        } else if (targetClass == LocalDateTime.class) {
            return zdt.toLocalDateTime();
        } else if (targetClass == ZonedDateTime.class) {
            return zdt;
        } else if (targetClass == OffsetDateTime.class) {
            return zdt.toOffsetDateTime();
        } else if (targetClass == OffsetTime.class) {
            return zdt.toOffsetDateTime().toOffsetTime();
        }
        return null;
    }

    /**
     * 返回实际目标类型
     *
     * @return 实际目标类型
     */
    @Override
    public Class<TemporalAccessor> getTargetClass() {
        return (Class<TemporalAccessor>) targetClass;
    }

}
