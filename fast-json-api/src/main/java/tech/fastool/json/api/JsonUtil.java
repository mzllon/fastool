package tech.fastool.json.api;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.fastool.core.lang.ObjectUtil;

import java.lang.reflect.Type;

/**
 * 提供默认的工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
@UtilityClass
public class JsonUtil {

    /**
     * 自定义的JSON引擎
     */
    private static JsonAdapter customJson = null;

    /**
     * 设置自定义的JSON引擎
     *
     * @param customJson 自定义的JSON引擎
     */
    public static void setCustomJson(@Nullable JsonAdapter customJson) {
        JsonUtil.customJson = customJson;
    }

    /**
     * 将对象转为JSON字符串
     *
     * @param src 对象
     * @return JSON字符串或空字符串
     * @see #toJson(Object, Type)
     */
    @Nullable
    public static String toJson(@Nullable Object src) {
        if (src == null) {
            return null;
        }
        return toJson(src, src.getClass());
    }

    /**
     * 将对象转为JSON字符串
     *
     * @param src       对象
     * @param typeOfSrc 对象的某个类型
     * @return JSON字符串或空字符串
     */
    @Nullable
    public static String toJson(@Nullable Object src, @Nullable Type typeOfSrc) {
        if (src == null) {
            return null;
        }
        return getJSON(customJson).toJson(src, typeOfSrc);
    }

    /**
     * 将JSON字符串转为Java对象
     *
     * @param json  字符串，可以为空
     * @param clazz 类型
     * @param <T>   泛型
     * @return 对象
     */
    public static <T> T fromJson(@Nullable String json, Class<T> clazz) {
        return getJSON(customJson).fromJson(json, clazz);
    }

    /**
     * 将JSON字符串转为Java对象
     *
     * @param json    字符串，可以为空
     * @param typeOfT 类型
     * @param <T>     泛型
     * @return 对象
     */
    public static <T> T fromJson(@Nullable String json, Type typeOfT) {
        return getJSON(customJson).fromJson(json, typeOfT);
    }

    /**
     * 将对象转为JSON字符串
     *
     * @param src  对象
     * @param json 指定JSON引擎
     * @return JSON字符串或空字符串
     */
    public static String toJson(@Nullable Object src, @Nullable JsonAdapter json) {
        if (src == null) {
            return null;
        }
        return toJson(src, src.getClass(), json);
    }

    /**
     * 将对象转为JSON字符串
     *
     * @param src       对象
     * @param typeOfSrc 类型
     * @param json      指定JSON引擎
     * @return JSON字符串或空字符串
     */
    public static String toJson(@Nullable Object src, @Nullable Type typeOfSrc, @Nullable JsonAdapter json) {
        if (src == null) {
            return null;
        }
        return getJSON(json).toJson(src, typeOfSrc);
    }

    /**
     * 将JSON字符串转为Java对象
     *
     * @param json  字符串，可以为空
     * @param clazz 类型
     * @param <T>   泛型
     * @return 对象
     */
    public static <T> T fromJson(String text, Class<T> clazz, @Nullable JsonAdapter json) {
        return getJSON(json).fromJson(text, clazz);
    }

    /**
     * 将JSON字符串转为Java对象
     *
     * @param json    字符串，可以为空
     * @param typeOfT 类型
     * @param <T>     泛型
     * @return 对象
     */
    public static <T> T fromJson(String text, Type typeOfT, @Nullable JsonAdapter json) {
        return getJSON(json).fromJson(text, typeOfT);
    }

    /**
     * 将JSON字符串转为Java对象
     *
     * @param text    字符串，可为空
     * @param typeRef 类型
     * @param <T>     泛型
     * @return 对象
     */
    public static <T> T fromJson(String text, @NotNull BaseTypeRef<T> typeRef) {
        return getJSON(null).fromJson(text, ObjectUtil.requireNonNull(typeRef).getType());
    }

    /**
     * 将JSON字符串转为Java对象
     *
     * @param text    字符串，可为空
     * @param typeRef 类型
     * @param jsonObj 指定JSON引擎
     * @param <T>     泛型
     * @return 对象
     */
    public static <T> T fromJson(String text, @NotNull BaseTypeRef<T> typeRef, JsonAdapter jsonObj) {
        return getJSON(jsonObj).fromJson(text, ObjectUtil.requireNonNull(typeRef).getType());
    }

    /**
     * 返回JSON引擎
     *
     * @param json 指定一个JSON引擎，可以为{@code null}
     * @return JSON引擎
     */
    private static JsonAdapter getJSON(JsonAdapter json) {
        JsonAdapter result = json != null ? json : JsonFactory.get();
        return ObjectUtil.requireNonNull(result, "JSON Provider Cannot find!");
    }

}
