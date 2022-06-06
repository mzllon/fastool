package tech.fastool.core.lang;

import tech.fastool.core.exceptions.ArrayIteratorException;

import java.io.Serializable;
import java.util.NoSuchElementException;

/**
 * 数组迭代器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class ArrayIterator<E> implements CombineIterator<E>, Serializable {

    private static final long serialVersionUID = 2022L;

    /**
     * 数组
     */
    private final E[] values;

    /**
     * 起始位置
     */
    private final int start;

    /**
     * 结束位置
     */
    private final int end;

    /**
     * 当前位置
     */
    private int current;

    /**
     * Array Iterator 构造
     *
     * @param values 数组
     * @throws NullPointerException 如果数组为空则抛出
     */
    public ArrayIterator(final E[] values) {
        this(values, 0);
    }

    /**
     * Array Iterator 构造
     *
     * @param values 数组
     * @param start  开始位置，支持负数，表示从最后第几个开始
     * @throws NullPointerException 如果数组为空则抛出
     */
    public ArrayIterator(final E[] values, final int start) {
        this(values, start, values.length - 1);
    }

    /**
     * Array Iterator 构造。开始位置不能大于结束位置
     *
     * @param values 数组
     * @param start  开始位置，支持负数，表示从最后第几个开始
     * @param end    结束位置，支持负数，表示从最后第几个开始
     * @throws NullPointerException 如果数组为空则抛出
     */
    public ArrayIterator(final E[] values, final int start, final int end) {
        this.values = values;
        int length = values.length;

        if (start >= 0) {
            if (start >= length) {
                throw new ArrayIteratorException("The start index [" + start + "] is out of array range [" + length + "]");
            }
            this.start = start;
        } else {
            int startIndex = length - Math.abs(start);
            if (startIndex < 0) {
                throw new ArrayIteratorException("The start index [" + Math.abs(start) +
                        "] is out of array range [" + length + "]");
            }
            this.start = startIndex;
        }

        if (end >= 0) {
            if (end >= length) {
                throw new ArrayIteratorException("The end index [" + end + "] is out of array range [" + length + "]");
            }
            this.end = end + 1;
        } else {
            int endIndex = length - Math.abs(end);
            if (endIndex < 0) {
                throw new ArrayIteratorException("The end index [" + Math.abs(end) +
                        "] is out of array range [" + length + "]");
            }
            this.end = endIndex + 1;
        }

        if (start > end) {
            throw new ArrayIteratorException("The start index is more than end index");
        }
        this.current = this.start;
    }

    /**
     * 如果迭代器还有更多元素则返回{@code true}，否则返回{@code false}
     */
    @Override
    public boolean hasNext() {
        return (this.current < this.end);
    }

    /**
     * 数组不支持此操作
     */
    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    @Override
    public E next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return values[current++];
    }

    /**
     * 重置数组位置
     */
    public void reset() {
        this.current = this.start;
    }

}
