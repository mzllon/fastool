package tech.fastool.json.provider.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import tech.fastool.core.lang.ObjectUtil;
import tech.fastool.json.api.BaseJsonBuilder;
import tech.fastool.json.api.JsonAdapter;
import tech.fastool.json.api.annotation.JsonProviderName;
import tech.fastool.json.provider.gson.deser.*;
import tech.fastool.json.provider.gson.ser.*;

import java.time.*;

/**
 * 基于{@code Gson}的JSON构建器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
@JsonProviderName("gson")
public class GsonJsonBuilder extends BaseJsonBuilder {

    private final Gson gson;

    public GsonJsonBuilder() {
        this(createJson());
    }

    public GsonJsonBuilder(Gson gson) {
        this.gson = ObjectUtil.requireNonNull(gson, "gson == null");
    }

    /**
     * 构建对象
     *
     * @return {@linkplain JsonAdapter}
     */
    @Override
    public JsonAdapter build() {

        GsonJsonHandler gsonHandler = new GsonJsonHandler();
        gsonHandler.setGson(gson);

        JsonAdapter json = new JsonAdapter();
        json.setHandler(gsonHandler);
        return json;
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
