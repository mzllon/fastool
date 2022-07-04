package tech.fastool.http.api.convenient;

import org.jetbrains.annotations.NotNull;
import tech.fastool.core.lang.Strings;
import tech.fastool.http.api.Factory;
import tech.fastool.http.api.HttpClient;

/**
 * HttpClient更高级的工具类入口
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class HttpClients {

    protected static HttpClient httpClient;

    static {
        httpClient = Factory.get().build(null);
    }

    /**
     * Get请求
     *
     * @param url 请求地址
     * @return {@link GetRequest}
     */
    public static GetRequest get(@NotNull String url) {
        return new GetRequest(url);
    }

    /**
     * Get请求
     *
     * @param urlPattern 请求地址
     * @return {@link GetRequest}
     */
    public static GetRequest get(@NotNull String urlPattern, Object... args) {
        return new GetRequest(Strings.format(urlPattern, args));
    }

    /**
     * FORM/POST表单提交
     *
     * @param url 提交地址
     * @return {@link PostRequest}
     */
    public static PostRequest post(String url) {
        return new PostRequest(url);
    }

    /**
     * FORM/POST表单提交
     *
     * @param urlPattern 提交地址
     * @return {@link PostRequest}
     */
    public static PostRequest post(String urlPattern, Object... args) {
        return new PostRequest(Strings.format(urlPattern, args));
    }

    /**
     * DELETE 请求
     *
     * @param url 请求地址
     * @return {@linkplain DeleteRequest}
     */
    public static DeleteRequest delete(@NotNull String url) {
        return new DeleteRequest(url);
    }

    /**
     * DELETE 请求
     *
     * @param urlPattern 请求地址
     * @return {@linkplain DeleteRequest}
     */
    public static DeleteRequest delete(@NotNull String urlPattern, Object... args) {
        return new DeleteRequest(Strings.format(urlPattern, args));
    }

    /**
     * HEAD 请求
     *
     * @param url 请求地址
     * @return {@linkplain HeadRequest}
     */
    public static HeadRequest head(@NotNull String url) {
        return new HeadRequest(url);
    }

    /**
     * HEAD 请求
     *
     * @param urlPattern 请求地址
     * @return {@linkplain HeadRequest}
     */
    public static HeadRequest head(@NotNull String urlPattern, Object... args) {
        return new HeadRequest(Strings.format(urlPattern, args));
    }

    /**
     * PATCH 请求
     *
     * @param url 请求地址
     * @return {@linkplain PatchRequest}
     */
    public static PatchRequest patch(@NotNull String url) {
        return new PatchRequest(url);
    }

    /**
     * PATCH 请求
     *
     * @param urlPattern 请求地址
     * @return {@linkplain PatchRequest}
     */
    public static PatchRequest patch(@NotNull String urlPattern, Object... args) {
        return new PatchRequest(Strings.format(urlPattern, args));
    }

    /**
     * PUT 请求
     *
     * @param url 请求地址
     * @return {@linkplain PutRequest}
     */
    public static PutRequest put(@NotNull String url) {
        return new PutRequest(url);
    }

    /**
     * PUT 请求
     *
     * @param urlPattern 请求地址
     * @return {@linkplain PutRequest}
     */
    public static PutRequest put(@NotNull String urlPattern, Object... args) {
        return new PutRequest(Strings.format(urlPattern, args));
    }

}
