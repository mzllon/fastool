package tech.fastool.http.api.exceptions;

import tech.fastool.http.api.HttpHeaders;
import tech.fastool.http.api.HttpRequest;
import tech.fastool.http.api.HttpStatus;

import java.nio.charset.Charset;

/**
 * Exception thrown when an HTTP 5xx is received.
 *
 * @author miles.tang
 * @version 0.0.1
 * @date 2022-06-06
 */
public class HttpServerErrorException extends HttpClientStatusException {

    private static final long serialVersionUID = 1990L;

    public HttpServerErrorException(HttpStatus statusCode, HttpRequest request) {
        super(statusCode, request);
    }

    public HttpServerErrorException(HttpStatus statusCode, String reason, HttpRequest request) {
        super(statusCode, reason, request);
    }

    public HttpServerErrorException(HttpStatus statusCode, String reason, byte[] responseBody,
                                    Charset responseCharset, HttpRequest request) {
        super(statusCode, reason, responseBody, responseCharset, request);
    }

    public HttpServerErrorException(HttpStatus statusCode, String reason, HttpHeaders responseHeaders,
                                    byte[] responseBody, Charset responseCharset, HttpRequest request) {
        super(statusCode, reason, responseHeaders, responseBody, responseCharset, request);
    }


}
