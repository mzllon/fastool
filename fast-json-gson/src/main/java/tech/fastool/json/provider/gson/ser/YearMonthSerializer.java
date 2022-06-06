package tech.fastool.json.provider.gson.ser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import tech.fastool.core.lang.ObjectUtil;

import java.lang.reflect.Type;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link YearMonth}s.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class YearMonthSerializer implements JsonSerializer<YearMonth> {

    private final DateTimeFormatter formatter;

    public YearMonthSerializer() {
        this(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    public YearMonthSerializer(DateTimeFormatter formatter) {
        this.formatter = ObjectUtil.requireNonNull(formatter, "formatter == null");
    }

    @Override
    public JsonElement serialize(YearMonth yearMonth, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(yearMonth.format(formatter));
    }

    public static final YearMonthSerializer INSTANCE = new YearMonthSerializer();

}
