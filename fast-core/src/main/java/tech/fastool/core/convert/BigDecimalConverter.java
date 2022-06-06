package tech.fastool.core.convert;

import tech.fastool.core.lang.NumberUtil;
import tech.fastool.core.lang.Singletons;

import java.math.BigDecimal;

/**
 * BigDecimal Converter
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class BigDecimalConverter extends AbstractConverter<BigDecimal> {

    private static final long serialVersionUID = 2022L;

    @Override
    protected BigDecimal handleInternal(Object value) {
        if (value instanceof Number) {
            return NumberUtil.createBigDecimal((Number) value);
        } else if (value instanceof Boolean) {
            return BigDecimal.valueOf(((Boolean) value) ? 1 : 0);
        }
        return NumberUtil.createBigDecimal(execToStr(value));
    }

    /**
     * 获取此类实现类的反省类型
     *
     * @return 此类的泛型类型，坑你为{@code null}
     */
    @Override
    public Class<BigDecimal> getTargetClass() {
        return BigDecimal.class;
    }

    /**
     * 返回{@linkplain  BigDecimalConverter}实例对象
     *
     * @return {@linkplain  BigDecimalConverter}
     */
    public static BigDecimalConverter getInstance() {
        return Singletons.get(BigDecimalConverter.class);
    }

}
