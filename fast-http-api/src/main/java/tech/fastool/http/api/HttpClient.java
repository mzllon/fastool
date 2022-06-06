package tech.fastool.http.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.fastool.core.exceptions.IoRuntimeException;
import tech.fastool.core.io.IoUtil;
import tech.fastool.core.lang.MapUtil;
import tech.fastool.core.lang.StringUtil;
import tech.fastool.core.lang.UrlUtil;
import tech.fastool.core.utils.MultiValueMap;
import tech.fastool.http.api.constants.HeaderName;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 执行HTTP请求{@linkplain HttpRequest}
 *
 * @author miles.tang
 */
public interface HttpClient {

    /**
     * 执行HTTP请求
     *
     * @param request     请求对象
     * @param httpOptions 请求选项
     * @return 执行结果
     * @throws IoRuntimeException HTTP请求异常
     */
    HttpResponse execute(@NotNull HttpRequest request, @Nullable HttpOptions httpOptions) throws IoRuntimeException;

    /**
     * 实现一个简单的HTTP客户端
     */
    class DefaultHttpClient implements HttpClient {

        /**
         * 执行HTTP请求
         *
         * @param request     请求对象
         * @param httpOptions 请求选项
         * @return 执行结果
         * @throws IoRuntimeException HTTP请求异常
         */
        @Override
        public HttpResponse execute(@NotNull HttpRequest request, HttpOptions httpOptions) throws IoRuntimeException {
            try {
                HttpURLConnection connection = convertAndSend(request, httpOptions);
                return convertResponse(connection, request);
            } catch (IOException e) {
                throw new IoRuntimeException(e);
            } finally {
                Utils.closeParts(request.body());
            }
        }

        public HttpURLConnection getConnection(final URL url) throws IOException {
            return (HttpURLConnection) url.openConnection();
        }

        HttpURLConnection convertAndSend(HttpRequest request, HttpOptions httpOptions) throws IOException {
            final URL url = new URL(encodeUrl(request.url(), request.queryParams()));
            HttpURLConnection connection = getConnection(url);
            if (connection instanceof HttpsURLConnection) {
                HttpsURLConnection httpsConnection = (HttpsURLConnection) connection;
                // do something
            }
            if (httpOptions != null) {
                connection.setConnectTimeout(httpOptions.connectTimeoutMillis());
                connection.setReadTimeout(httpOptions.readTimeoutMillis());
                connection.setAllowUserInteraction(false);
                connection.setInstanceFollowRedirects(httpOptions.followRedirects());
            }
            connection.setRequestMethod(request.method().name());
            List<String> contentEncodingValues = request.headers().get(HeaderName.CONTENT_ENCODING.toString());
            boolean gzipEncodedRequest =
                    contentEncodingValues != null && contentEncodingValues.contains(ENCODING_GZIP);
            boolean deflateEncodedRequest =
                    contentEncodingValues != null && contentEncodingValues.contains(ENCODING_DEFLATE);
            boolean hasAcceptHeader = false;
            Integer contentLength = null;
            for (String field : request.headers().keySet()) {
                if (field.equalsIgnoreCase("Accept")) {
                    hasAcceptHeader = true;
                }
                for (String value : request.headers().get(field)) {
                    if (field.equals(HeaderName.CONTENT_LENGTH.toString())) {
                        if (!gzipEncodedRequest && !deflateEncodedRequest) {
                            contentLength = Integer.valueOf(value);
                            connection.addRequestProperty(field, value);
                        }
                    } else {
                        connection.addRequestProperty(field, value);
                    }
                }
            }
            if (!hasAcceptHeader) {
                connection.addRequestProperty("Accept", "*/*");
            }

            if (request.body() != null) {
                if (contentLength != null) {
                    connection.setFixedLengthStreamingMode(contentLength);
                } else {
                    connection.setChunkedStreamingMode(8192);
                }
                connection.setDoOutput(true);
                OutputStream out = connection.getOutputStream();
                if (gzipEncodedRequest) {
                    out = new GZIPOutputStream(out);
                } else if (deflateEncodedRequest) {
                    out = new DeflaterOutputStream(out);
                }
                try {
                    out.write(request.body().getData());
                } finally {
                    IoUtil.closeQuietly(out);
                }
            }
            return connection;
        }

        private String encodeUrl(String url, MultiValueMap<String, String> queryParamMap) {
            if (MapUtil.isNotEmpty(queryParamMap)) {
                StringBuilder buffer = new StringBuilder(url);
                buffer.append("?");
                queryParamMap.forEach((name, values) ->
                        values.forEach(value ->
                                buffer.append(name).append(StringUtil.EQUALS).append(UrlUtil.encode(value)).append(StringUtil.AMP)
                        )
                );
                buffer.deleteCharAt(buffer.length() - 1);
                return buffer.toString();
            }
//            int index = url.indexOf('?');
//            if (index > 0) {
//                String paramStr = url.substring(index + 1);
//                MultiValueMap<String, String> paramMap = UrlUtil.parseByUrlQueryString(paramStr);
//                StringBuilder buffer = new StringBuilder(url.substring(0, index));
//                for (Map.Entry<String, List<String>> entry : paramMap.entrySet()) {
//                    for (String val : entry.getValue()) {
//                        buffer.append(entry.getKey()).append(StringUtil.EQUALS)
//                                .append(UrlUtil.encode(val)).append(StringUtil.AMP);
//                    }
//                }
//                buffer.deleteCharAt(buffer.length() - 1);
//                return buffer.toString();
//            }
            return url;
        }

        public HttpResponse convertResponse(HttpURLConnection connection, HttpRequest request) throws IOException {
            int status = connection.getResponseCode();
            String reason = connection.getResponseMessage();

            if (status < 0) {
                throw new IOException(StringUtil.format("Invalid status({}) executing {} {}", status,
                        connection.getRequestMethod(), connection.getURL()));
            }

            Map<String, List<String>> headers = connection.getHeaderFields();

            int length = connection.getContentLength();
            InputStream stream;
            if (status >= 400) {
                stream = connection.getErrorStream();
            } else {
                stream = connection.getInputStream();
            }
            return HttpResponse.builder()
                    .rawStatus(status)
                    .reason(reason)
                    .headers(new HttpHeaders(headers))
                    .request(request)
                    .body(stream, length)
                    .build();
        }
    }


    /**
     * Value for the Content-Encoding header that indicates that GZIP encoding is in use.
     */
    String ENCODING_GZIP = "gzip";

    /**
     * Value for the Content-Encoding header that indicates that DEFLATE encoding is in use.
     */
    String ENCODING_DEFLATE = "deflate";

}
