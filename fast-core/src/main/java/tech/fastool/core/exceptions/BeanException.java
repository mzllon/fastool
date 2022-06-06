package tech.fastool.core.exceptions;

/**
 * Bean Exception
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class BeanException extends GenericRuntimeException {

    private static final long serialVersionUID = 2022L;

    /**
     * Bean Exception Constructor
     *
     * @param message 异常消息
     */
    public BeanException(String message) {
        super(message);
    }

    /**
     * Bean Exception Constructor
     *
     * @param message 异常消息
     * @param cause   异常
     */
    public BeanException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Bean Exception Constructor
     *
     * @param cause 异常
     */
    public BeanException(Throwable cause) {
        super(cause);
    }

}
