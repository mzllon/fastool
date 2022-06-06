package tech.fastool.core.lang;

import java.util.Iterator;

/**
 * 提供合成迭代器，提供了{@linkplain Iterator}和{@linkplain Iterable}的功能
 *
 * @param <E> 元素类型
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public interface CombineIterator<E> extends Iterator<E>, Iterable<E> {

    /**
     * 返回组合迭代器的自己
     *
     * @return {@linkplain CombineIterator}
     */
    @Override
    default Iterator<E> iterator() {
        return this;
    }

}
