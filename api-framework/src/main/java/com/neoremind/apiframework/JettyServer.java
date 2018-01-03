package com.neoremind.apiframework;

import com.neoremind.apiframework.common.LoggerInitializer;
import jersey.repackaged.com.google.common.base.Throwables;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * By leveraging Jetty HTTP Servlet Server.
 * This class is the main entrance and class for launching a OpenAPI specification based Restful API.
 * <p>
 * Simply execute the following command, a server can be started.
 * <pre>
 *     java -jar my-server.jar com.neoremind.apiframework.JettyServer
 * </pre>
 * <p>
 *
 * @author xu.zhang
 */
public class JettyServer {

    private static final Logger LOGGER = LoggerFactory.getLogger("Server");

    public static void main(String[] args) throws Exception {
        new LoggerInitializer();
        preferIpv4();
        Server server = new ServerBuilder().build();
        server.setStopAtShutdown(true);
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            LOGGER.error("Failed to start server", e);
            Throwables.propagate(e);
        }
    }
    private static void preferIpv4() {
        System.setProperty("java.net.preferIPv4Stack", "true");
    }

}
