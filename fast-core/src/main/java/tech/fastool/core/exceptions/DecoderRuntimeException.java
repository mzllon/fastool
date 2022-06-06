package tech.fastool.core.exceptions;

/**
 * 解码异常类
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class DecoderRuntimeException extends GenericRuntimeException {

    private static final long serialVersionUID = 2022L;

    /**
     * Decoder Exception Constructor
     *
     * @param message 异常消息
     */
    public DecoderRuntimeException(String message) {
        super(message);
    }

}
