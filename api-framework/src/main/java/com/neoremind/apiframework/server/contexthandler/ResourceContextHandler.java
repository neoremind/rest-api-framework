package com.neoremind.apiframework.server.contexthandler;

import com.neoremind.apiframework.able.Closure;
import com.neoremind.apiframework.spi.ContextHandlerBuilder;
import com.neoremind.apiframework.spi.CustomResourceConfigProvider;
import com.neoremind.apiframework.spi.FilterHolderBuilder;
import com.neoremind.apiframework.spi.ServiceProviderLoader;
import com.neoremind.apiframework.util.CollectionUtil;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

/**
 * Jersey RS resource handler
 *
 * @author xu.zhang
 */
public class ResourceContextHandler implements ContextHandlerBuilder {

    /**
     * Jersey RS will scan the following packages to get services
     */
    public static final String[] DEFAULT_PACKAGES = new String[]{"com", "io.swagger.jaxrs.listing"};

    @Override
    public ContextHandler get() {
        ResourceConfig resourceConfig = new ResourceConfig();
        CustomResourceConfigProvider customResourceConfig = ServiceProviderLoader.getFirstInstances(CustomResourceConfigProvider.class);
        if (customResourceConfig != null) {
            resourceConfig = customResourceConfig.get();
        }
        resourceConfig.packages(DEFAULT_PACKAGES);
        resourceConfig.register(CustomLoggingFilter.class);
        resourceConfig.property("jersey.config.server.wadl.disableWadl", true);
        resourceConfig.register(MoxyJsonFeature.class);

        ServletContainer servletContainer = new ServletContainer(resourceConfig);

        ServletHolder servletHolder = new ServletHolder(servletContainer);
        servletHolder.setInitOrder(1);
        servletHolder.setInitParameter("jersey.config.server.tracing", "ALL");

        final ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.setSessionHandler(new SessionHandler());
        servletContextHandler.setContextPath("/");

        servletContextHandler.addServlet(servletHolder, "/*");

        CollectionUtil.forAllDo(ServiceProviderLoader.getAllInstances(FilterHolderBuilder.class), new Closure<FilterHolderBuilder>() {
            @Override
            public void execute(FilterHolderBuilder input) {
                servletContextHandler.addFilter(input.get(), "/*", EnumSet.of(DispatcherType.REQUEST));
            }
        });

        return servletContextHandler;
    }

}
