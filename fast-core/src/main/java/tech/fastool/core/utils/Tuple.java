package tech.fastool.core.utils;

import tech.fastool.core.lang.ArrayIterator;
import tech.fastool.core.lang.CloneSupport;
import tech.fastool.core.lang.ObjectUtil;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * 元组: 不可变数组类型,用于多值返回,多值可以支持每个元素值类型不同
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class Tuple extends CloneSupport<Tuple> implements Iterable<Object>, Serializable {

    private static final long serialVersionUID = 2022L;

    private final Object[] elements;

    public Tuple(Object... elements) {
        this.elements = elements;
    }

    /**
     * 获得所有元素
     *
     * @return 获得所有元素
     */
    public Object[] getElements() {
        return elements;
    }

    /**
     * 获取指定位置元素
     *
     * @param <T>   返回对象类型
     * @param index 位置
     * @return 元素
     */
    @SuppressWarnings("unchecked")
    public <T> T get(int index) {
        return (T) elements[index];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tuple tuple = (Tuple) o;
        return Arrays.equals(elements, tuple.elements);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(elements);
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Object> iterator() {
        return new ArrayIterator<>(elements);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void forEach(Consumer<? super Object> action) {
        ObjectUtil.requireNonNull(action);
        for (Object element : elements) {
            action.accept(element);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return Arrays.toString(elements);
    }

}
