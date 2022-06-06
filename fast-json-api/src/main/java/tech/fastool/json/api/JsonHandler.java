package tech.fastool.json.api;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * JSON Handler
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public interface JsonHandler {

    /**
     * 将Java对象序列化为JSON字符串
     *
     * @param src     Java对象
     * @param typeOfT 类型
     * @return JSON字符串
     * @throws JsonRuntimeException 序列化出现异常
     */
    String serialize(@NotNull Object src, @Nullable Type typeOfT) throws JsonRuntimeException;

    /**
     * 将JSON字符串放序列化为Java对象
     *
     * @param json    JSON字符串
     * @param typeOfT Java类型
     * @param <T>     泛型类型
     * @return Java对象
     * @throws JsonRuntimeException 反序列化出现异常
     */
    <T> T deserialize(@NotNull String json, @NotNull Type typeOfT) throws JsonRuntimeException;

    /**
     * 将{@linkplain Reader}内容转为Java对象
     *
     * @param reader  内容
     * @param typeOfT Java类型
     * @param <T>     泛型类型
     * @return Java对象
     * @throws JsonRuntimeException 反序列化异常
     */
    <T> T deserialize(@NotNull Reader reader, @NotNull Type typeOfT) throws JsonRuntimeException;

}
