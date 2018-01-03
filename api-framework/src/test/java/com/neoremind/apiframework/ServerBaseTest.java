package com.neoremind.apiframework;

import com.neoremind.apiframework.common.LoggerInitializer;
import org.eclipse.jetty.server.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xu.zhang
 */
public class ServerBaseTest {

    protected static final Logger LOGGER = LoggerFactory.getLogger("Server");

    protected static Server server;

    protected static final String baseUrl = "http://localhost:";

    protected static int PORT;

    protected Backoff backoff = new Backoff(30000, 60000);

    @BeforeClass
    public static void setUp() throws Exception {
        new LoggerInitializer();
        // retry 4 times to launch server
        for (int i = 0; i < 4; i++) {
            try {
                System.setProperty("PORT", "" + RandomPortUtil.getPort(8000, 12000));
                PORT = Integer.parseInt(System.getProperty("PORT"));
                server = new ServerBuilder().build();
                server.setStopAtShutdown(true);
                server.start();
                System.out.println("Please visit " + baseUrl + PORT);
                return;
            } catch (Exception e) {
                System.err.println("Address conflict on " + PORT);
            }
        }
        throw new RuntimeException("Test server failed to start");
    }


    @AfterClass
    public static void tearDown() throws Exception {
        if (server != null) {
            server.stop();
        }
    }
}
