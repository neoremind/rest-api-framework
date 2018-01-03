package com.hulu.ap.apiframework.spi;

import org.eclipse.jetty.servlet.FilterHolder;

/**
 * Context handler builder SPI(Service Provider Interface).
 * <p>
 * Users can specify multiple {@link FilterHolder}s in the following file in CLASSPATH.
 * <p>
 * <code>META-INF/services/com.hulu.ap.apiframework.spi.FilterHolderBuilder</code>
 * <p>
 * A server builder can rely on this builder to retrieve {@link FilterHolder}s for server initialization.
 *
 * @author xu.zhang
 */
public interface FilterHolderBuilder extends Supplier<FilterHolder> {

}
