package tech.fastool.core.exceptions;

/**
 * 克隆异常
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-05
 */
public class CloneRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 2022L;

    /**
     * Clone Exception Constructor
     *
     * @param cause 异常
     */
    public CloneRuntimeException(CloneNotSupportedException cause) {
        super(cause);
    }

}
