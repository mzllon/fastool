package tech.fastool.core.convert;

import tech.fastool.core.lang.BeanUtil;
import tech.fastool.core.lang.reflect.ReflectUtil;

/**
 * Bean Converter
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class BeanConverter<R> extends AbstractConverter< R> {

    private static final long serialVersionUID = 2022L;

    private final Class<R> beanClass;

    public BeanConverter(Class<R> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    protected R handleInternal(Object value) {
        R result = ReflectUtil.newInstance(beanClass);
        BeanUtil.copyProperties(value, result);
        return result;
    }

    @Override
    public Class<R> getTargetClass() {
        return beanClass;
    }

}
