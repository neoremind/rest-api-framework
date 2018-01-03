package com.hulu.ap.example.production.exception;


/**
 * Input param is in valid
 *
 * @author xu.zhang
 */
public class ParamException extends RuntimeException {

    public ParamException(String msg) {
        super(msg);
    }
}
