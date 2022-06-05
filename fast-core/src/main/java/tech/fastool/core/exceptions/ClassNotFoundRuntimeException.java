package tech.fastool.core.exceptions;

/**
 * Class Not Found Exception
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class ClassNotFoundRuntimeException extends GenericRuntimeException {

    private static final long serialVersionUID = 2022L;

    /**
     * Class Not Found Exception Constructor
     *
     * @param cause 异常
     */
    public ClassNotFoundRuntimeException(ClassNotFoundException cause) {
        super(cause);
    }

}
