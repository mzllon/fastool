package tech.fastool.core.convert;

import lombok.experimental.UtilityClass;
import tech.fastool.core.exceptions.ConverterRuntimeException;

/**
 * 转换工具类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
@UtilityClass
public class ConvertUtil {

    /**
     * 转换指定类型的值，不抛异常转换<br>
     * 当转换失败时返回默认值
     *
     * @param <T>          目标泛型
     * @param clazz        目标类型
     * @param value        值
     * @param defaultValue 默认值
     * @return 转换后的值
     * @throws ConverterRuntimeException 异常
     */
    public static <T> T convert(Class<T> clazz, Object value, T defaultValue) throws ConverterRuntimeException {
        return null;
    }



}
