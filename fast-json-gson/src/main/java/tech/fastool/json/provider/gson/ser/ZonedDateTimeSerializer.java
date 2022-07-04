package tech.fastool.json.provider.gson.ser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import tech.fastool.core.date.DatePattern;
import tech.fastool.core.date.ZoneConstant;
import tech.fastool.core.lang.Objects;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link ZonedDateTime}s.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class ZonedDateTimeSerializer implements JsonSerializer<ZonedDateTime> {

    private final DateTimeFormatter formatter;

    public ZonedDateTimeSerializer() {
        this(DatePattern.NORMAL_DATETIME_FORMATTER);
    }

    public ZonedDateTimeSerializer(DateTimeFormatter formatter) {
        this.formatter = Objects.requireNonNull(formatter, "formatter == null");
        if (this.formatter.getZone() == null) {
            this.formatter.withZone(ZoneConstant.BEIJING_ZONE_OFFSET);
        }
    }

    @Override
    public JsonElement serialize(ZonedDateTime zonedDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        if (zonedDateTime == null) {
            return null;
        }
        return new JsonPrimitive(zonedDateTime.format(formatter));
    }

    public static final ZonedDateTimeSerializer INSTANCE = new ZonedDateTimeSerializer();

}
