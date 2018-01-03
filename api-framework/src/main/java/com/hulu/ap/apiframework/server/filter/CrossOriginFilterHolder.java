package com.hulu.ap.apiframework.server.filter;

import com.hulu.ap.apiframework.spi.FilterHolderBuilder;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;

/**
 * Cross origin filter holder
 *
 * @author xu.zhang
 */
public class CrossOriginFilterHolder implements FilterHolderBuilder {

    @Override
    public FilterHolder get() {
        FilterHolder crossOriginFilterHolder = new FilterHolder(new CrossOriginFilter());
        crossOriginFilterHolder.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM,
                "GET, POST, DELETE, PUT, OPTIONS");
        crossOriginFilterHolder.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM,
                "Content-Type, api_key, Authorization");
        return crossOriginFilterHolder;
    }
}
