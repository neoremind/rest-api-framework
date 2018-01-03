package com.hulu.ap.apiframework.client;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Failure callback when encountering <code>IOException</code>
 *
 * @author xu.zhang
 */
public interface FailCallback {

    /**
     * Failure callback.
     * Usually logging or re-throwing exception
     *
     * @param request    http request
     * @param response   http response
     * @param e          exception
     * @param retryTimes retry times
     * @throws IOException
     */
    void onFail(Request request, Response response, IOException e, int retryTimes) throws IOException;
}
