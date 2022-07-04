package tech.fastool.core.lang.tuple;

/**
 * 元组: 不可变数组类型,用于多值返回,多值可以支持每个元素值类型不同
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class TupleN extends BaseTuple {

    private static final long serialVersionUID = 2022L;

    private final Object[] elements;

    public TupleN(Object... elements) {
        this.elements = elements;
    }

    /**
     * 获得所有元素
     *
     * @return 获得所有元素
     */
    public Object[] elements() {
        return elements;
    }

    /**
     * 返回元组元素个数
     *
     * @return 元素个数
     */
    @Override
    public int size() {
        return elements.length;
    }

}
