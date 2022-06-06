package tech.fastool.core.convert;

import tech.fastool.core.exceptions.ConverterRuntimeException;
import tech.fastool.core.lang.BeanUtil;
import tech.fastool.core.lang.Singletons;
import tech.fastool.core.lang.StringUtil;
import tech.fastool.core.lang.reflect.ReflectUtil;
import tech.fastool.core.lang.reflect.TypeUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 转换器服务
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class ConverterRegistry {

    private Map<Class<?>, Converter<?>> builtin;

    private Map<Class<?>, Converter<?>> custom;


    private ConverterRegistry() {
        this.initBuiltIn();
    }

    private void initBuiltIn() {
        builtin = new ConcurrentHashMap<>(32);
        custom = new ConcurrentHashMap<>(32);

        // 原始类型转换器
        builtin.put(boolean.class, BooleanConverter.getInstance());
        builtin.put(byte.class, ByteConverter.getInstance());
        builtin.put(short.class, ShortConverter.getInstance());
        builtin.put(int.class, IntegerConverter.getInstance());
        builtin.put(long.class, LongConverter.getInstance());
        builtin.put(float.class, FloatConverter.getInstance());
        builtin.put(double.class, DoubleConverter.getInstance());
        builtin.put(char.class, CharacterConverter.getInstance());

        // 包装类转换器
        builtin.put(Boolean.class, BooleanConverter.getInstance());
        builtin.put(Byte.class, ByteConverter.getInstance());
        builtin.put(Short.class, ShortConverter.getInstance());
        builtin.put(Integer.class, IntegerConverter.getInstance());
        builtin.put(Long.class, LongConverter.getInstance());
        builtin.put(Float.class, FloatConverter.getInstance());
        builtin.put(Double.class, DoubleConverter.getInstance());
        builtin.put(Character.class, CharacterConverter.getInstance());
        builtin.put(BigDecimal.class, BigDecimalConverter.getInstance());
        builtin.put(BigInteger.class, BigIntegerConverter.getInstance());
        builtin.put(CharSequence.class, new StringConverter());
        builtin.put(String.class, new StringConverter());
    }

    /**
     * 注册一个自定义类型转换器
     *
     * @param clazz     类型
     * @param converter 对应的转换器
     */
    public <T> ConverterRegistry register(Class<T> clazz, Converter<T> converter) {
        custom.put(clazz, converter);
        return this;
    }

    /**
     * 注册一个自定义类型转换器
     *
     * @param clazz          类型
     * @param converterClass 对应的转换器
     */
    public <T> ConverterRegistry register(Class<T> clazz, Class<? extends Converter<T>> converterClass) {
        return register(clazz, ReflectUtil.newInstance(converterClass));
    }

    /**
     * 获得转换器
     *
     * @param clazz       类型
     * @param customFirst 优先返回自定义注册器
     * @param <T>         目标类型
     * @return 转换器
     */
    @SuppressWarnings("unchecked")
    public <T> Converter<T> getConverter(Class<T> clazz, boolean customFirst) {
        Converter<T> result = null;
        if (customFirst) {
            result = (Converter<T>) custom.get(clazz);
        }
        if (result == null) {
            result = (Converter<T>) builtin.get(clazz);
        }
        return result;
    }

    /**
     * 获取默认转换器
     *
     * @param clazz 类型
     * @param <T>   目标类型
     * @return 默认转换器
     */
    public <T> Converter< T> getDefaultConverter(Class<T> clazz) {
        return getConverter(clazz, false);
    }

    /**
     * 获取自定义转换器
     *
     * @param clazz 类型
     * @param <T>   目标类型
     * @return 自定义转换器
     */
    public <T> Converter< T> getCustomConverter(Class<T> clazz) {
        return getConverter(clazz, true);
    }

    /**
     * 将原对象转为目标类型的对象
     *
     * @param value       源对象
     * @param targetClass 目标对象类型
     * @param <T>         泛型类
     * @return 目标对象
     * @throws ConverterRuntimeException 转换异常
     */
    public <T> T convert(Object value, Class<T> targetClass) throws ConverterRuntimeException {
        return convert(value, targetClass, null);
    }

    /**
     * 将原对象转为目标类型的对象
     *
     * @param value        源对象
     * @param targetClass  目标对象类型
     * @param <T>          泛型类
     * @param defaultValue 默认值
     * @return 目标对象
     * @throws ConverterRuntimeException 转换异常
     */
    public <T> T convert(Object value, Class<T> targetClass, T defaultValue) throws ConverterRuntimeException {
        return convert(value, targetClass, defaultValue, true);
    }

    /**
     * 将原对象转为目标类型的对象
     *
     * @param value        源对象
     * @param targetClass  目标对象类型
     * @param <T>          泛型类
     * @param defaultValue 默认值
     * @param customFirst  优先返回自定义注册器
     * @return 目标对象
     * @throws ConverterRuntimeException 转换异常
     */
    @SuppressWarnings("unchecked")
    public <T> T convert(Object value, Class<T> targetClass, T defaultValue, boolean customFirst) throws ConverterRuntimeException {
        if (TypeUtil.isUnknown(targetClass) && null == defaultValue) {
            return (T) value;
        }
        if (value == null) {
            return defaultValue;
        }
        if (TypeUtil.isUnknown(targetClass)) {
            targetClass = (Class<T>) defaultValue.getClass();
        }

        Converter< T> converter = getConverter(targetClass, customFirst);
        if (converter != null) {
            return converter.handle(value, defaultValue);
        }

        // 尝试转Bean
        if (BeanUtil.isBeanType(targetClass)) {
            return new BeanConverter<>(targetClass).handle(value, defaultValue);
        }

        //
        throw new ConverterRuntimeException(StringUtil.format("Can not Converter from [{}] to [{}]", value.getClass(), targetClass));
    }

    /**
     * 获取单例
     *
     * @return {@linkplain ConverterRegistry} 单例
     */
    public static ConverterRegistry getInstance() {
        return Singletons.get(ConverterRegistry.class);
    }

}
