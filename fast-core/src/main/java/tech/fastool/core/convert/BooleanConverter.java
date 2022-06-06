package tech.fastool.core.convert;

import tech.fastool.core.lang.Singletons;
import tech.fastool.core.utils.BooleanEvaluator;

/**
 * 布尔转换器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class BooleanConverter extends AbstractConverter<Boolean> {

    private static final long serialVersionUID = 2022L;

    /**
     * 返回{@linkplain  BooleanConverter}实例对象
     *
     * @return {@linkplain  BooleanConverter}
     */
    public static BooleanConverter getInstance() {
        return Singletons.get(BooleanConverter.class);
    }

    @Override
    protected Boolean handleInternal(Object value) {
        if (value instanceof Number) {
            // 0为false，其它数字为true
            return 0 != ((Number) value).doubleValue();
        }
        return BooleanEvaluator.DEFAULT_TRUE_EVALUATOR.evalTrue(execToStr(value));
    }

    /**
     * 获取此类实现类的反省类型
     *
     * @return 此类的泛型类型，坑你为{@code null}
     */
    @Override
    public Class<Boolean> getTargetClass() {
        return Boolean.class;
    }

    @Override
    public String toString() {
        return "BooleanConverter";
    }

}
