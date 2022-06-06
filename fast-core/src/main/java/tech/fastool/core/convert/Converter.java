package tech.fastool.core.convert;

import tech.fastool.core.exceptions.ConverterRuntimeException;

/**
 * 转换器接口，定义了数据转为指定类型的数据
 *
 * @param <R> 输出参数类型
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-04-07
 */
public interface Converter<R> {

    /**
     * 将数据转为指定类型的数据
     *
     * @param src          输入的数据值
     * @param defaultValue 默认值
     * @return 转换后的值
     * @throws ConverterRuntimeException 转换异常
     */
    R handleQuietly(Object src, R defaultValue) throws ConverterRuntimeException;

}
