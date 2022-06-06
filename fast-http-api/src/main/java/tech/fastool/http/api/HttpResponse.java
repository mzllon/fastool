package tech.fastool.http.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.fastool.core.exceptions.IoRuntimeException;
import tech.fastool.core.io.IoUtil;
import tech.fastool.core.lang.CharsetUtil;
import tech.fastool.core.lang.ListUtil;
import tech.fastool.core.lang.MapUtil;
import tech.fastool.core.lang.ObjectUtil;
import tech.fastool.http.api.exceptions.HttpClientErrorException;
import tech.fastool.http.api.exceptions.HttpServerErrorException;
import tech.fastool.http.api.exceptions.UnknownHttpStatusCodeException;

import java.io.*;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * 通用的HTTP响应类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class HttpResponse implements Closeable {

    /**
     * HTTP状态码
     */
    int rawStatus;

    /**
     * HTTP状态消息
     */
    String reason;

    /**
     * 响应头
     */
    HttpHeaders headers;

    /**
     * 响应内容
     */
    HttpResponseBody body;

    /**
     * 原始HTTP请求
     */
    HttpRequest request;

    private HttpResponse(Builder builder) {
        this.rawStatus = builder.rawStatus;
        this.reason = builder.reason;
        this.headers = builder.headers;
        this.body = builder.body;
        this.request = builder.request;
    }

    public Builder newBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    /**
     * status code. ex {@code 200}
     * <p>
     * See <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html" >rfc2616</a>
     */
    public int rawStatus() {
        return rawStatus;
    }

    /**
     * 返回HTTP包装过的状态码
     *
     * @return 状态码
     */
    public HttpStatus status() {
        return HttpStatus.valueOf(rawStatus());
    }

    /**
     * Nullable and not set when using http/2
     * <p>
     * See https://github.com/http2/http2-spec/issues/202
     */
    public String reason() {
        return reason;
    }

    public Map<String, List<String>> headers() {
        return headers;
    }

    public HttpResponseBody body() {
        return body;
    }

    public HttpRequest request() {
        return request;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("HTTP/1.1 ").append(rawStatus);
        if (reason != null) {
            builder.append(' ').append(reason);
        }
        builder.append('\n');

        if (MapUtil.isNotEmpty(headers)) {
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                if (ListUtil.isNotEmpty(entry.getValue())) {
                    for (String value : entry.getValue()) {
                        builder.append(entry.getKey()).append(": ").append(value).append('\n');
                    }
                }
            }
        }

        if (body != null) {
            builder.append('\n').append(body);
        }
        return builder.toString();
    }

    @Override
    public void close() {
        IoUtil.closeQuietly(body);
    }

    /**
     * 检查HTTP status code是否有错误
     *
     * @throws HttpClientErrorException       客户端异常
     * @throws HttpServerErrorException       服务端异常
     * @throws UnknownHttpStatusCodeException 未知状态码异常
     */
    public void checkStatus() {
        if (hasError()) {
            HttpStatus statusCode = status();
            byte[] responseBody = IoUtil.readBytes(body.byteStream());
            switch (statusCode.series()) {
                case CLIENT_ERROR:
                    throw new HttpClientErrorException(statusCode, reason, headers, responseBody, null, request);
                case SERVER_ERROR:
                    throw new HttpServerErrorException(statusCode, reason, headers, responseBody, null, request);
                default:
                    throw new UnknownHttpStatusCodeException(statusCode.value(), reason, headers, responseBody, null, request);
            }
        }
    }

    /**
     * 判断Response是否有错误
     *
     * @return {@code true}/{@code false}
     */
    public boolean hasError() {
        HttpStatus statusCode = status();
        return (statusCode.series() == HttpStatus.Series.CLIENT_ERROR || statusCode.series() == HttpStatus.Series.SERVER_ERROR);
    }

    /**
     * {@linkplain HttpResponse}的构造者
     *
     * @author miles.tang
     */
    public static class Builder {

        /**
         * HTTP状态码
         */
        int rawStatus;

        /**
         * HTTP状态消息
         */
        String reason;

        /**
         * 响应头
         */
        HttpHeaders headers;

        /**
         * 响应内容
         */
        HttpResponseBody body;

        /**
         * 原始HTTP请求
         */
        HttpRequest request;

        Builder() {
        }

        Builder(@NotNull HttpResponse source) {
            this.rawStatus = ObjectUtil.requireNonNull(source).rawStatus;
            this.reason = source.reason;
            this.headers = source.headers;
            this.body = source.body;
            this.request = source.request;
        }

        /**
         * @see HttpResponse#rawStatus
         */
        public Builder rawStatus(int rawStatus) {
            this.rawStatus = rawStatus;
            return this;
        }

        /**
         * @see HttpResponse#reason
         */
        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        /**
         * @see HttpResponse#headers
         */
        public Builder headers(HttpHeaders headers) {
            this.headers = headers;
            return this;
        }

        /**
         * @see HttpResponse#body
         */
        public Builder body(HttpResponseBody body) {
            this.body = body;
            return this;
        }

        /**
         * @see HttpResponse#body
         */
        public Builder body(InputStream inputStream, int length) {
            this.body = InputStreamResponseBody.create(inputStream, length);
            return this;
        }

        /**
         * @see HttpResponse#body
         */
        public Builder body(byte[] data) {
            this.body = ByteArrayResponseBody.create(data);
            return this;
        }

        /**
         * @see HttpResponse#body
         */
        public Builder body(String text, Charset charset) {
            this.body = ByteArrayResponseBody.create(text, charset);
            return this;
        }

        /**
         * @see HttpResponse#request
         */
        public Builder request(HttpRequest request) {
            this.request = ObjectUtil.requireNonNull(request, "request is required");
            return this;
        }


        public HttpResponse build() {
            return new HttpResponse(this);
        }
    }

    static class ByteArrayResponseBody implements HttpResponseBody {

        private final byte[] data;

        private ByteArrayResponseBody(@NotNull byte[] data) {
            this.data = data;
        }

        @Override
        public Integer length() {
            return data.length;
        }

        @Override
        public boolean isRepeatable() {
            return true;
        }

        @Override
        public InputStream byteStream() throws IoRuntimeException {
            return new ByteArrayInputStream(data);
        }

        @Override
        public Reader charStream(Charset charset) throws IoRuntimeException {
            return new BufferedReader(new InputStreamReader(byteStream(), charset));
        }

        /**
         * 将响应内容转为字符串，并且会自动关闭流
         *
         * @param charset 编码字符集，可空，
         * @return 响应字符串
         * @throws IoRuntimeException IO异常
         */
        @Override
        public String string(Charset charset) throws IoRuntimeException {
            return new String(data, charset);
        }

        @Override
        public void close() {
            // NOP
        }

        public static HttpResponseBody create(byte[] data) {
            return new ByteArrayResponseBody(ObjectUtil.requireNonNull(data));
        }

        public static HttpResponseBody create(@NotNull String text, @Nullable Charset charset) {
            return create(ObjectUtil.requireNotEmpty(text).getBytes(CharsetUtil.getCharset(charset)));
        }
    }

    static class InputStreamResponseBody implements HttpResponseBody {

        private final InputStream in;

        private final int length;

        private InputStreamResponseBody(@NotNull InputStream in, int length) {
            this.in = in;
            this.length = length;
        }


        /**
         * 字节的长度，可能为{@code null}
         *
         * @return 字节长度
         */
        @Override
        public Integer length() {
            return length;
        }

        @Override
        public boolean isRepeatable() {
            return false;
        }

        @Override
        public InputStream byteStream() throws IoRuntimeException {
            return in;
        }

        @Override
        public Reader charStream(Charset charset) throws IoRuntimeException {
            Charset encoding = CharsetUtil.getCharset(charset, CharsetUtil.UTF_8);
            return new BufferedReader(new InputStreamReader(in, encoding));
        }

        /**
         * 将响应内容转为字符串，并且会自动关闭流
         *
         * @param charset 编码字符集，可空，
         * @return 响应字符串
         * @throws IoRuntimeException IO异常
         */
        @Override
        public String string(Charset charset) throws IoRuntimeException {
            return IoUtil.read(charStream(charset));
        }

        @Override
        public void close() throws IOException {
            in.close();
        }

        public static HttpResponseBody create(@NotNull InputStream in, int length) {
            return new InputStreamResponseBody(ObjectUtil.requireNonNull(in), length);
        }

    }

}
