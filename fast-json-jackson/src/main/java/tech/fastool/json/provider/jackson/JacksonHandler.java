package tech.fastool.json.provider.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.fastool.core.lang.ArrayUtil;
import tech.fastool.core.lang.ObjectUtil;
import tech.fastool.json.api.BaseJsonHandler;
import tech.fastool.json.api.JsonRuntimeException;
import tech.fastool.json.api.annotation.JsonProviderName;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Jackson Handler
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
@JsonProviderName(value = "jackson", index = 30)
public class JacksonHandler extends BaseJsonHandler {

    private final ObjectMapper objectMapper;

    public JacksonHandler() {
        this(JacksonUtil.createObjectMapper());
    }

    public JacksonHandler(ObjectMapper objectMapper) {
        this.objectMapper = ObjectUtil.requireNonNull(objectMapper, "objectMapper == null");
    }


    /**
     * 将Java对象序列化为JSON字符串
     *
     * @param src                 Java对象
     * @param ignorePropertyNames 忽略的属性名
     * @return JSON字符串
     * @throws JsonRuntimeException 序列化出现异常
     */
    @Override
    public String doSerialize(@NotNull Object src, @Nullable String[] ignorePropertyNames) throws JsonRuntimeException {
        if (ArrayUtil.isNotEmpty(ignorePropertyNames)) {
            SimpleFilterProvider sfp = new SimpleFilterProvider();
            sfp.addFilter("fieldFilter", SimpleBeanPropertyFilter.serializeAllExcept(ignorePropertyNames));
            try {
                return objectMapper.copy().setFilterProvider(sfp).writeValueAsString(src);
            } catch (JsonProcessingException e) {
                throw new JsonRuntimeException(e);
            }
        } else {
            return serialize(src, (Type) null);
        }
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
    public String doSerialize(@NotNull Object src, @Nullable Type typeOfT) throws JsonRuntimeException {
        try {
            return objectMapper.writeValueAsString(src);
        } catch (JsonProcessingException e) {
            throw new JsonRuntimeException(e);
        }
    }

    /**
     * 将JSON字符串放序列化为Java对象
     *
     * @param json    JSON字符串
     * @param typeOfT Java类型
     * @return Java对象
     * @throws JsonRuntimeException 反序列化出现异常
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T doDeserialize(@NotNull String json, @NotNull Type typeOfT) throws JsonRuntimeException {
        try {
            if (JacksonUtil.isJacksonJavaType(typeOfT)) {
                return objectMapper.readValue(json, JacksonUtil.toJavaType(typeOfT));
            }
            // is primitive ?

            if (JacksonUtil.isClass(typeOfT)) {
                return objectMapper.readValue(json, (Class<T>) typeOfT);
            }

            if (JacksonUtil.isParameterizedType(typeOfT)) {
                return objectMapper.readValue(json, new TypeReference<T>() {
                    @Override
                    public Type getType() {
                        return typeOfT;
                    }
                });
            }
            return null;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

}
