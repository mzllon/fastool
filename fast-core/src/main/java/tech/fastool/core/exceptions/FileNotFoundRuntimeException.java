package tech.fastool.core.exceptions;

/**
 * File Not Found Exception
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class FileNotFoundRuntimeException extends IoRuntimeException {

    private static final long serialVersionUID = 2022L;

    /**
     * File Not Found Exception Constructor
     *
     * @param message 异常消息
     */
    public FileNotFoundRuntimeException(String message) {
        super(message);
    }

}
