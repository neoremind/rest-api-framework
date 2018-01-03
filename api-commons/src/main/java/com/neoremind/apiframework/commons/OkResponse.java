package com.neoremind.apiframework.commons;

/**
 * Pure ok response
 *
 * @author xu.zhang
 */
public class OkResponse extends ResponseWithCodeAndMsg {

    public static OkResponse create() {
        return new OkResponse();
    }

}
