package com.neoremind.example.production.exception;


/**
 * @author xu.zhang
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String msg) {
        super(msg);
    }
}
