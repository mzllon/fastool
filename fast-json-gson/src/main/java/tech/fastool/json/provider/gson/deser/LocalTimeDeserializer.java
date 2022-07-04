package tech.fastool.json.provider.gson.deser;

import com.google.gson.*;
import tech.fastool.core.date.DatePattern;
import tech.fastool.core.lang.Objects;
import tech.fastool.core.lang.StringUtil;
import tech.fastool.json.provider.gson.ser.LocalTimeSerializer;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Deserializer for Java 8 temporal {@link LocalTime}s.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class LocalTimeDeserializer implements JsonDeserializer<LocalTime> {

    private DateTimeFormatter formatter;

    public LocalTimeDeserializer() {
        this(DatePattern.NORMAL_TIME_FORMATTER);
    }

    public LocalTimeDeserializer(DateTimeFormatter formatter) {
        this.formatter = Objects.requireNonNull(formatter, "formatter == null");
    }

    @Override
    public LocalTime deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                String value = primitive.getAsString();
                if (StringUtil.isEmpty(value)) {
                    return null;
                }
                return LocalTime.parse(value, formatter);
            }
        }
        return null;
    }

    public static final LocalTimeSerializer INSTANCE = new LocalTimeSerializer();

}
