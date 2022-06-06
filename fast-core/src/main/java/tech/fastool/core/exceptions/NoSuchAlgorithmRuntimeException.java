package tech.fastool.core.exceptions;

import java.security.NoSuchAlgorithmException;

/**
 * No Such Algorithm Exception
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class NoSuchAlgorithmRuntimeException extends GenericRuntimeException {

    private static final long serialVersionUID = 2022L;

    /**
     * Constructor
     *
     * @param cause 异常类
     */
    public NoSuchAlgorithmRuntimeException(NoSuchAlgorithmException cause) {
        super(cause);
    }
}
