package com.neoremind.apiframework.guice;

import com.google.inject.Guice;
import com.google.inject.Module;
import com.google.inject.servlet.ServletModule;
import com.squarespace.jersey2.guice.JerseyGuiceModule;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.extension.ServiceLocatorGenerator;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * A extension of {@link ResourceConfig} for Jersey servlet container
 * {@link org.glassfish.jersey.servlet.ServletContainer} to initialize.
 *
 * @author xu.zhang
 */
public class GuiceResourceConfig extends ResourceConfig {

    /**
     * Service provider loader for Guice enabled <code>JettyModule</code>s
     */
    private ServiceLoader<JettyModule> moduleServiceLoader;

    /**
     * Default constructor.
     * Load Guice enabled <code>JettyModule</code> through SPI.
     * User can specify multiple Guice {@link Module}s which is defined in
     * <code>META-INF/services/com.neoremind.apiframework.guice.JettyModule</code>
     * for bean management.
     */
    public GuiceResourceConfig() {
        moduleServiceLoader = ServiceLoader.load(JettyModule.class);
        JerseyGuiceUtils.install(new ServiceLocatorGenerator() {
            @Override
            public ServiceLocator create(String name, ServiceLocator parent) {
                if (!name.startsWith("__HK2_")) {
                    return null;
                }

                List<Module> modules = new ArrayList<>();

                modules.add(new JerseyGuiceModule(name));
                modules.add(new ServletModule());
                //modules.add(jerseyModule);

                for (JettyModule module : moduleServiceLoader) {
                    modules.add(module);
                }
                return Guice.createInjector(modules).getInstance(ServiceLocator.class);
            }
        });
    }

    /**
     * Not in use for customization later
     */
    private final ServletModule jerseyModule = new ServletModule() {
        @Override
        protected void configureServlets() {
            //serve("/*").with(GuiceContainer.class);
            //filter(MyFilter.PATH).through(MyFilter.class);
        }
    };

}
