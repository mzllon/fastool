package tech.fastool.core.lang;

/**
 * Builder Interface
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-05
 */
@FunctionalInterface
public interface Builder<T> {

    /**
     * 构建
     *
     * @return 被构建的对象
     */
    T build();

}
