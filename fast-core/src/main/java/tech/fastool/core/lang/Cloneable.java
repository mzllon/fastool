package tech.fastool.core.lang;

import tech.fastool.core.exceptions.CloneRuntimeException;

/**
 * 标记接口，标记类或接口可以被克隆
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-03-26
 */
public interface Cloneable<T> extends java.lang.Cloneable {

    /**
     * 克隆当前对象，浅复制
     *
     * @return 克隆后的对象
     * @throws CloneRuntimeException 克隆时抛出的异常
     */
    T clone() throws CloneRuntimeException;

}
