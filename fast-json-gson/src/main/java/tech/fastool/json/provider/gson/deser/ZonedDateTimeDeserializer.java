package tech.fastool.json.provider.gson.deser;

import com.google.gson.*;
import tech.fastool.core.date.ZoneConstant;
import tech.fastool.core.lang.Objects;
import tech.fastool.core.lang.StringUtil;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Serializer for Java 8 temporal {@link ZonedDateTime}s.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class ZonedDateTimeDeserializer implements JsonDeserializer<ZonedDateTime> {

    private DateTimeFormatter formatter;

    public ZonedDateTimeDeserializer() {
        this(DateTimeFormatter.ISO_ZONED_DATE_TIME.withZone(ZoneConstant.BEIJING_ZONE_OFFSET));
    }

    public ZonedDateTimeDeserializer(DateTimeFormatter formatter) {
        this.formatter = Objects.requireNonNull(formatter, "formatter == null");
        if (this.formatter.getZone() == null) {
            this.formatter = this.formatter.withZone(ZoneConstant.BEIJING_ZONE_OFFSET);
        }
    }

    @Override
    public ZonedDateTime deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            try {
                if (primitive.isString()) {
                    String value = primitive.getAsString();
                    if (StringUtil.isEmpty(value)) {
                        return null;
                    }
                    // '2011-12-03T10:15:30+01:00[Asia/Shanghai]'
                    return ZonedDateTime.parse(value, this.formatter);
                } else if (primitive.isNumber()) {
                    // 认为是毫秒
                    return ZonedDateTime.ofInstant(Instant.ofEpochMilli(primitive.getAsLong()), formatter.getZone());
                }
            } catch (RuntimeException e) {
                throw new JsonParseException("Unable to parse ZonedDateTime", e);
            }
        }
        return null;
    }

    public static final ZonedDateTimeDeserializer INSTANCE = new ZonedDateTimeDeserializer();

}
