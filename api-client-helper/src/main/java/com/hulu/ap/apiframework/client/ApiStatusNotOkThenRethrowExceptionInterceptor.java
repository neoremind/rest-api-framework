package com.hulu.ap.apiframework.client;

import net.neoremind.dynamicproxy.Interceptor;

/**
 * Leverage <a href="https://github.com/neoremind/dynamic-proxy"></a> to re-throw exception
 * if {@link com.hulu.ap.apiframework.commons.ResponseWithCodeAndMsg#code} is not OK.
 *
 * @author xu.zhang
 */
public class ApiStatusNotOkThenRethrowExceptionInterceptor extends AbstractApiStatusNotOkInterceptor implements Interceptor {

    @Override
    public void handle(String errorMsg) {
        throw new ApiClientException(errorMsg.toString());
    }

}
