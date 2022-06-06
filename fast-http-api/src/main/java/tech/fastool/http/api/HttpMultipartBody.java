package tech.fastool.http.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.fastool.core.lang.FileUtil;
import tech.fastool.core.lang.ObjectUtil;
import tech.fastool.core.lang.StringUtil;
import tech.fastool.core.utils.ContentType;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 含文件的表单
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class HttpMultipartBody extends HttpRequestBody {

    private final String boundary;

    private final List<Part> parts;

    HttpMultipartBody(Builder builder) {
        this.contentType = builder.contentType;
        this.boundary = builder.boundary;
        this.parts = Collections.unmodifiableList(builder.parts);
    }

    public String getBoundary() {
        return boundary;
    }

    public List<Part> getParts() {
        return parts;
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Part {

        private final String name;

        private String value;

        private final ContentType contentType;

        private HttpRequestBody body;

        private File file;

        private InputStream in;

        Part(String name, String value, ContentType contentType) {
            this.name = name;
            this.value = value;
            this.contentType = contentType;
        }

        Part(String name, HttpRequestBody value, ContentType contentType) {
            this.name = name;
            this.body = value;
            this.contentType = contentType;
        }

        Part(String name, String value, HttpRequestBody body, ContentType contentType) {
            this(name, value, contentType);
            this.body = body;
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        @NotNull
        public ContentType getContentType() {
            return contentType;
        }

        public HttpRequestBody getBody() {
            return body;
        }

        @Nullable
        public File getFile() {
            return file;
        }

        @Nullable
        public InputStream getIn() {
            return in;
        }

        public static Part create(String name, String value) {
            return create(name, value, (ContentType) null);
        }

        public static Part create(String name, String value, @Nullable ContentType contentType) {
            return new Part(name, value, contentType == null ? ContentType.DEFAULT_TEXT : contentType);
        }

        public static Part create(String name, @NotNull File file) {
            return create(name, file.getName(), file);
        }

        public static Part create(String name, String filename, @NotNull File file) {
            ObjectUtil.requireNonNull(file, "file == null");
            Part part = new Part(name, filename, ContentType.parseByFileExt(FileUtil.getFileExt(file)));
            part.file = file;
            return part;
        }

        public static Part create(String name, String filename, InputStream in) {
            return create(name, filename, in, null);
        }

        public static Part create(String name, String filename, @NotNull InputStream in,
                                  @Nullable ContentType contentType) {
            Part part = new Part(name, filename, contentType == null ? ContentType.DEFAULT_BINARY : contentType);
            part.in = in;
            return part;
        }

        public static Part create(String name, String filename, HttpRequestBody body) {
            return create(name, filename, body, null);
        }

        public static Part create(String name, String filename, HttpRequestBody body, @Nullable ContentType contentType) {
            Part part = new Part(name, filename, contentType == null ? ContentType.DEFAULT_BINARY : contentType);
            part.body = body;
            return part;
        }

    }

    public static class Builder {

        private String boundary;

        private ContentType contentType;

        private final List<Part> parts = new ArrayList<>();

        public Builder() {
            this.contentType = ContentType.MULTIPART_FORM_DATA;
        }

        public Builder boundary(String boundary) {
            this.contentType = ContentType.MULTIPART_FORM_DATA;
            this.boundary = boundary;
            return this;
        }

        public Builder contentType(@NotNull ContentType contentType) {
            this.contentType = ObjectUtil.requireNonNull(contentType, "contentType == null");
            return this;
        }

        public Builder add(@Nullable String name, @Nullable String value) {
            return add(name, value, (ContentType) null);
        }

        public Builder add(@Nullable String name, @Nullable String value, @Nullable ContentType contentType) {
            if (StringUtil.hasLength(name)) {
                parts.add(Part.create(name, value, contentType));
            }
            return this;
        }

        public Builder add(@Nullable String name, @Nullable File file) {
            if (StringUtil.hasLength(name) && file != null) {
                parts.add(Part.create(name, file));
            }
            return this;
        }

        public Builder add(@Nullable String name, @Nullable String filename, @Nullable HttpRequestBody body) {
            if (StringUtil.isAllNotBlank(name, filename) && body != null) {
                parts.add(Part.create(name, filename, body));
            }
            return this;
        }

        public Builder add(@NotNull Part part) {
            parts.add(ObjectUtil.requireNonNull(part, "part == null"));
            return this;
        }

        public HttpMultipartBody build() {
            return new HttpMultipartBody(this);
        }

    }

}
