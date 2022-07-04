package tech.fastool.http.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.fastool.core.io.IoUtil;
import tech.fastool.core.lang.CharsetUtil;
import tech.fastool.core.lang.FileUtil;
import tech.fastool.core.lang.Objects;
import tech.fastool.core.utils.ContentType;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * HttpRequest Body
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class HttpRequestBody {

    protected ContentType contentType;

    private byte[] data;

    protected HttpRequestBody() {
        this.contentType = ContentType.ALL;
    }

    protected HttpRequestBody(ContentType contentType, byte[] data) {
        this.contentType = contentType;
        this.data = data;
    }

    public int contentLength() {
        return data != null ? data.length : 0;
    }

    public ContentType contentType() {
        return contentType;
    }

    public byte[] getData() {
        return data;
    }

    /**
     * 返回编码
     *
     * @return 编码，可能为{@code null}
     */
    public Charset getCharset() {
        return contentType != null ? contentType.getCharset() : CharsetUtil.UTF_8;
    }

    public static HttpRequestBody create(@Nullable ContentType contentType, @NotNull String content) {
        Charset encoding = null;
        if (contentType != null) {
            encoding = contentType.getCharset();
        }
        byte[] data = content.getBytes(CharsetUtil.getCharset(encoding, CharsetUtil.UTF_8));
        return new HttpRequestBody(contentType, data);
    }

    public static HttpRequestBody create(@Nullable ContentType contentType, byte[] data) {
        return new HttpRequestBody(contentType, data);
    }

    public static HttpRequestBody create(@Nullable ContentType contentType, @NotNull File file) {
        Objects.requireNonNull(file, "file == null");
        return new HttpRequestBody(contentType, FileUtil.readBytes(file));
    }

    public static HttpRequestBody create(@Nullable ContentType contentType, InputStream in) {
        return new HttpRequestBody(contentType, IoUtil.readBytes(Objects.requireNonNull(in, "in == null")));
    }

}
