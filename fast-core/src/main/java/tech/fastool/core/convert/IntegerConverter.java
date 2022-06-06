package tech.fastool.core.convert;

import tech.fastool.core.exceptions.ConverterRuntimeException;
import tech.fastool.core.lang.NumberUtil;
import tech.fastool.core.lang.Singletons;
import tech.fastool.core.lang.StringUtil;

/**
 * {@linkplain Integer} 转换器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class IntegerConverter extends AbstractConverter<Integer> {

    /**
     * 返回{@linkplain  IntegerConverter}实例对象
     *
     * @return {@linkplain  IntegerConverter}
     */
    public static IntegerConverter getInstance() {
        return Singletons.get(IntegerConverter.class);
    }

    @Override
    protected Integer handleInternal(Object input) {
        if (input == null) {
            return null;
        } else if (input instanceof Boolean) {
            return ((Boolean) input) ? 1 : 0;
        } else if (input instanceof Number) {
            return NumberUtil.convert((Number) input, Integer.class);
        } else if (input instanceof String) {
            Number number = NumberUtil.createNumber(input.toString());
            if (number == null) {
                return null;
            }
            return NumberUtil.convert(number, Integer.class);
        }
        throw new ConverterRuntimeException(StringUtil.format("Can't cast {} to java.lang.Integer", input));
    }

    /**
     * 获取此类实现类的反省类型
     *
     * @return 此类的泛型类型，坑你为{@code null}
     */
    @Override
    public Class<Integer> getTargetClass() {
        return Integer.class;
    }

    @Override
    public String toString() {
        return "IntegerConverter";
    }

}
