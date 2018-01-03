package com.hulu.ap.apiframework.spi;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Resource Config SPI(Service Provider Interface).
 * <p>
 * Users can specify a {@link ResourceConfig} in the following file in CLASSPATH.
 * <p>
 * <code>META-INF/services/com.hulu.ap.apiframework.spi.CustomResourceConfigProvider</code>
 * <p>
 * A server builder can rely on this builder to retrieve {@link ResourceConfig} for server initialization.
 *
 * @author xu.zhang
 */
public interface CustomResourceConfigProvider extends Supplier<ResourceConfig> {

}
