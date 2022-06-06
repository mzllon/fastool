package tech.fastool.json.provider.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import tech.fastool.core.date.DatePattern;
import tech.fastool.json.provider.jackson.deser.OffsetDateTimeDeserializer;
import tech.fastool.json.provider.jackson.deser.ZonedDateTimeDeserializer;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 内部的JSON工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class JacksonUtil {

    /**
     * 创建默认的{@linkplain JavaTimeModule}
     *
     * @return {@linkplain JavaTimeModule}
     */
    public static JavaTimeModule createJavaTimeModule() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DatePattern.NORMAL_DATE_FORMATTER));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DatePattern.NORMAL_DATE_FORMATTER));

        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DatePattern.NORMAL_DATETIME_FORMATTER));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DatePattern.NORMAL_DATETIME_FORMATTER));

        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DatePattern.NORMAL_TIME_FORMATTER));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DatePattern.NORMAL_TIME_FORMATTER));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        javaTimeModule.addSerializer(Year.class, new YearSerializer(formatter));
        javaTimeModule.addDeserializer(Year.class, new YearDeserializer(formatter));

        formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        javaTimeModule.addSerializer(YearMonth.class, new YearMonthSerializer(formatter));
        javaTimeModule.addDeserializer(YearMonth.class, new YearMonthDeserializer(formatter));

        formatter = DateTimeFormatter.ofPattern("MM-dd");
        javaTimeModule.addSerializer(MonthDay.class, new MonthDaySerializer(formatter));
        javaTimeModule.addDeserializer(MonthDay.class, new MonthDayDeserializer(formatter));

        javaTimeModule.addSerializer(OffsetDateTime.class, new OffsetDateTimeSerializer(
                OffsetDateTimeSerializer.INSTANCE, false, DatePattern.UTC_MS_WITH_ZONE_OFFSET_FORMATTER) {
        });
//        javaTimeModule.addDeserializer(OffsetDateTime.class, new InstantDeserializer<OffsetDateTime>(
//                InstantDeserializer.OFFSET_DATE_TIME, DatePattern.UTC_MS_WITH_ZONE_OFFSET_FORMATTER) {
//        });
        javaTimeModule.addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer());

        javaTimeModule.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer(DatePattern.UTC_MS_WITH_ZONE_OFFSET_FORMATTER));
//        javaTimeModule.addDeserializer(ZonedDateTime.class,
//                new InstantDeserializer<ZonedDateTime>(InstantDeserializer.ZONED_DATE_TIME, DatePattern.UTC_MS_WITH_ZONE_OFFSET_FORMATTER) {
//                });
        javaTimeModule.addDeserializer(ZonedDateTime.class, new ZonedDateTimeDeserializer());

        return javaTimeModule;
    }

    /**
     * 创建默认的{@linkplain  ObjectMapper}
     *
     * @return {@linkplain  ObjectMapper}
     */
    public static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(createJavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 过滤transient
        objectMapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
            @Override
            public boolean hasIgnoreMarker(AnnotatedMember annotatedMember) {
                return Modifier.isTransient(annotatedMember.getMember().getModifiers()) || super.hasIgnoreMarker(annotatedMember);
            }
        });
        return objectMapper;
    }

    /**
     * 判断类型是否为Jackson类型
     *
     * @param type 被检测的类型
     * @return 是否
     */
    public static boolean isJacksonJavaType(Type type) {
        return type instanceof JavaType;
    }

    /**
     * 将{@linkplain Type}转为{@linkplain JavaType}
     *
     * @param type Java类型
     * @return Jackson类型
     */
    public static JavaType toJavaType(Type type) {
        return (JavaType) type;
    }

    /**
     * 是否为类
     *
     * @param type 被检测的类型
     * @return 是否
     */
    public static boolean isClass(Type type) {
        return type instanceof Class;
    }

    /**
     * judge a type is a ParameterizedType
     *
     * @param type 被检测的类型
     * @return 是否
     */
    public static boolean isParameterizedType(Type type) {
        return (type instanceof ParameterizedType);
    }

}
