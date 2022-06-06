package tech.fastool.json.provider.gson.ser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import tech.fastool.core.date.DatePattern;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link LocalDate}s.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class LocalDateSerializer implements JsonSerializer<LocalDate> {

    private final DateTimeFormatter formatter;

    public LocalDateSerializer() {
        this(DatePattern.NORMAL_DATE_FORMATTER);
    }

    public LocalDateSerializer(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }


    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) {
            return null;
        }
        return new JsonPrimitive(src.format(formatter));
    }

    public static final LocalDateSerializer INSTANCE = new LocalDateSerializer();

}
