package tech.fastool.core.exceptions;

/**
 * 数组为空异常
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-05-30
 */
public class ArrayEmptyException extends GenericRuntimeException {

    private static final long serialVersionUID = 2022L;

    /**
     * Array Empty Exception Constructor
     */
    public ArrayEmptyException() {
        super();
    }

    /**
     * Array Empty Exception Constructor
     *
     * @param message 异常消息
     */
    public ArrayEmptyException(String message) {
        super(message);
    }

}
