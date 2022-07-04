package tech.fastool.core.lang.tuple;

import tech.fastool.core.lang.ArrayUtil;

/**
 * 空元组
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-07-03
 */
public class Tuple0 extends BaseTuple {

    private Tuple0() {
    }

    /**
     * 返回元组中所有的元素
     *
     * @return 元素列表
     */
    @Override
    public Object[] elements() {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    /**
     * 返回{@linkplain Tuple0}的单例对象
     *
     * @return {@linkplain Tuple0}对象
     */
    public static Tuple0 instance() {
        return INSTANCE;
    }

    /**
     * 返回元组元素个数
     *
     * @return 元素个数
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * Tuple0 单例模式
     */
    private static final Tuple0 INSTANCE = new Tuple0();

}
