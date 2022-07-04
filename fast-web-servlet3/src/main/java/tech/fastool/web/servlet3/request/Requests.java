package tech.fastool.web.servlet3.request;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Nullable;
import tech.fastool.core.exceptions.IoRuntimeException;
import tech.fastool.core.io.FastByteArrayOutputStream;
import tech.fastool.core.io.IOes;
import tech.fastool.core.lang.Charsets;
import tech.fastool.core.lang.Objects;
import tech.fastool.core.lang.Strings;
import tech.fastool.json.api.BaseTypeRef;
import tech.fastool.json.api.Jsons;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Request工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
@UtilityClass
public final class Requests {

    /**
     * 获取用户的真正IP地址
     *
     * @param request request对象
     * @return 返回用户的IP地址
     * @see Ips#getClientIp(HttpServletRequest)
     */
    public static String getClientIp(HttpServletRequest request) {
        return Ips.getClientIp(request);
    }

    /**
     * 读取{@linkplain HttpServletRequest}的body内容，并转为对应的JavaBean
     *
     * @param request HTTP请求对象
     * @param clazz   目标类型
     * @param <T>     泛型类型
     * @return 目标对象
     */
    public static <T> T read2Bean(@Nullable HttpServletRequest request, Class<T> clazz) {
        String body = read2Bean(request);
        if (Strings.isBlank(body)) {
            return null;
        }
        return (Strings.isEmpty(body) ? null : Jsons.fromJson(body,
                Objects.requireNonNull(clazz, "clazz == null")));
    }

    /**
     * 读取{@linkplain HttpServletRequest}的body内容，并转为对应的JavaBean
     *
     * @param request HTTP请求对象
     * @param typeRef 目标类型
     * @param <T>     泛型类型
     * @return 目标对象
     */
    public static <T> T read2Bean(@Nullable HttpServletRequest request, BaseTypeRef<T> typeRef) {
        String body = read2Bean(request);
        return (Strings.isEmpty(body) ? null : Jsons.fromJson(body, typeRef.getType()));
    }

    /**
     * 读取{@linkplain HttpServletRequest}的body内容
     *
     * @param request HTTP请求对象
     * @return body的内容
     */
    @Nullable
    public static String read2Bean(@Nullable HttpServletRequest request) {
        if (Objects.isNull(request)) {
            return null;
        }
        int contentLength = request.getContentLength();
        if (contentLength <= 0) {
            return null;
        }
        FastByteArrayOutputStream outputStream = new FastByteArrayOutputStream(contentLength);
        try {
            IOes.copy(request.getInputStream(), outputStream);
            return outputStream.toString(Charsets.forName(request.getCharacterEncoding()));
        } catch (IOException e) {
            throw new IoRuntimeException(e);
        }
    }

}
