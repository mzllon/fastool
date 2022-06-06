package tech.fastool.json.provider.gson;

import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.fastool.json.api.JsonHandler;
import tech.fastool.json.api.JsonRuntimeException;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * 基于{@code Gson}的JSON处理器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class GsonJsonHandler implements JsonHandler {

    private Gson gson;

    /**
     * 将Java对象序列化为JSON字符串
     *
     * @param src     Java对象
     * @param typeOfT 类型
     * @return JSON字符串
     * @throws JsonRuntimeException 序列化出现异常
     */
    @Override
    public String serialize(@NotNull Object src, @Nullable Type typeOfT) throws JsonRuntimeException {
        return (typeOfT == null) ? gson.toJson(src) : gson.toJson(src, typeOfT);
    }

    /**
     * 将JSON字符串放序列化为Java对象
     *
     * @param json    JSON字符串
     * @param typeOfT Java类型
     * @return Java对象
     * @throws JsonRuntimeException 反序列化出现异常
     */
    @Override
    public <T> T deserialize(@NotNull String json, @NotNull Type typeOfT) throws JsonRuntimeException {
        return gson.fromJson(json, typeOfT);
    }

    @Override
    public <T> T deserialize(@NotNull Reader reader, @NotNull Type typeOfT) throws JsonRuntimeException {
        return null;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

}
