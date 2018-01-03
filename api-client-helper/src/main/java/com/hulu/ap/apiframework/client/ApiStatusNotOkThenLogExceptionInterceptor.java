package com.hulu.ap.apiframework.client;

import net.neoremind.dynamicproxy.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Leverage <a href="https://github.com/neoremind/dynamic-proxy"></a> to log exception
 * if {@link com.hulu.ap.apiframework.commons.ResponseWithCodeAndMsg#code} is not OK.
 *
 * Usage would like:
 * <pre>
 *     ProxyCreator proxyCreator = new DefaultProxyCreator();
 *     SnapshotApi snapshotApi = proxyCreator.createInterceptorProxy(snapshotApi, new ApiStatusNotOkThenRethrowExceptionInterceptor(), SnapshotApi.class);
 * </pre>
 *
 * @author xu.zhang
 */
public class ApiStatusNotOkThenLogExceptionInterceptor extends AbstractApiStatusNotOkInterceptor implements Interceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void handle(String errorMsg) {
        logger.error(errorMsg);
    }

}
