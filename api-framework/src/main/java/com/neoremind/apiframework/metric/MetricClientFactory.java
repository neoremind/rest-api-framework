package com.neoremind.apiframework.metric;

import com.neoremind.apiframework.util.SystemPropUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        try (final InputStream stream = this.getClass().getClassLoader().getResourceAsStream(statsPropPath)) {
            properties.load(stream);
        } catch (Exception e) {
            LOG.warn("Could not find {} from class path", statsPropPath);
        }

        metricClient = new DefaultMetricClient();
        if ("true".equalsIgnoreCase(properties.getProperty("statsd.enabled"))) {
            MetricClient metricClient;
            try {
                metricClient = StatsDClient.buildStatsDClient(properties);
            } catch (Exception e) {
                metricClient = new DefaultMetricClient();
                LOG.warn("Use default metric client which will not send any metrics out");
            }
            this.metricClient = metricClient;
            LOG.info("Metrics will send to grafana {} is enabled", properties.toString());
        } else {
            LOG.info("Metrics sent to grafana is disabled");
        }
    }

    public static MetricClient getMetricClient() {
        return ClientHolder.INSTANCE.metricClient;
    }

    private static class ClientHolder {
        private static final MetricClientFactory INSTANCE = new MetricClientFactory();
    }
}
