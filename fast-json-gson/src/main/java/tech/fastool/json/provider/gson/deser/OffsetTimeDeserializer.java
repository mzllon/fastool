package tech.fastool.json.provider.gson.deser;

import com.google.gson.*;
import tech.fastool.core.date.DatePattern;
import tech.fastool.core.date.ZoneConstant;
import tech.fastool.core.lang.Objects;
import tech.fastool.core.lang.StringUtil;

import java.lang.reflect.Type;
import java.time.OffsetTime;
import java.time.format.DateTimeFormatter;

/**
 * Deserializer for Java 8 temporal {@link OffsetTime}s.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class OffsetTimeDeserializer implements JsonDeserializer<OffsetTime> {

    private DateTimeFormatter formatter;

    public OffsetTimeDeserializer() {
        this(DatePattern.NORMAL_TIME_FORMATTER.withZone(ZoneConstant.BEIJING_ZONE_OFFSET));
    }

    public OffsetTimeDeserializer(DateTimeFormatter formatter) {
        this.formatter = Objects.requireNonNull(formatter, "formatter == null");
        if (this.formatter.getZone() == null) {
            this.formatter = this.formatter.withZone(ZoneConstant.BEIJING_ZONE_OFFSET);
        }
    }

    @Override
    public OffsetTime deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isString()) {
                String value = primitive.getAsString();
                if (StringUtil.isEmpty(value)) {
                    return null;
                }
                return OffsetTime.parse(value, formatter);
            }
        }
        return null;
    }

    public static final OffsetTimeDeserializer INSTANCE = new OffsetTimeDeserializer();

}
