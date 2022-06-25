package tech.fastool.core.exceptions;

/**
 * Reflective Operation Exception
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class ReflectiveOperationRuntimeException extends GenericRuntimeException {

    /**
     * Reflective Operation Exception Constructor
     *
     * @param message 异常消息
     */
    public ReflectiveOperationRuntimeException(String message) {
        super(message);
    }

    /**
     * Reflective Operation Exception Constructor
     *
     * @param message 异常消息
     * @param cause   异常
     */
    public ReflectiveOperationRuntimeException(String message, ReflectiveOperationException cause) {
        super(message, cause);
    }

    /**
     * Reflective Operation Exception Constructor
     *
     * @param message 异常消息
     * @param cause   异常
     */
    public ReflectiveOperationRuntimeException(String message, Exception cause) {
        super(message, cause);
    }

}
