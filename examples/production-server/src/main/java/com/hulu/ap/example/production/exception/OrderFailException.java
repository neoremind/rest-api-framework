package com.hulu.ap.example.production.exception;


/**
 * Usually caused by inventory not enough or etc.
 *
 * @author xu.zhang
 */
public class OrderFailException extends RuntimeException {

    public OrderFailException(String msg) {
        super(msg);
    }
}
