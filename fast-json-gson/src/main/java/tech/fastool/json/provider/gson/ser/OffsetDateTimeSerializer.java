package tech.fastool.json.provider.gson.ser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import tech.fastool.core.date.DatePattern;

import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 OffsetDateTime
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class OffsetDateTimeSerializer implements JsonSerializer<OffsetDateTime> {

    private final DateTimeFormatter formatter;

    public OffsetDateTimeSerializer() {
        this(DatePattern.UTC_MS_WITH_ZONE_OFFSET_FORMATTER);
    }

    public OffsetDateTimeSerializer(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }


    @Override
    public JsonElement serialize(OffsetDateTime offsetDateTime, Type type, JsonSerializationContext jsonSerializationContext) {

        return new JsonPrimitive(offsetDateTime.format(formatter));
    }

    public static final OffsetDateTimeSerializer INSTANCE = new OffsetDateTimeSerializer();

}
