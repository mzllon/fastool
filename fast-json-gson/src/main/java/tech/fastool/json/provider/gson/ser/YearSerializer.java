package tech.fastool.json.provider.gson.ser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import tech.fastool.core.lang.Objects;

import java.lang.reflect.Type;
import java.time.Year;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link Year}s.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class YearSerializer implements JsonSerializer<Year> {

    private final DateTimeFormatter formatter;

    public YearSerializer() {
        this(DateTimeFormatter.ofPattern("yyyy"));
    }

    public YearSerializer(DateTimeFormatter formatter) {
        this.formatter = Objects.requireNonNull(formatter, "formatter == null");
    }

    @Override
    public JsonElement serialize(Year year, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(year.format(formatter));
    }

    public static final YearSerializer INSTANCE = new YearSerializer();

}
