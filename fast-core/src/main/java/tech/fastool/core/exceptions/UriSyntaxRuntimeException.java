package tech.fastool.core.exceptions;

import java.net.URISyntaxException;

/**
 * URI Syntax Exception
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class UriSyntaxRuntimeException extends GenericRuntimeException {

    private static final long serialVersionUID = 2022L;

    /**
     * URI Syntax Exception Constructor
     *
     * @param cause URI Syntax Exception
     */
    public UriSyntaxRuntimeException(URISyntaxException cause) {
        super(cause);
    }

}
