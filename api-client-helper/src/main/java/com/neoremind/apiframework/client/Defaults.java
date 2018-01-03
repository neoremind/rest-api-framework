package com.neoremind.apiframework.client;

import com.neoremind.apiframework.commons.ResponseWithCodeAndMsg;

/**
 * Socket default parameters
 *
 * @author xu.zhang
 */
public class Defaults {

    /**
     * connect timeout in ms
     */
    public static final int CONNECT_TIMEOUT_IN_MS = 4000;

    /**
     * read timeout in ms
     */
    public static final int READ_TIMEOUT_IN_MS = 30000;

    /**
     * write timeout in ms
     */
    public static final int WRITE_TIMEOUT_IN_MS = 30000;

    /**
     * retry count
     */
    public static final int RETRY_TIMES = 0;

    /**
     * HTTP Status code - OK
     */
    public static final int HTTP_STATUS_OK = 200;

    /**
     * {@link ResponseWithCodeAndMsg} ok status code
     */
    public static final int RESPONSE_STATUS_CODE_OK = 0;

}
