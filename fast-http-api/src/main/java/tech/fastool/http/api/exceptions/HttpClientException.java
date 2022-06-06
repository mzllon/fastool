package tech.fastool.http.api.exceptions;

import tech.fastool.core.exceptions.GenericRuntimeException;

/**
 * Base class for exceptions thrown by {@link tech.fastool.http.api.HttpClient} whenever it encounters client-side HTTP errors.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class HttpClientException extends GenericRuntimeException {

    private static final long serialVersionUID = 1990L;

    public HttpClientException(String message) {
        super(message);
    }

    public HttpClientException(String message, Throwable ex) {
        super(message, ex);
    }

}
