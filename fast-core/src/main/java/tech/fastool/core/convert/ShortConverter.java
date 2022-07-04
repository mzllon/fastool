package tech.fastool.core.convert;

import tech.fastool.core.lang.Numbers;
import tech.fastool.core.lang.Singletons;

/**
 * {@linkplain Short}转换器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class ShortConverter extends AbstractConverter<Short> {

    public static ShortConverter getInstance() {
        return Singletons.get(ShortConverter.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Short handleInternal(Object input) {
        Integer output = IntegerConverter.getInstance().handleInternal(input);
        return Numbers.convert(output, Short.class);
    }

    /**
     * 获取此类实现类的反省类型
     *
     * @return 此类的泛型类型，坑你为{@code null}
     */
    @Override
    public Class<Short> getTargetClass() {
        return Short.class;
    }

    @Override
    public String toString() {
        return "ShortConverter";
    }

}
