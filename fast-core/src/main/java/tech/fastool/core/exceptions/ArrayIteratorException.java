package tech.fastool.core.exceptions;

/**
 * 数组迭代异常
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class ArrayIteratorException extends GenericRuntimeException {

    /**
     * Array Iterator Exception
     *
     * @param message 消息
     */
    public ArrayIteratorException(String message) {
        super(message);
    }

}
