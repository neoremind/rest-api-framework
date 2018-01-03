package com.hulu.ap.apiframework.server.filter;

import com.google.inject.servlet.GuiceFilter;
import com.hulu.ap.apiframework.spi.FilterHolderBuilder;
import org.eclipse.jetty.servlet.FilterHolder;

/**
 * Guice servlet filter for enabling Google Guice IoC container within Jersey servlet.
 * Rely on the <code>com.google.inject.extensions:guice-servlet</code> dependency.
 *
 * @author xu.zhang
 */
public class GuiceFilterHolder implements FilterHolderBuilder {

    @Override
    public FilterHolder get() {
        return new FilterHolder(new GuiceFilter());
    }
}
