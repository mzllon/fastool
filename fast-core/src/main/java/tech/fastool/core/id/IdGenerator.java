package tech.fastool.core.id;

import java.util.function.Supplier;

/**
 * ID生成器
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public interface IdGenerator<T> extends Supplier<T> {

    /**
     * 返回ID
     *
     * @return ID
     */
    @Override
    T get();

}
