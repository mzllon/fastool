package tech.fastool.json.provider.jackson.deser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import tech.fastool.core.date.DatePattern;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.regex.Pattern;

/**
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class OffsetDateTimeDeserializer extends InstantDeserializer<OffsetDateTime> {

    public OffsetDateTimeDeserializer() {
        super(InstantDeserializer.OFFSET_DATE_TIME, DatePattern.UTC_MS_WITH_ZONE_OFFSET_FORMATTER);
    }

    @Override
    public OffsetDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        switch (parser.getCurrentTokenId()) {
            case JsonTokenId.ID_NUMBER_FLOAT:
                return _fromDecimal(context, parser.getDecimalValue());
            case JsonTokenId.ID_NUMBER_INT:
                return _fromLong(context, parser.getLongValue());
            case JsonTokenId.ID_STRING: {
                String string = parser.getText().trim();
                if (string.length() == 0) {
                    return null;
                }
                return OffsetDateTime.parse(string, _formatter);
            }
            case JsonTokenId.ID_EMBEDDED_OBJECT:
                // 20-Apr-2016, tatu: Related to [databind#1208], can try supporting embedded
                //    values quite easily
                return (OffsetDateTime) parser.getEmbeddedObject();

            case JsonTokenId.ID_START_ARRAY:
                return _deserializeFromArray(parser, context);
            default:
                break;
        }
        return _handleUnexpectedToken(context, parser, JsonToken.VALUE_STRING,
                JsonToken.VALUE_NUMBER_INT, JsonToken.VALUE_NUMBER_FLOAT);
    }

    private String replaceZeroOffsetAsZIfNecessary(String text) {
        if (replaceZeroOffsetAsZ) {
            return ISO8601_UTC_ZERO_OFFSET_SUFFIX_REGEX.matcher(text).replaceFirst("Z");
        }

        return text;
    }

    /**
     * Constants used to check if the time offset is zero. See [jackson-modules-java8#18]
     *
     * @since 2.9.0
     */
    private static final Pattern ISO8601_UTC_ZERO_OFFSET_SUFFIX_REGEX = Pattern.compile("\\+00:?(00)?$");

}
