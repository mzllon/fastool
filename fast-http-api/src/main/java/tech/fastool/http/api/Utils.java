package tech.fastool.http.api;

import tech.fastool.core.io.IOes;
import tech.fastool.core.lang.Charsets;
import tech.fastool.core.lang.Collections;
import tech.fastool.core.lang.Objects;
import tech.fastool.core.lang.Strings;
import tech.fastool.core.lang.regex.PatternPool;
import tech.fastool.http.api.constants.HeaderName;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class Utils {

    public static String header(Map<String, List<String>> headers, String headerName) {
        return header(headers, headerName, false);
    }

    public static String header(Map<String, List<String>> headers, HeaderName headerName) {
        return header(headers, headerName, false);
    }

    public static String header(Map<String, List<String>> headers, String headerName, boolean ignoreCase) {
        if (Objects.isAnyEmpty(headers, headerName)) {
            return null;
        }
        List<String> values = null;
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            if (Strings.equals(entry.getKey(), headerName, ignoreCase)) {
                values = entry.getValue();
            }
        }
        if (Collections.isEmpty(values)) {
            return null;
        }
        return values.get(0);
    }

    public static String header(Map<String, List<String>> headers, HeaderName headerName, boolean ignoreCase) {
        Objects.requireNonNull(headerName, "headerName == null");
        return header(headers, headerName.toString(), ignoreCase);
    }

    public static String expandFilenameFromContentDisposition(Map<String, List<String>> headers) {
        String contentDisposition = header(headers, HeaderName.CONTENT_DISPOSITION, true);
        if (contentDisposition == null) {
            return null;
        }

        // 判断是不是ISO-8859-1
        if (contentDisposition.equals(new String(contentDisposition.getBytes(Charsets.ISO_8859_1), Charsets.ISO_8859_1))) {
            contentDisposition = new String(contentDisposition.getBytes(Charsets.ISO_8859_1));
        }

        Pattern pattern = PatternPool.get("filename=\"?(.+)\"?", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(contentDisposition);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public static void closeParts(HttpRequestBody requestBody) {
        if (requestBody instanceof HttpMultipartBody) {
            HttpMultipartBody multipartBody = (HttpMultipartBody) requestBody;
            List<HttpMultipartBody.Part> parts = multipartBody.getParts();
            if (Collections.isNotEmpty(parts)) {
                parts.forEach(part -> {
                    InputStream in = part.getIn();
                    if (in != null) {
                        IOes.closeQuietly(in);
                    }
                });
            }
        }
    }

}
