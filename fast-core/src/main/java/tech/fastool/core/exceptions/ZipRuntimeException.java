package tech.fastool.core.exceptions;

import java.io.IOException;

/**
 * Zip Runtime Exception
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class ZipRuntimeException extends GenericRuntimeException {

    private static final long serialVersionUID = 2022L;

    /**
     * Zip Runtime Exception Constructor
     *
     * @param message 异常消息
     */
    public ZipRuntimeException(String message) {
        super(message);
    }

    /**
     * Zip Runtime Exception Constructor
     *
     * @param e IO异常
     */
    public ZipRuntimeException(IOException e) {
        super(e);
    }

}
