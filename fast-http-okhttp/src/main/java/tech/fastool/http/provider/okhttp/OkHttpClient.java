package tech.fastool.http.provider.okhttp;

import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.fastool.core.exceptions.IoRuntimeException;
import tech.fastool.core.io.IOes;
import tech.fastool.core.lang.*;
import tech.fastool.core.utils.ContentType;
import tech.fastool.http.api.*;
import tech.fastool.http.api.constants.HttpMethod;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 基于{@code okhttp}提供的{@code Client}
 *
 * @author miles.tang
 */
public class OkHttpClient implements HttpClient {

    private final okhttp3.OkHttpClient delegate;

    public OkHttpClient() {
        this(new okhttp3.OkHttpClient());
    }

    public OkHttpClient(@NotNull okhttp3.OkHttpClient delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    public OkHttpClient(@Nullable HttpOptions options) {
        HttpOptions httpOptions = Objects.getIfNull(options, HttpOptions.DEFAULT_OPTIONS);

        okhttp3.OkHttpClient.Builder builder = new okhttp3.OkHttpClient.Builder()
                // 连接超时
                .connectTimeout(httpOptions.connectTimeoutMillis(), TimeUnit.MILLISECONDS)
                // 读取超时
                .readTimeout(httpOptions.readTimeoutMillis(), TimeUnit.MILLISECONDS)
                // 写出超时
                .writeTimeout(httpOptions.writeTimeoutMillis(), TimeUnit.MILLISECONDS)
                // 3XX自动跳转
                .followRedirects(httpOptions.followRedirects());
//        // debugger
//        DebugLoggingInterceptor debugLoggingInterceptor = DebugLoggingInterceptor.INSTANCE;
//        debugLoggingInterceptor.setLoggingLevel(DebugLoggingInterceptor.Level.ALL);
//        builder.addNetworkInterceptor(debugLoggingInterceptor);

        // 代理
        ProxyInfo proxyInfo = httpOptions.proxyInfo();
        if (proxyInfo != null) {
            builder.proxy(proxyInfo.toJdkProxy());
            if (Strings.isAllNotBlank(proxyInfo.username(), proxyInfo.password())) {
                builder.proxyAuthenticator((route, response) -> response.request().newBuilder()
                        .header("Proxy-Authorization", Credentials.basic(proxyInfo.username(), proxyInfo.password()))
                        .build());
            }
        }

        // 重试机制
        if (httpOptions.retryCount() > 0) {
            builder.addNetworkInterceptor(new Retry(httpOptions.retryCount()));
        }

        this.delegate = builder.build();
    }

    /**
     * 执行HTTP请求
     *
     * @param request 请求对象
     * @param options 请求选项
     * @return 执行结果
     * @throws IoRuntimeException HTTP请求异常¬
     */
    @Override
    public HttpResponse execute(@NotNull HttpRequest request, HttpOptions options) throws IoRuntimeException {
        okhttp3.OkHttpClient okHttpClientScoped = null;
        if (options != null) {
            okhttp3.OkHttpClient.Builder builder = delegate.newBuilder();
            if (delegate.connectTimeoutMillis() != options.connectTimeoutMillis() ||
                    delegate.readTimeoutMillis() != options.readTimeoutMillis() ||
                    delegate.writeTimeoutMillis() != options.writeTimeoutMillis() ||
                    delegate.followRedirects() != options.followRedirects()) {
                builder.connectTimeout(options.connectTimeoutMillis(), TimeUnit.MILLISECONDS)
                        .readTimeout(options.readTimeoutMillis(), TimeUnit.MILLISECONDS)
                        .writeTimeout(options.connectTimeoutMillis(), TimeUnit.MILLISECONDS)
                        .followRedirects(options.followRedirects())
                        .build();
            }
            ProxyInfo proxyInfo = options.proxyInfo();
            if (proxyInfo != null) {
                builder.proxy(proxyInfo.toJdkProxy());
                if (Strings.isAllNotBlank(proxyInfo.username(), proxyInfo.password())) {
                    builder.proxyAuthenticator((route, response) -> response.request().newBuilder()
                            .header("Proxy-Authorization", Credentials.basic(proxyInfo.username(), proxyInfo.password()))
                            .build());
                }
            }
            if (options.retryCount() > 0) {
                builder.addNetworkInterceptor(new Retry(options.retryCount()));
            }
            okHttpClientScoped = builder.build();
        } else {
            okHttpClientScoped = delegate;
        }
        okhttp3.Request okRequest = toOkHttpRequest(request);
        try {
            okhttp3.Response okResponse = okHttpClientScoped.newCall(okRequest).execute();
            return toHttpResponse(okResponse, request).newBuilder().request(request).build();
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } finally {
            Utils.closeParts(request.body());
        }
    }

    private static okhttp3.Request toOkHttpRequest(HttpRequest input) {
        HttpUrl.Builder httpUrlBuilder = HttpUrl.parse(input.url()).newBuilder();
        if (Maps.isNotEmpty(input.queryParams())) {
            input.queryParams().forEach((name, values) -> values.forEach(value -> httpUrlBuilder.addQueryParameter(name, value)));
        }

        okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder();
        requestBuilder.url(httpUrlBuilder.build());

        ContentType contentType = null;
        if (input.body() != null) {
            contentType = input.body().contentType();
        }

        // header
        boolean hasAcceptHeader = false;
        for (Map.Entry<String, List<String>> entry : input.headers().entrySet()) {
            if (Strings.equalsIgnoreCase(entry.getKey(), "Accept")) {
                hasAcceptHeader = true;
            }

            for (String value : entry.getValue()) {
                requestBuilder.addHeader(entry.getKey(), value);
                if (Strings.equalsIgnoreCase("Content-Type", entry.getKey())) {
                    contentType = ContentType.parse(value);
                }
            }
        }
        // Some servers choke on the default accept string.
        if (!hasAcceptHeader) {
            requestBuilder.addHeader("Accept", "*/*");
        }

        MediaType okhttp3MediaType = contentType == null ? null : MediaType.parse(contentType.toString());

        // process body
        boolean isMethodWithBody = HttpMethod.POST == input.method() || HttpMethod.PUT == input.method() ||
                HttpMethod.PATCH == input.method();
        if (isMethodWithBody) {
            requestBuilder.removeHeader("Content-Type");
        }
        if (input.body() == null) {
            if (isMethodWithBody) {
                requestBuilder.method(input.method().name(), okhttp3.RequestBody.create(okhttp3MediaType, new byte[0]));
            } else {
                requestBuilder.method(input.method().name(), null);
            }
        } else if (input.body() instanceof HttpFormBody) {
            HttpFormBody formBody = (HttpFormBody) input.body();
            okhttp3.FormBody.Builder builder = new okhttp3.FormBody.Builder(formBody.getCharset());
            for (HttpFormBody.Item item : formBody.getItems()) {
                builder.add(item.getName(), item.getValue());
            }
            requestBuilder.method(input.method().name(), builder.build());
        } else if (input.body() instanceof HttpMultipartBody) {
            HttpMultipartBody multipartBody = (HttpMultipartBody) input.body();
            okhttp3.MultipartBody.Builder builder;
            if (Strings.hasLength(multipartBody.getBoundary())) {
                builder = new okhttp3.MultipartBody.Builder(multipartBody.getBoundary());
            } else {
                builder = new okhttp3.MultipartBody.Builder();
            }
            builder.setType(okhttp3MediaType);

            if (Lists.isEmpty(multipartBody.getParts())) {
                builder.addPart(okhttp3.RequestBody.create(okhttp3.MultipartBody.FORM, new byte[0]));
            } else {
                for (HttpMultipartBody.Part part : multipartBody.getParts()) {
                    okhttp3.MediaType partMediaType = okhttp3.MediaType.parse(part.getContentType().toString());
                    if (part.getFile() != null) {
                        builder.addFormDataPart(part.getName(), part.getValue(),
                                okhttp3.RequestBody.create(partMediaType, part.getFile()));
                    } else if (part.getIn() != null) {
                        builder.addFormDataPart(part.getName(), part.getValue(),
                                okhttp3.RequestBody.create(partMediaType, HttpRequestBody.create(null, part.getIn()).getData()));
                    } else if (part.getBody() != null) {
                        builder.addFormDataPart(part.getName(), part.getValue(),
                                okhttp3.RequestBody.create(partMediaType, part.getBody().getData()));
                    } else if (part.getValue() != null) {
                        builder.addFormDataPart(part.getName(), null,
                                okhttp3.RequestBody.create(partMediaType, part.getValue()));
                    }
                }
            }
            requestBuilder.method(input.method().name(), builder.build());
        } else {
            requestBuilder.method(input.method().name(), okhttp3.RequestBody.create(okhttp3MediaType, input.body().getData()));
        }
        return requestBuilder.build();
    }

    private static HttpResponse toHttpResponse(okhttp3.Response okResponse, HttpRequest input) {
        return HttpResponse.builder()
                .rawStatus(okResponse.code())
                .reason(okResponse.message())
                .request(input)
                .headers(new HttpHeaders(okResponse.headers().toMultimap()))
                .body(toBody(okResponse))
                .build();
    }

    private static HttpResponseBody toBody(final okhttp3.Response okResponse) {
        okhttp3.ResponseBody okBody = okResponse.body();
        if (okBody == null) {
            return null;
        }
        long contentLength = okBody.contentLength();
        if (contentLength == 0) {
            okBody.close();
            return null;
        }
        final Integer length = contentLength > Integer.MAX_VALUE ? null : (int) contentLength;
        return new HttpResponseBody() {
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
                return okBody.byteStream();
            }

            @Override
            public Reader charStream(Charset charset) throws IoRuntimeException {
                return okBody.charStream();
            }

            @Override
            public String string(Charset charset) throws IoRuntimeException {
                if (okResponse.code() == HttpStatus.NOT_FOUND.value() ||
                        okResponse.code() == HttpStatus.NO_CONTENT.value()) {
                    return null;
                }

                okhttp3.MediaType okMediaType = okBody.contentType();
                Charset encoding = null;
                if (okMediaType != null) {
                    encoding = okMediaType.charset(charset);
                }
                if (encoding == null) {
                    encoding = Charsets.UTF_8;
                }

                Reader reader = null;
                try {
                    reader = charStream(encoding);
                    return IOes.read(reader);
                } finally {
                    IOes.closeQuietly(reader);
                }
            }

            @Override
            public void close() {
                okBody.close();
            }
        };

    }

}
