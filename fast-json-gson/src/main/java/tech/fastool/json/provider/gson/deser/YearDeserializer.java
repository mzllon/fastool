package tech.fastool.json.provider.gson.deser;

import com.google.gson.*;
import tech.fastool.core.lang.Objects;
import tech.fastool.core.lang.Strings;

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
public class YearDeserializer implements JsonDeserializer<Year> {

    private DateTimeFormatter formatter;

    public YearDeserializer() {
        this(DateTimeFormatter.ofPattern("yyyy"));
    }

    public YearDeserializer(DateTimeFormatter formatter) {
        this.formatter = Objects.requireNonNull(formatter, "formatter == null");
    }

    @Override
    public Year deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonPrimitive primitive = element.getAsJsonPrimitive();
        if (primitive.isString()) {
            String value = primitive.getAsString();
            if (Strings.isEmpty(value)) {
                return null;
            }
            if (formatter == null) {
                return Year.parse(value);
            }
            return Year.parse(value, formatter);
        } else if (primitive.isNumber()) {
            return Year.of(primitive.getAsInt());
        }
        return null;
    }

    public static final YearDeserializer INSTANCE = new YearDeserializer();

}
