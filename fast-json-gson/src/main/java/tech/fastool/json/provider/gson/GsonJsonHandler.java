package tech.fastool.json.provider.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.fastool.core.lang.ObjectUtil;
import tech.fastool.json.api.JsonHandler;
import tech.fastool.json.api.JsonRuntimeException;
import tech.fastool.json.provider.gson.deser.*;
import tech.fastool.json.provider.gson.ser.*;

import java.io.Reader;
import java.lang.reflect.Type;
import java.time.*;

/**
 * 基于{@code Gson}的JSON处理器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class GsonJsonHandler implements JsonHandler {

    private final Gson gson;

    public GsonJsonHandler() {
        this(createJson());
    }

    public GsonJsonHandler(Gson gson) {
        this.gson = ObjectUtil.requireNonNull(gson, "gson == null");
    }

    /**
     * 将Java对象序列化为JSON字符串
     *
     * @param src     Java对象
     * @param typeOfT 类型
     * @return JSON字符串
     * @throws JsonRuntimeException 序列化出现异常
     */
    @Override
    public String serialize(@NotNull Object src, @Nullable Type typeOfT) throws JsonRuntimeException {
        return (typeOfT == null) ? gson.toJson(src) : gson.toJson(src, typeOfT);
    }

    /**
     * 将JSON字符串放序列化为Java对象
     *
     * @param json    JSON字符串
     * @param typeOfT Java类型
     * @return Java对象
     * @throws JsonRuntimeException 反序列化出现异常
     */
    @Override
    public <T> T deserialize(@NotNull String json, @NotNull Type typeOfT) throws JsonRuntimeException {
        return gson.fromJson(json, typeOfT);
    }

    @Override
    public <T> T deserialize(@NotNull Reader reader, @NotNull Type typeOfT) throws JsonRuntimeException {
        return null;
    }

    /**
     * 创建支持JSR310的gson处理
     *
     * @return
     */
    public static Gson createJson() {
        GsonBuilder builder = new GsonBuilder();

        // 序列化
        builder.registerTypeAdapter(Instant.class, InstantSerializer.INSTANCE);

        builder.registerTypeAdapter(LocalDate.class, LocalDateSerializer.INSTANCE);
        builder.registerTypeAdapter(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE);
        builder.registerTypeAdapter(LocalTime.class, LocalTimeSerializer.INSTANCE);
        builder.registerTypeAdapter(Year.class, YearSerializer.INSTANCE);
        builder.registerTypeAdapter(YearMonth.class, YearMonthSerializer.INSTANCE);
        builder.registerTypeAdapter(MonthDay.class, MonthDaySerializer.INSTANCE);

        builder.registerTypeAdapter(OffsetDateTime.class, OffsetDateTimeSerializer.INSTANCE);
        builder.registerTypeAdapter(OffsetTime.class, OffsetTimeSerializer.INSTANCE);

        builder.registerTypeAdapter(ZonedDateTime.class, ZonedDateTimeSerializer.INSTANCE);

        // 反序列化
        builder.registerTypeAdapter(LocalDate.class, LocalDateDeserializer.INSTANCE);
        builder.registerTypeAdapter(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
        builder.registerTypeAdapter(LocalTime.class, LocalTimeDeserializer.INSTANCE);

        builder.registerTypeAdapter(Year.class, YearDeserializer.INSTANCE);
        builder.registerTypeAdapter(YearMonth.class, YearMonthDeserializer.INSTANCE);
        builder.registerTypeAdapter(MonthDay.class, MonthDayDeserializer.INSTANCE);

        builder.registerTypeAdapter(OffsetTime.class, OffsetTimeDeserializer.INSTANCE);
        builder.registerTypeAdapter(ZonedDateTime.class, ZonedDateTimeDeserializer.INSTANCE);

        return builder.create();
    }

}
