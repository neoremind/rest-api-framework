package com.hulu.ap.apiframework.exception;

import javax.ws.rs.WebApplicationException;

public class AuthenticationException extends WebApplicationException {

    public AuthenticationException(String msg) {
        super(msg);
    }
}
