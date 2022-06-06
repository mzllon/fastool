package tech.fastool.json.provider.gson.ser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import tech.fastool.core.lang.ObjectUtil;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link Instant}s.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class InstantSerializer implements JsonSerializer<Instant> {

    private DateTimeFormatter formatter;

    public InstantSerializer() {
        this(DateTimeFormatter.ISO_INSTANT);
    }

    public InstantSerializer(DateTimeFormatter formatter) {
        this.formatter = ObjectUtil.requireNonNull(formatter, "formatter == null");
    }

    @Override
    public JsonElement serialize(Instant instant, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(formatter.format(instant));
    }

    public static final InstantSerializer INSTANCE = new InstantSerializer();

}
