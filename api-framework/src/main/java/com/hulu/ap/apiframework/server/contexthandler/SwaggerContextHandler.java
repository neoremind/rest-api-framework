package com.hulu.ap.apiframework.server.contexthandler;

import com.hulu.ap.apiframework.spi.ContextHandlerBuilder;
import io.swagger.jaxrs.config.DefaultJaxrsConfig;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Swagger context and UI handler
 *
 * @author xu.zhang
 */
public class SwaggerContextHandler implements ContextHandlerBuilder {

    @Override
    public ContextHandler get() {
        final ServletHolder swaggerServletHolder = new ServletHolder(new DefaultJaxrsConfig());
        swaggerServletHolder.setName("Swagger");

        ServletContextHandler swaggerServletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        swaggerServletContextHandler.setSessionHandler(new SessionHandler());

        swaggerServletContextHandler.addServlet(swaggerServletHolder, "/*");

        return swaggerServletContextHandler;
    }

}
