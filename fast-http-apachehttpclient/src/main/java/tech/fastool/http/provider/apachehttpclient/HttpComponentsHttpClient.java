package tech.fastool.http.provider.apachehttpclient;

import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.Configurable;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.fastool.core.exceptions.IoRuntimeException;
import tech.fastool.core.io.IoUtil;
import tech.fastool.core.lang.CharsetUtil;
import tech.fastool.core.lang.ListUtil;
import tech.fastool.core.lang.ObjectUtil;
import tech.fastool.core.lang.StringUtil;
import tech.fastool.http.api.HttpHeaders;
import tech.fastool.http.api.HttpRequest;
import tech.fastool.http.api.HttpResponse;
import tech.fastool.http.api.*;
import tech.fastool.http.api.constants.HeaderName;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 基于{@code Apache HttpComponents}包装实现
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class HttpComponentsHttpClient implements HttpClient {

    private final org.apache.http.client.HttpClient httpClient;

    public HttpComponentsHttpClient() {
        this((HttpOptions) null);
    }

    public HttpComponentsHttpClient(@NotNull org.apache.http.client.HttpClient httpClient) {
        this.httpClient = ObjectUtil.requireNonNull(httpClient, "httpClient == null");
    }

    public HttpComponentsHttpClient(@Nullable HttpOptions options) {
        this.httpClient = build(options, null);
    }

    org.apache.http.client.HttpClient build(@Nullable HttpOptions options, @Nullable org.apache.http.client.HttpClient httpClient) {
        if (options == null) {
            return HttpClientBuilder.create().build();
        } else {
            HttpClientBuilder builder = HttpClientBuilder.create();
            RequestConfig.Builder customBuilder = ((httpClient instanceof Configurable) ?
                    RequestConfig.copy(((Configurable) httpClient).getConfig()) : RequestConfig.custom())
                    .setConnectTimeout(options.connectTimeoutMillis())
                    .setSocketTimeout(options.readTimeoutMillis())
                    .setRedirectsEnabled(options.followRedirects());

            // 代理
            ProxyInfo proxyInfo = options.proxyInfo();
            if (proxyInfo != null) {
                builder.setProxy(new HttpHost(proxyInfo.hostOrIp(), proxyInfo.port(), proxyInfo.type().name()));

                if (StringUtil.isAllNotBlank(proxyInfo.username(), proxyInfo.password())) {
                    CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                    credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(proxyInfo.username(), proxyInfo.password()));
                    builder.setDefaultCredentialsProvider(credentialsProvider);
                }
            }

            // 重试机制
            if (options.retryCount() > 0) {
                builder.setRetryHandler(new DefaultHttpRequestRetryHandler(options.retryCount()));
            }

            builder.setDefaultRequestConfig(customBuilder.build());
            return builder.build();
        }
    }

    @Override
    public HttpResponse execute(@NotNull HttpRequest request, HttpOptions options) throws IoRuntimeException {
        try {
            HttpUriRequest httpUriRequest = toHttpUriRequest(request);
            org.apache.http.client.HttpClient httpClientScoped;

            if (options == null) {
                httpClientScoped = httpClient;
            } else {
                httpClientScoped = build(options, httpClient);
            }
            org.apache.http.HttpResponse httpResponse = httpClientScoped.execute(httpUriRequest);
            return toApiResponse(httpResponse, request);
        } catch (URISyntaxException e) {
            throw new IoRuntimeException("URL '" + request.url() + "' couldn't be parsed into a URI", e);
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        } finally {
            Utils.closeParts(request.body());
        }
    }

    private HttpUriRequest toHttpUriRequest(HttpRequest request) throws URISyntaxException {
        RequestBuilder requestBuilder = RequestBuilder.create(request.method().name());
        URIBuilder uriBuilder = new URIBuilder(request.url());
        request.queryParams().forEach((name, values) -> values.forEach(value -> uriBuilder.addParameter(name, value)));
        requestBuilder.setUri(uriBuilder.build());

        // request headers
        boolean hasAcceptHeader = false;
        String headerName;
        for (Map.Entry<String, List<String>> entry : request.headers().entrySet()) {
            headerName = entry.getKey();
            if (headerName.equalsIgnoreCase(HeaderName.ACCEPT.toString())) {
                hasAcceptHeader = true;
            }

            if (StringUtil.equalsIgnoreCase(headerName, HeaderName.CONTENT_LENGTH.toString())) {
                // The 'Content-Length' header is always set by the Apache client and it
                // doesn't like us to set it as well.
                continue;
            }

            for (String value : entry.getValue()) {
                requestBuilder.addHeader(headerName, value);
            }
        }
        if (!hasAcceptHeader) {
            requestBuilder.addHeader(HeaderName.ACCEPT.toString(), "*/*");
        }

        // request body
        if (request.body() != null) {
            ContentType contentType = getContentType(request);
            if (request.body() instanceof HttpFormBody) {
                HttpFormBody formBody = (HttpFormBody) request.body();
                List<NameValuePair> nvpList = formBody.getItems().stream()
                        .map(item -> new BasicNameValuePair(item.getName(), item.getValue()))
                        .collect(Collectors.toList());
                requestBuilder.setEntity(new UrlEncodedFormEntity(nvpList, formBody.getCharset()));
            } else if (request.body() instanceof HttpMultipartBody) {
                HttpMultipartBody multipartBody = (HttpMultipartBody) request.body();
                MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create()
                        .setBoundary(multipartBody.getBoundary())
                        .setCharset(multipartBody.getCharset())
                        .setContentType(contentType)
                        .setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                for (HttpMultipartBody.Part part : multipartBody.getParts()) {
                    ContentType partContentType = ContentType.parse(part.getContentType().toString());
                    if (part.getFile() != null) {
                        multipartEntityBuilder.addBinaryBody(part.getName(), part.getFile());
                    } else if (part.getIn() != null) {
                        multipartEntityBuilder.addBinaryBody(part.getName(), part.getIn(), partContentType, part.getValue());
                    } else if (part.getBody() != null) {
                        multipartEntityBuilder.addBinaryBody(part.getName(), part.getBody().getData(),
                                partContentType, part.getValue());
                    } else if (part.getValue() != null) {
                        multipartEntityBuilder.addTextBody(part.getName(), part.getValue(), partContentType);
                    }
                }
                requestBuilder.setEntity(multipartEntityBuilder.build());
            } else {
                ByteArrayEntity byteArrayEntity = new ByteArrayEntity(request.body().getData());
                if (request.body().contentType() != null) {
                    byteArrayEntity.setContentType(contentType.toString());
                }
                requestBuilder.setEntity(byteArrayEntity);
            }
        } else {
            requestBuilder.setEntity(new ByteArrayEntity(new byte[0]));
        }
        return requestBuilder.build();
    }

    private ContentType getContentType(HttpRequest request) {
        ContentType contentType = null;
        HttpRequestBody body = request.body();
        if (body != null) {
            contentType = ContentType.parse(body.contentType().toString());
        }
        if (contentType == null) {
            for (Map.Entry<String, List<String>> entry : request.headers().entrySet()) {
                if (entry.getKey().equalsIgnoreCase(HeaderName.CONTENT_TYPE.toString())) {
                    List<String> values = entry.getValue();
                    if (ListUtil.isNotEmpty(values)) {
                        contentType = ContentType.parse(values.get(0));
                        if (contentType.getCharset() == null) {
                            contentType = contentType.withCharset(CharsetUtil.UTF_8);
                        }
                        break;
                    }
                }
            }
        }
        return contentType;
    }

    private HttpResponse toApiResponse(org.apache.http.HttpResponse httpResponse, HttpRequest request) {
        StatusLine statusLine = httpResponse.getStatusLine();
        int statusCode = statusLine.getStatusCode();
        String reason = statusLine.getReasonPhrase();

        HttpHeaders httpHeaders = new HttpHeaders();
        for (Header header : httpResponse.getAllHeaders()) {
            httpHeaders.append(header.getName(), header.getValue());
        }

        return HttpResponse.builder()
                .rawStatus(statusCode)
                .reason(reason)
                .headers(httpHeaders)
                .request(request)
                .body(toBody(httpResponse))
                .build();
    }

    private HttpResponseBody toBody(org.apache.http.HttpResponse httpResponse) {
        HttpEntity entity = httpResponse.getEntity();
        if (entity == null) {
            return null;
        }
        return new HttpResponseBody() {
            @Override
            public Integer length() {
                long contentLength = entity.getContentLength();
                return contentLength >= 0 && contentLength <= Integer.MAX_VALUE ? (int) contentLength : null;
            }

            @Override
            public boolean isRepeatable() {
                return entity.isRepeatable();
            }

            @Override
            public InputStream byteStream() throws IoRuntimeException {
                try {
                    return entity.getContent();
                } catch (IOException e) {
                    throw new IoRuntimeException(e);
                }
            }

            @Override
            public Reader charStream(Charset charset) throws IoRuntimeException {
                return new InputStreamReader(byteStream(), getCharset(charset));
            }

            @Override
            public String string(Charset charset) throws IoRuntimeException {
                try {
                    return IoUtil.read(charStream(charset));
                } finally {
                    try {
                        close();
                    } catch (IOException e) {
                        //ignore
                    }
                }
            }

            @Override
            public void close() throws IOException {
                try {
                    EntityUtils.consume(entity);
                } finally {
                    if (httpResponse instanceof Cloneable) {
                        IoUtil.closeQuietly(((Closeable) httpResponse));
                    }
                }
            }

            @NotNull
            Charset getCharset(Charset charset) {
                Charset resultEncoding = charset;
                if (resultEncoding == null) {
                    ContentType contentType = ContentType.get(entity);
                    if (contentType != null) {
                        resultEncoding = contentType.getCharset();
                    }
                }
                if (resultEncoding == null) {
                    resultEncoding = CharsetUtil.UTF_8;
                }
                return resultEncoding;
            }

        };
    }

}
