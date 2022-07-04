package tech.fastool.http.api.convenient;

import tech.fastool.core.convert.ConvertUtil;
import tech.fastool.core.lang.*;
import tech.fastool.core.utils.ContentType;
import tech.fastool.http.api.HttpFormBody;
import tech.fastool.http.api.HttpMultipartBody;
import tech.fastool.http.api.HttpRequestBody;
import tech.fastool.http.api.exceptions.HttpClientException;
import tech.fastool.json.api.JsonUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Post Body Request
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
@SuppressWarnings("unchecked")
public class BaseBodyRequest<Req extends BaseBodyRequest<Req>> extends AbstractBaseRequest<Req> {

    protected boolean isMultipart;

    protected Map<String, String> params;

    protected List<HttpMultipartBody.Part> parts;

    protected HttpRequestBody httpRequestBody;

    public BaseBodyRequest() {
        super();
        this.params = new LinkedHashMap<>();
        this.parts = new LinkedList<>();
    }


    /**
     * 是否强制开启文件上传(multipart/form-data)，如果框架检测到有文件上传则该方法设置无效
     *
     * @param isMultipart 如果值为{@code true}则开启，否则关闭
     * @return {@link Req}
     */
    public Req isMultipart(boolean isMultipart) {
        this.isMultipart = isMultipart;
        return (Req) this;
    }

    /**
     * 设置提交的请求参数及其值
     *
     * @param name  参数名
     * @param value 参数值
     * @return {@linkplain PostRequest}
     */
    public Req param(String name, Object value) {
        if (StringUtil.hasLength(name) && value != null) {
            if (value instanceof Collection<?>) {
                ((Collection<?>) value).forEach((Consumer<Object>) obj -> processParam(name, obj));
            } else {
                processParam(name, value);
            }
        }
        return (Req) this;
    }

    /**
     * 设置提交的请求参数及其值
     *
     * @param parameters 键值对列表
     * @return {@linkplain PostRequest}
     */
    public Req param(Map<String, ?> parameters) {
        if (MapUtil.isNotEmpty(parameters)) {
            parameters.forEach((BiConsumer<String, Object>) this::param);
        }
        return (Req) this;
    }

    /**
     * 设置提交的文件
     *
     * @param name       参数名
     * @param uploadFile 上传的文件
     * @return {@linkplain PostRequest}
     */
    public Req param(String name, File uploadFile) {
        if (StringUtil.hasLength(name) && uploadFile != null) {
            this.isMultipart = true;
            this.parts.add(HttpMultipartBody.Part.create(name, uploadFile));
        }
        return (Req) this;
    }

    /**
     * 设置提交的文件
     *
     * @param name       参数名
     * @param uploadFile 上传的文件
     * @param filename   文件名
     * @return {@linkplain PostRequest}
     */
    public Req param(String name, File uploadFile, String filename) {
        if (StringUtil.hasLength(name) && uploadFile != null) {
            this.isMultipart = true;
            if (StringUtil.hasLength(filename)) {
                this.parts.add(HttpMultipartBody.Part.create(name, filename, uploadFile));
            } else {
                this.parts.add(HttpMultipartBody.Part.create(name, uploadFile));
            }
        }
        return (Req) this;
    }

    /**
     * 设置提交的文件
     *
     * @param name        参数名
     * @param inputStream 上传数据流
     * @param streamName  数据流的标识
     * @return {@linkplain PostRequest}
     */
    public Req param(String name, InputStream inputStream, String streamName) {
        if (StringUtil.hasLength(name) && inputStream != null && StringUtil.hasLength(streamName)) {
            this.isMultipart = true;
            this.parts.add(HttpMultipartBody.Part.create(name, streamName, inputStream));
        }
        return (Req) this;
    }

    public Req param(String name, byte[] binaryData, String filename) {
        if (StringUtil.hasLength(name) && ArrayUtil.isNotEmpty(binaryData) && StringUtil.hasLength(filename)) {
            this.isMultipart = true;
            this.parts.add(HttpMultipartBody.Part.create(name, filename, new ByteArrayInputStream(binaryData)));
        }
        return (Req) this;
    }


    // region Request Body

    /**
     * POST提交一段json字符串
     *
     * @param value Java对象
     * @return {@link Req}
     */
    public Req json(Object value) {
        Objects.requireNonNull(value, "value == null");
        return this.json(JsonUtil.toJson(value));
    }

    /**
     * POST提交一段json字符串
     *
     * @param json json字符串
     * @return {@link Req}
     */
    public Req json(String json) {
        Objects.requireNonNull(json, "json == null");
        this.httpRequestBody = HttpRequestBody.create(ContentType.APPLICATION_JSON, json);
        return (Req) this;
    }

    /**
     * POST提交一段j文本内容
     *
     * @param text 文本字符串
     * @return {@link Req}
     */
    public Req text(String text) {
        Objects.requireNonNull(text, "text == null");
        this.httpRequestBody = HttpRequestBody.create(ContentType.TEXT_PLAIN, text);
        return (Req) this;
    }

    /**
     * POST提交一段xml代码
     *
     * @param xml xml字符串
     * @return {@link Req}
     */
    public Req xml(String xml) {
        Objects.requireNonNull(xml, "xml == null");
        this.httpRequestBody = HttpRequestBody.create(ContentType.APPLICATION_XML, xml);
        return (Req) this;
    }

    /**
     * POST提交一段html代码
     *
     * @param html html字符串
     * @return {@link Req}
     */
    public Req html(String html) {
        Objects.requireNonNull(html, "html == null");
        this.httpRequestBody = HttpRequestBody.create(ContentType.APPLICATION_HTML, html);
        return (Req) this;
    }

    /**
     * POST提交一段javascript代码
     *
     * @param javascript 字符串
     * @return {@link Req}
     */
    public Req javascript(String javascript) {
        Objects.requireNonNull(javascript, "javascriptc == null");
        this.httpRequestBody = HttpRequestBody.create(ContentType.APPLICATION_JAVASCRIPT, javascript);
        return (Req) this;
    }

    // endregion


    /**
     * 构建{@linkplain HttpRequestBody}，根据不同的请求类型
     *
     * @return {@linkplain HttpRequestBody}
     */
    @Override
    protected HttpRequestBody generateRequestBody() {
        if (httpRequestBody != null) {
            return httpRequestBody;
        }
        if (isMultipart) {
            HttpMultipartBody.Builder builder = HttpMultipartBody.builder();
            if (MapUtil.isNotEmpty(params)) {
                params.forEach(builder::add);
            }
            if (ListUtil.isNotEmpty(parts)) {
                parts.forEach(builder::add);
            }
            return builder.build();
        } else {
            HttpFormBody.Builder builder = HttpFormBody.builder();
            if (MapUtil.isNotEmpty(params)) {
                params.forEach(builder::add);
            }
            return builder.build();
        }
    }

    private void processParam(String name, Object value) {
        if (value != null) {
            if (value instanceof File) {
                this.param(name, (File) value);
            } else if (value instanceof Path) {
                this.param(name, ((Path) value).toFile());
            } else if ((value instanceof byte[]) || (value instanceof InputStream)) {
                throw new HttpClientException("Please use overload method[BaseBodyRequest#param(...)] instead.");
            } else if (value instanceof URL) {
                URL url = (URL) value;
                this.param(name, UrlUtil.openStream(url), FileUtil.getFilename(url.getFile()));
            } else if (value instanceof HttpMultipartBody.Part) {
                this.parts.add((HttpMultipartBody.Part) value);
                this.isMultipart = true;
            } else {
                this.params.put(name, ConvertUtil.toStr(value));
            }
        }
    }

}
