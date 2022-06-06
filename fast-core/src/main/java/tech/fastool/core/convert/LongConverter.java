package tech.fastool.core.convert;

import tech.fastool.core.exceptions.ConverterRuntimeException;
import tech.fastool.core.lang.NumberUtil;
import tech.fastool.core.lang.Singletons;
import tech.fastool.core.lang.StringUtil;

import java.util.Date;

/**
 * {@linkplain Long} 转换器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class LongConverter extends AbstractConverter<Long> {

    public static LongConverter getInstance() {
        return Singletons.get(LongConverter.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Long handleInternal(Object input) {
        if (input == null) {
            return null;
        } else if (input instanceof Boolean) {
            return ((Boolean) input) ? 1L : 0L;
        } else if (input instanceof Number) {
            return NumberUtil.convert((Number) input, Long.class);
        } else if (input instanceof String) {
            return NumberUtil.convert(NumberUtil.createNumber(input.toString()), Long.class);
        } else if (input instanceof Date) {
            return ((Date) input).getTime();
        }
        throw new ConverterRuntimeException(StringUtil.format("Can't cast {} to java.lang.Long", input));
    }

    /**
     * 获取此类实现类的反省类型
     *
     * @return 此类的泛型类型，坑你为{@code null}
     */
    @Override
    public Class<Long> getTargetClass() {
        return Long.class;
    }

    @Override
    public String toString() {
        return "LongConverter";
    }

}
