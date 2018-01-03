package com.hulu.ap.apiframework.metric;

import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.setProperty("statsd.host", "127.0.0.1");
        properties.setProperty("statsd.port", "8125");
        properties.setProperty("statsd.prefix", "my.prefix.mj");
        properties.setProperty("statsd.sampleRate", "1.0");

        try (StatsDClient statsDClient = StatsDClient.buildStatsDClient(properties)) {
            for (int i = 0; i < 6000; i++) {
                long start = System.nanoTime();
                for (int j = 0; j < 10; j++) {
                    // These should all result in approximately the same series
                    statsDClient.count("aCount.oneI.defaultSampleRate", 1);
                    statsDClient.count("aCount.oneF.defaultSampleRate", 1.0);
                    statsDClient.count("aCount.oneI.halfSampleRate", 1, 0.5);
                    statsDClient.count("aCount.oneF.halfSampleRate", 1.0, 0.5);

                    // Check that counter deltas can be fractional (should be half of above series)
                    statsDClient.count("aCount.halfF.defaultSampleRate", 0.5);
                    statsDClient.count("aCount.halfF.halfSampleRate", 0.5, 0.5);

                    // Check that gauges can be integral (i.e. 3)
                    statsDClient.gauge("aGauge.threeI.defaultSampleRate", 3);
                    statsDClient.gauge("aGauge.threeI.halfSampleRate", 3, 0.5);

                    // Check that gauges can be fractional (i.e. 0.5)
                    statsDClient.gauge("aGauge.halfF.defaultSampleRate", 0.5);
                    statsDClient.gauge("aGauge.halfF.halfSampleRate", 0.5, 0.5);

                    // These should all result in approximately the same series
                    statsDClient.timing("aTimer.oneI.defaultSampleRate", 1);
                    statsDClient.timing("aTimer.oneF.defaultSampleRate", 1.0);
                    statsDClient.timing("aTimer.oneI.halfSampleRate", 1, 0.5);
                    statsDClient.timing("aTimer.oneF.halfSampleRate", 1.0, 0.5);

                    // Check that timers can be fractional (i.e. 0.5)
                    statsDClient.timing("aTimer.halfF.defaultSampleRate", 0.5);
                }
                long end = System.nanoTime();

                Thread.sleep(100 - (end - start) / 1000000);
            }
        }
    }
}
