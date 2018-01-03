package com.neoremind.apiframework.exception;

import javax.ws.rs.WebApplicationException;

public class BadRequestException extends WebApplicationException {

    public BadRequestException(String msg) {
        super(msg);
    }
}
