package com.neoremind.apiframework.client;

import com.neoremind.apiframework.commons.ResponseWithCodeAndMsg;
import net.neoremind.dynamicproxy.Interceptor;

/**
 * Leverage <a href="https://github.com/neoremind/dynamic-proxy"></a> to re-throw exception
 * if {@link ResponseWithCodeAndMsg#code} is not OK.
 *
 * @author xu.zhang
 */
public class ApiStatusNotOkThenRethrowExceptionInterceptor extends AbstractApiStatusNotOkInterceptor implements Interceptor {

    @Override
    public void handle(String errorMsg) {
        throw new ApiClientException(errorMsg.toString());
    }

}
