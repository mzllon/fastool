package tech.fastool.http.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.fastool.core.lang.Maps;
import tech.fastool.core.lang.Objects;
import tech.fastool.core.lang.Urls;
import tech.fastool.core.utils.LinkedMultiValueMap;
import tech.fastool.core.utils.MultiValueMap;
import tech.fastool.http.api.constants.HttpMethod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 请求对象
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public final class HttpRequest {

    /**
     * 请求方法
     */
    private final HttpMethod method;

    /**
     * 请求地址和请求参数
     */
    private final String url;

    /**
     * 请求消息头
     */
    private final MultiValueMap<String, String> headers;

    /**
     * URL参数
     */
    private final MultiValueMap<String, String> queryParams;

    /**
     * 请求内容
     */
    private final HttpRequestBody body;

    HttpRequest(Builder builder) {
        this.method = builder.method;
        this.headers = new LinkedMultiValueMap<>(builder.headerMap);
        this.queryParams = new LinkedMultiValueMap<>(builder.queryParams);
        int index = builder.url.indexOf('?');
        if (index > 0) {
            String paramStr = builder.url.substring(index + 1);
            this.queryParams.addAll(Urls.parseByUrlQueryString(paramStr));
            this.url = builder.url.substring(0, index);
        } else {
            this.url = builder.url;
        }
        this.body = builder.body;
    }

    /**
     * 返回请求URL
     *
     * @return URL url
     */
    public String url() {
        return url;
    }

    /**
     * HttpRequest Headers.
     *
     * @return the request headers.
     */
    public Map<String, List<String>> headers() {
        return headers;
    }

    public MultiValueMap<String, String> queryParams() {
        return queryParams;
    }

    public HttpMethod method() {
        return method;
    }

    @Nullable
    public HttpRequestBody body() {
        return body;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        /**
         * 请求方法
         */
        private HttpMethod method;

        /**
         * 请求地址和请求参数
         */
        private String url;

        /**
         * 请求消息头
         */
        private final MultiValueMap<String, String> headerMap;

        private final MultiValueMap<String, String> queryParams;

        /**
         * 请求内容
         */
        private HttpRequestBody body;

        public Builder() {
            this.method = HttpMethod.GET;
            this.headerMap = new LinkedMultiValueMap<>();
            this.queryParams = new LinkedMultiValueMap<>();
        }

        public Builder(HttpRequest request) {
            this.method = request.method;
            this.url = request.url;
            this.headerMap = new LinkedMultiValueMap<>(request.headers);
            this.body = request.body;
            this.queryParams = request.queryParams;
        }

        public Builder method(@NotNull HttpMethod method) {
            this.method = Objects.requireNonNull(method, "method == null ");
            return this;
        }

        public Builder url(@NotNull String url) {
            this.url = Objects.requireNotEmpty(url, "url is null or empty");
            return this;
        }

        public Builder replaceHeader(String name, String value) {
            Objects.requireNotEmpty(name, "name is null or empty");
            Objects.requireNonNull(value, "value == null");

            List<String> values = new ArrayList<>();
            values.add(value);
            headerMap.put(name, values);
            return this;
        }

        public Builder addHeader(String name, String value) {
            Objects.requireNotEmpty(name, "name is null or empty");
            Objects.requireNonNull(value, "value == null");

            List<String> values = headerMap.computeIfAbsent(name, k -> new ArrayList<>());
            values.add(value);
            return this;
        }

        public Builder removeHeader(String name) {
            headerMap.remove(name);
            return this;
        }

        public Builder headers(HttpHeaders headers) {
            if (Maps.isNotEmpty(headers)) {
                headers.forEach((BiConsumer<String, Object>) (name, values) -> {
                    if (Objects.isAnyNull(name, values)) {
                        return;
                    }
                    List<String> oValues = headerMap.computeIfAbsent(name, k -> new ArrayList<>());
                    if (values instanceof Collection<?>) {
                        ((Collection<?>) values).forEach((Consumer<Object>) value -> {
                            if (value != null) {
                                oValues.add(value.toString());
                            }
                        });
                    } else {
                        oValues.add(values.toString());
                    }
                });
            }

            return this;
        }

        public Builder queryParams(Map<String, ?> queryParamMap) {
            if (Maps.isNotEmpty(queryParamMap)) {
                queryParamMap.forEach((BiConsumer<String, Object>) (name, values) -> {
                    if (Objects.isAnyNull(name, values)) {
                        return;
                    }
                    List<String> oValues = queryParams.computeIfAbsent(name, k -> new ArrayList<>());
                    if (values instanceof Collection<?>) {
                        ((Collection<?>) values).forEach((Consumer<Object>) value -> {
                            if (value != null) {
                                oValues.add(value.toString());
                            }
                        });
                    } else {
                        oValues.add(values.toString());
                    }
                });
            }
            return this;
        }

        public Builder body(@Nullable HttpRequestBody body) {
            return method(this.method, body);
        }

        public Builder method(HttpMethod method, @Nullable HttpRequestBody body) {
            method(method);
            if (method == HttpMethod.GET || method == HttpMethod.HEAD) {
                // 不能有body
                if (body != null) {
                    throw new IllegalArgumentException("method " + method.name() + " must not have a request body");
                }
            } else if (method == HttpMethod.POST || method == HttpMethod.PUT || method == HttpMethod.PATCH) {
                if (body == null) {
                    throw new IllegalArgumentException("method " + method.name() + " must have a request body");
                }
            }
            this.body = body;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(this);
        }
    }

}
