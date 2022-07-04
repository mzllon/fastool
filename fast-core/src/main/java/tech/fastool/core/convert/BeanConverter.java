package tech.fastool.core.convert;

import tech.fastool.core.lang.Beans;
import tech.fastool.core.lang.reflect.Reflects;

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
        R result = Reflects.newInstance(beanClass);
        Beans.copyProperties(value, result);
        return result;
    }

    @Override
    public Class<R> getTargetClass() {
        return beanClass;
    }

}
