package tech.fastool.core.filter;

/**
 * Filter
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-03-27
 */
@FunctionalInterface
public interface Filter<E> {

    /**
     * 是否接受该对象
     *
     * @param ele 当前对象
     * @return 接受则返回{@code true},否则返回{@code false}
     */
    boolean accept(E ele);

}
