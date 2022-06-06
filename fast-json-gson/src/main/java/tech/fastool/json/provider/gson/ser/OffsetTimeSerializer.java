package tech.fastool.json.provider.gson.ser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import tech.fastool.core.date.DatePattern;
import tech.fastool.core.lang.ObjectUtil;

import java.lang.reflect.Type;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link OffsetTime}s.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class OffsetTimeSerializer implements JsonSerializer<OffsetTime> {

    private DateTimeFormatter formatter;

    public OffsetTimeSerializer() {
        this(DatePattern.NORMAL_TIME_FORMATTER);
    }

    public OffsetTimeSerializer(DateTimeFormatter formatter) {
        this.formatter = ObjectUtil.requireNonNull(formatter, "formatter == null");
    }

    @Override
    public JsonElement serialize(OffsetTime offsetTime, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(offsetTime.format(formatter));
    }

    public static final OffsetTimeSerializer INSTANCE = new OffsetTimeSerializer();

}
