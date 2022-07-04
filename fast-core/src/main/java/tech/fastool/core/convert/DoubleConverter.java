package tech.fastool.core.convert;

import tech.fastool.core.exceptions.ConverterRuntimeException;
import tech.fastool.core.lang.Numbers;
import tech.fastool.core.lang.Singletons;
import tech.fastool.core.lang.Strings;

/**
 * {@linkplain Double} 转换器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class DoubleConverter extends AbstractConverter<Double> {

    private static final long serialVersionUID = 2022L;

    public static DoubleConverter getInstance() {
        return Singletons.get(DoubleConverter.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Double handleInternal(Object input) {
        if (input == null) {
            return null;
        }
        if (input instanceof Boolean) {
            return ((Boolean) input) ? 1D : 0D;
        } else if (input instanceof Number) {
            return Numbers.convert((Number) input, Double.class);
        } else if (input instanceof String) {
            return Numbers.convert(Numbers.createNumber(input.toString()), Double.class);
        }
        throw new ConverterRuntimeException(Strings.format("Can't cast {} to java.lang.Double", input));
    }

    /**
     * 获取此类实现类的反省类型
     *
     * @return 此类的泛型类型，坑你为{@code null}
     */
    @Override
    public Class<Double> getTargetClass() {
        return Double.class;
    }

    @Override
    public String toString() {
        return "DoubleConverter";
    }


}
