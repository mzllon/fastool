package tech.fastool.http.api.exceptions;

import tech.fastool.http.api.HttpHeaders;
import tech.fastool.http.api.HttpRequest;

import java.nio.charset.Charset;

/**
 * Exception thrown when an unknown (or custom) HTTP status code is received.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class UnknownHttpStatusCodeException extends HttpClientResponseException {

    private static final long serialVersionUID = 1990L;

    public UnknownHttpStatusCodeException(int rawStatus, String reason, HttpHeaders responseHeaders,
                                          byte[] responseBody, Charset responseCharset, HttpRequest request) {
        super("Unknown status code [" + rawStatus + "]" + " " + reason, rawStatus, reason, responseHeaders,
                responseBody, responseCharset, request);
    }

}
