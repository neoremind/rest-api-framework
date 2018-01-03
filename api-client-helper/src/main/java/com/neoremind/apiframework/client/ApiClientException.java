package com.neoremind.apiframework.client;

import com.neoremind.apiframework.commons.ResponseWithCodeAndMsg;

/**
 * ApiClientException
 * <p>
 * If there is no exception but  <br/>
 * 1) server status code is not successful(not between 200 - 300), or <br/>
 * 2) server return {@link ResponseWithCodeAndMsg} but status ok is not OK,
 * then you will get a {@link ApiClientException}
 * which for case #1, framework helps you wraps the request and response text, but the details information is
 * omitted because response body stream can only be read once.
 * for case #2, {@link #ApiClientException(String)} will be used to fill in the error message.
 *
 * @author xu.zhang
 */
public class ApiClientException extends RuntimeException {

    private static final long serialVersionUID = 3222573813262320183L;

    public ApiClientException() {
    }

    public ApiClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiClientException(String message) {
        super(message);
    }

    public ApiClientException(Throwable cause) {
        super(cause);
    }

}
