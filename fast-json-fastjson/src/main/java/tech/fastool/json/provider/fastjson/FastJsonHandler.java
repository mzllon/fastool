package tech.fastool.json.provider.fastjson;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.filter.SimplePropertyPreFilter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.fastool.core.lang.ArrayUtil;
import tech.fastool.json.api.JsonHandler;
import tech.fastool.json.api.JsonRuntimeException;
import tech.fastool.json.api.annotation.JsonProviderName;

import java.io.Reader;
import java.lang.reflect.Type;

/**
 * 基于{@code Fastjson}的JSON处理器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
@JsonProviderName(value = "fastjson",index = 50)
public class FastJsonHandler implements JsonHandler {

    /**
     * 将Java对象序列化为JSON字符串
     *
     * @param src                 Java对象
     * @param ignorePropertyNames 忽略的属性名
     * @return JSON字符串
     * @throws JsonRuntimeException 序列化出现异常
     */
    @Override
    public String serialize(@NotNull Object src, @Nullable String... ignorePropertyNames) throws JsonRuntimeException {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        if (ArrayUtil.isNotEmpty(ignorePropertyNames)) {
            for (String ignorePropertyName : ignorePropertyNames) {
                filter.getExcludes().add(ignorePropertyName);
            }
        }
        return JSON.toJSONString(src, filter);
    }

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
        return JSON.toJSONString(src);
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
        return JSON.parseObject(json, typeOfT);
    }

    @Override
    public <T> T deserialize(@NotNull Reader reader, @NotNull Type typeOfT) throws JsonRuntimeException {
        throw new UnsupportedOperationException();
    }

}
