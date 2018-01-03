package com.neoremind.apiframework.guice;

import com.neoremind.apiframework.spi.CustomResourceConfigProvider;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Resource Config SPI(Service Provider Interface) implementation.
 * <p>
 * Users can specify a {@link ResourceConfig} in the following file in CLASSPATH.
 * <p>
 * <code>META-INF/services/com.neoremind.apiframework.spi.CustomResourceConfigProvider</code>
 * <p>
 * A server builder can rely on this builder to retrieve {@link ResourceConfig} for
 * build a Google Guice IoC container enabled server.
 *
 * @author xu.zhang
 */
public class GuiceResourceConfigProvider implements CustomResourceConfigProvider {

    @Override
    public ResourceConfig get() {
        return new GuiceResourceConfig();
    }
}
