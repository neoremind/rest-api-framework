package com.hulu.ap.apiframework.exception;

import javax.ws.rs.WebApplicationException;

public class ResourceNotFoundException extends WebApplicationException {

    public ResourceNotFoundException(String msg) {
        super(msg);
    }
}
