package com.hulu.ap.apiframework;

import com.hulu.ap.apiframework.server.contexthandler.ContextHandlerTransformer;
import com.hulu.ap.apiframework.spi.ContextHandlerBuilder;
import com.hulu.ap.apiframework.spi.ServiceProviderLoader;
import com.hulu.ap.apiframework.util.ConfigUtil;
import lombok.Getter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.BlockingArrayQueue;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import static com.hulu.ap.apiframework.common.Constants.ServerDefaults.PORT;
import static com.hulu.ap.apiframework.common.Constants.ServerDefaults.JETTY_MAX_THREADS;
import static com.hulu.ap.apiframework.common.Constants.ServerDefaults.JETTY_MIN_THREADS;
import static com.hulu.ap.apiframework.common.Constants.ServerDefaults.JETTY_QUEUE_MAX_CAPACITY;
import static com.hulu.ap.apiframework.common.Constants.ServerDefaults.JETTY_THREAD_IDLE_TIMEOUT_IN_MS;

/**
 * Jetty HTTP Server builder.
 */
@Getter
public class ServerBuilder {

    /**
     * Server port
     *
     * @see com.hulu.ap.apiframework.common.Constants.ServerDefaults#PORT
     */
    private final int serverPort;

    /**
     * Jetty server blocking io waiting queue max capacity
     *
     * @see com.hulu.ap.apiframework.common.Constants.ServerDefaults#JETTY_QUEUE_MAX_CAPACITY
     */
    private final int jettyQueueMaxCapacity;

    /**
     * Jetty server min serving thread number
     *
     * @see com.hulu.ap.apiframework.common.Constants.ServerDefaults#JETTY_MIN_THREADS
     */
    private final int jettyMinThreads;

    /**
     * Jetty server max serving thread number
     *
     * @see com.hulu.ap.apiframework.common.Constants.ServerDefaults#JETTY_MAX_THREADS
     */
    private final int jettyMaxThreads;

    /**
     * Jetty waiting thread max idle timeout in milliseconds
     *
     * @see com.hulu.ap.apiframework.common.Constants.ServerDefaults#JETTY_THREAD_IDLE_TIMEOUT_IN_MS
     */
    private final int jettyThreadIdleTimeoutInMs;

    /**
     * Default constructor
     */
    public ServerBuilder() {
        serverPort = ConfigUtil.getInt(PORT.getKey(), PORT.getDefaultValue());
        jettyQueueMaxCapacity = ConfigUtil.getInt(JETTY_QUEUE_MAX_CAPACITY.getKey(), JETTY_QUEUE_MAX_CAPACITY.getDefaultValue());
        jettyMinThreads = ConfigUtil.getInt(JETTY_MIN_THREADS.getKey(), JETTY_MIN_THREADS.getDefaultValue());
        jettyMaxThreads = ConfigUtil.getInt(JETTY_MAX_THREADS.getKey(), JETTY_MAX_THREADS.getDefaultValue());
        jettyThreadIdleTimeoutInMs = ConfigUtil.getInt(JETTY_THREAD_IDLE_TIMEOUT_IN_MS.getKey(), JETTY_THREAD_IDLE_TIMEOUT_IN_MS.getDefaultValue());
    }

    /**
     * Build Jetty server
     *
     * @return Jetty server
     */
    public Server build() throws Exception {
        BlockingArrayQueue queue = new BlockingArrayQueue<>(jettyMinThreads, jettyMinThreads, jettyQueueMaxCapacity);
        QueuedThreadPool queuedThreadPool = new QueuedThreadPool(jettyMaxThreads, jettyMinThreads, jettyThreadIdleTimeoutInMs, queue);
        queuedThreadPool.setName("Jetty-Server");
        Server server = new Server(queuedThreadPool);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(serverPort);
        server.addConnector(connector);
        server.setHandler(new ContextHandlerTransformer().transform(ServiceProviderLoader.getAllInstances(ContextHandlerBuilder.class)));
        return server;
    }

}
