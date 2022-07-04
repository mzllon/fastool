package tech.fastool.json.provider.gson.deser;

import com.google.gson.*;
import tech.fastool.core.date.DatePattern;
import tech.fastool.core.lang.Objects;
import tech.fastool.core.lang.StringUtil;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Deserializer for Java 8 temporal {@link LocalDate}s.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class LocalDateDeserializer implements JsonDeserializer<LocalDate> {

    private DateTimeFormatter formatter;

    public LocalDateDeserializer() {
        this(DatePattern.NORMAL_DATE_FORMATTER);
    }

    public LocalDateDeserializer(DateTimeFormatter formatter) {
        this.formatter = Objects.requireNonNull(formatter, "formatter == null");
    }

    @Override
    public LocalDate deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                String value = primitive.getAsString();
                if (StringUtil.isEmpty(value)) {
                    return null;
                }
                return LocalDate.parse(value, formatter);
            } else if (primitive.isNumber()) {
                return LocalDate.ofEpochDay(primitive.getAsLong());
            }
        }
        return null;
    }

    public static final LocalDateDeserializer INSTANCE = new LocalDateDeserializer();

}
