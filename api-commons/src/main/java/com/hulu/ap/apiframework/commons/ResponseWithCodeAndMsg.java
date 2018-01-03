package com.hulu.ap.apiframework.commons;

/**
 * Basic response with code and message.
 *
 * @author xu.zhang
 */
public class ResponseWithCodeAndMsg {

    public static final int OK = 0;

    /**
     * code
     * <p>
     * By default 0 means everything is OK.
     */
    public int code = OK;

    /**
     * message
     */
    public String msg;

    public ResponseWithCodeAndMsg() {

    }

    public ResponseWithCodeAndMsg(int code) {
        this.code = code;
    }

    public ResponseWithCodeAndMsg(String msg) {
        this.msg = msg;
    }

    public ResponseWithCodeAndMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResponseWithCodeAndMsg create() {
        return new ResponseWithCodeAndMsg();
    }

    public static ResponseWithCodeAndMsg create(int code) {
        return new ResponseWithCodeAndMsg(code);
    }

    public static ResponseWithCodeAndMsg create(String msg) {
        return new ResponseWithCodeAndMsg(msg);
    }

    public static ResponseWithCodeAndMsg create(int code, String msg) {
        return new ResponseWithCodeAndMsg(code, msg);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
