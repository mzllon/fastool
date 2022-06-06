package tech.fastool.json.provider.gson.ser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link MonthDay}s.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class MonthDaySerializer implements JsonSerializer<MonthDay> {

    private final DateTimeFormatter formatter;

    public MonthDaySerializer() {
        this(DateTimeFormatter.ofPattern("MM-dd"));
    }

    public MonthDaySerializer(DateTimeFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public JsonElement serialize(MonthDay monthDay, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(monthDay.format(formatter));
    }

    public static final MonthDaySerializer INSTANCE = new MonthDaySerializer();

}
