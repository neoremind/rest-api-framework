package com.hulu.ap.apiframework.metric;

import com.hulu.ap.apiframework.util.AddressUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public final class StatsDClient implements MetricClient {
    private static final Logger LOG = LoggerFactory.getLogger(StatsDClient.class);
    private final NonBlockingSender sender;
    private final StatsD statsD;
    private final double defaultSampleRate;

    private StatsDClient(NonBlockingSender sender, StatsD statsD, double defaultSampleRate) {
        this.sender = sender;
        this.statsD = statsD;
        this.defaultSampleRate = defaultSampleRate;
    }

    @Override
    public void close() throws InterruptedException {
        sender.close();
    }

    @Override
    public void count(String aspect, double delta) {
        count(aspect, delta, defaultSampleRate);
    }

    @Override
    public void count(String aspect, double delta, double sampleRate) {
        if (selected(sampleRate)) {
            statsD.count(aspect, delta, sampleRate);
        }
    }

    @Override
    public void count(String aspect, int delta) {
        count(aspect, delta, defaultSampleRate);
    }

    @Override
    public void count(String aspect, int delta, double sampleRate) {
        if (selected(sampleRate)) {
            statsD.count(aspect, delta, sampleRate);
        }
    }

    @Override
    public void gauge(String aspect, double value) {
        gauge(aspect, value, defaultSampleRate);
    }

    @Override
    public void gauge(String aspect, double value, double sampleRate) {
        if (selected(sampleRate)) {
            statsD.gauge(aspect, value, sampleRate);
        }
    }

    @Override
    public void gauge(String aspect, int value) {
        gauge(aspect, value, defaultSampleRate);
    }

    @Override
    public void gauge(String aspect, int value, double sampleRate) {
        if (selected(sampleRate)) {
            statsD.gauge(aspect, value, sampleRate);
        }
    }

    @Override
    public void timing(String aspect, double timeInMilliseconds) {
        timing(aspect, timeInMilliseconds, defaultSampleRate);
    }

    @Override
    public void timing(String aspect, double timeInMilliseconds, double sampleRate) {
        if (selected(sampleRate)) {
            statsD.timing(aspect, timeInMilliseconds, sampleRate);
        }
    }

    @Override
    public void timing(String aspect, int timeInMilliseconds) {
        timing(aspect, timeInMilliseconds, defaultSampleRate);
    }

    @Override
    public void timing(String aspect, int timeInMilliseconds, double sampleRate) {
        if (selected(sampleRate)) {
            statsD.timing(aspect, timeInMilliseconds, sampleRate);
        }
    }

    private boolean selected(double sampleRate) {
        // Use ThreadLocalRandom to avoid hidden contention of Math.random()
        return sampleRate >= 1 || ThreadLocalRandom.current().nextDouble() < sampleRate;
    }

    @Override
    public TimeMeasureContext measureTime(String aspect) {
        return new TimeMeasureContextImpl(aspect, defaultSampleRate);
    }

    @Override
    public TimeMeasureContext measureTime(String aspect, double sampleRate) {
        return new TimeMeasureContextImpl(aspect, sampleRate);
    }

    private final class TimeMeasureContextImpl implements MetricClient.TimeMeasureContext {
        private final String aspect;
        private final double sampleRate;
        private final long startTimestampInNanos;

        TimeMeasureContextImpl(String aspect, double sampleRate) {
            this.aspect = aspect;
            this.sampleRate = sampleRate;
            this.startTimestampInNanos = System.nanoTime();
        }

        @Override
        public void close() {
            /*
             * Do conversion to millis ourselves; TimeUnit.NANOSECONDS.toMillis() would cause unnecessary loss of
             * precision.
             */
            double timeUsedInMilliseconds = (System.nanoTime() - startTimestampInNanos) / 1e6;
            timing(aspect, timeUsedInMilliseconds, sampleRate);
        }
    }

    public static StatsDClient buildStatsDClient() throws IOException {
        Properties properties = getPropertyByClassLoader(StatsD.class.getClassLoader(), "statsd-client.properties");
        return buildStatsDClient(properties);
    }

    public static StatsDClient buildStatsDClient(Properties properties) throws UnknownHostException {
        String statsDHost = properties.getProperty("statsd.host");
        int statsDPort = Integer.parseInt(properties.getProperty("statsd.port"));
        String statsDPrefix = properties.getProperty("statsd.prefix");
        double statsDSampleRate = Double.parseDouble(properties.getProperty("statsd.sampleRate"));
        return buildStatsDClient(statsDHost, statsDPort, statsDPrefix, statsDSampleRate);
    }

    private static StatsDClient buildStatsDClient(
            String statsDHost,
            int statsDPort,
            String statsDPrefix,
            double statsDSampleRate) throws UnknownHostException {
        NonBlockingSender sender = new NonBlockingSender(statsDHost, statsDPort);
        String hostname = AddressUtils.getShortHostname();
        StatsD statsD = new StatsD(sender, hostname, statsDPrefix);
        return new StatsDClient(sender, statsD, statsDSampleRate);
    }

    private static Properties getPropertyByClassLoader(ClassLoader classLoader, String fileName) throws IOException {
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;
        }
    }

    private static final class NonBlockingSender implements StatsD.Sender, AutoCloseable {
        private static final int MAX_UDP_SIZE = 512;
        private final BlockingQueue<Wrapper> queue = new ArrayBlockingQueue<>(256 * 1024);
        private final Thread thread;
        private volatile boolean stopping;

        private NonBlockingSender(String host, int port) {
            this.thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder buffer = new StringBuilder();
                    try (DatagramSocket socket = new DatagramSocket()) {
                        while (!stopping) {
                            Wrapper wrapper = queue.take();
                            if (wrapper instanceof Sentinel) {
                                break;
                            }

                            String metric = wrapper.metric;
                            if (buffer.length() + metric.length() + 1 > MAX_UDP_SIZE) {
                                sendPacket(buffer.toString(), socket);
                                buffer.setLength(0);
                            }

                            if (buffer.length() > 0) {
                                buffer.append('\n');
                            }

                            buffer.append(metric);
                        }
                    } catch (InterruptedException e) {
                        LOG.debug("interrupted");
                    } catch (Exception e) {
                        LOG.error("StatsD sender thread crashed! No more metrics will be sent!", e);
                    }
                }

                private void sendPacket(String data, DatagramSocket socket) {
                    try {
                        /*
                         * INSTANTIATE A NEW ADDRESS FOR EVERY PACKET! THIS ENSURES THAT IF THE HOST FQDN
                         * CHANGES, WE'LL PICK UP THE CHANGE. JVM CACHES THE ENTRIES FOR 30s BY DEFAULT SO
                         * THAT WE AREN'T CONSTANTLY ASKING THE OS TO RESOLVE THE ADDRESS FOR US EACH TIME
                         * WE SEND A PACKET.
                         */
                        InetSocketAddress address = new InetSocketAddress(host, port);
                        byte[] message = data.getBytes(StandardCharsets.UTF_8);
                        DatagramPacket sendPacket = new DatagramPacket(message, message.length, address);
                        socket.send(sendPacket);
                    } catch (Exception e) {
                        /*
                         * This is potentially an extremely hot code path. Only log at debug level so that
                         * we don't get swamped by logging.
                         */
                        LOG.debug("Failed to send message: {}", data, e);
                    }
                }
            }, "statsd-sender");

            /*
             * Mark thread as a daemon so that it will never prevent the JVM from shutting down (in case applications
             * forget to close the class and clean up resources).
             */
            this.thread.setDaemon(true);
            this.thread.start();
        }

        @Override
        public void close() throws InterruptedException {
            stopping = true;
            thread.interrupt();
            while (!queue.offer(new Sentinel())) {
                queue.clear();
            }

            thread.join(TimeUnit.SECONDS.toMillis(30));
        }

        @Override
        public void send(String metric) {
            if (!stopping) {
                queue.offer(new Wrapper(metric));
            }
        }

        private static class Wrapper {
            private final String metric;

            private Wrapper(String metric) {
                this.metric = metric;
            }
        }

        private static final class Sentinel extends Wrapper {
            private Sentinel() {
                super(null);
            }
        }
    }
}
