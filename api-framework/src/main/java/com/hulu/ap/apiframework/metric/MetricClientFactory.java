package com.hulu.ap.apiframework.metric;

import com.hulu.ap.apiframework.util.SystemPropUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class MetricClientFactory {

    private static final Logger LOG = LoggerFactory.getLogger(MetricClientFactory.class);

    private static final String PROPERTY_BASE_PATH = "conf";

    private MetricClient metricClient;

    private String env;

    private MetricClientFactory() {
        this.env = SystemPropUtil.getSystemProperty("rest.api.env", "default");
        String statsPropPath = String.format("%s/%s/%s", PROPERTY_BASE_PATH, this.env,
                "statsd-client.properties");
        final Properties properties = new Properties();
        try (final InputStream stream = this.getClass().getResourceAsStream(statsPropPath)) {
            properties.load(stream);
        } catch (Exception e) {
            LOG.warn("Could not find {} from class path", statsPropPath);
        }

        MetricClient metricClient;
        try {
            metricClient = StatsDClient.buildStatsDClient(properties);
        } catch (Exception e) {
            metricClient = new DefaultMetricClient();
            LOG.info("Use default metric client which will not send any metrics out");
        }

        this.metricClient = metricClient;
    }

    public static MetricClient getMetricClient() {
        return ClientHolder.INSTANCE.metricClient;
    }

    private static class ClientHolder {
        private static final MetricClientFactory INSTANCE = new MetricClientFactory();
    }
}
