package tech.fastool.http.api;

import org.jetbrains.annotations.Nullable;
import tech.fastool.core.lang.Maps;
import tech.fastool.core.utils.LinkedMultiValueMap;
import tech.fastool.http.api.constants.HeaderName;

import java.util.Map;
import java.util.function.BiConsumer;

/**
 * HTTP头
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class HttpHeaders extends LinkedMultiValueMap<String, String> {

    public HttpHeaders() {
        super();
    }

    public HttpHeaders(@Nullable Map<String, ?> headerMap) {
        super();
        if (Maps.isNotEmpty(headerMap)) {
            headerMap.forEach((BiConsumer<String, Object>) this::append);
        }
    }

    public HttpHeaders append(@Nullable String key, @Nullable Object value) {
        if (key != null && value != null) {
            add(key, value.toString());
        }

        return this;
    }

    public HttpHeaders append(@Nullable HeaderName key, Object value) {
        if (key != null && value != null) {
            add(key.toString(), value.toString());
        }

        return this;
    }

}
