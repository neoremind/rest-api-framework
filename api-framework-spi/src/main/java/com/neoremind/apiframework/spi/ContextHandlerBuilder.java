package com.neoremind.apiframework.spi;

import org.eclipse.jetty.server.handler.ContextHandler;

/**
 * Context handler builder SPI(Service Provider Interface).
 * <p>
 * Users can specify multiple {@link ContextHandler}s in the following file in CLASSPATH.
 * <p>
 * <code>META-INF/services/com.neoremind.apiframework.spi.ContextHandlerBuilder</code>
 * <p>
 * A server builder can rely on this builder to retrieve {@link ContextHandler}s for server initialization.
 *
 * @author xu.zhang
 */
public interface ContextHandlerBuilder extends Supplier<ContextHandler> {

}
