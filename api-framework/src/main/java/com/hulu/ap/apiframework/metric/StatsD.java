package com.hulu.ap.apiframework.metric;

class StatsD {
    private final Sender sender;
    private final String hostname;
    private final String prefix;

    StatsD(Sender sender, String hostname, String prefix) {
        this.sender = sender;
        this.hostname = hostname;
        this.prefix = prefix;
    }

    public void count(String aspect, double delta, double sampleRate) {
        send(formatMetric(aspect, delta, "c"), sampleRate);

        if (hostname != null) {
            send(formatMetric("ByHostname." + hostname + "." + aspect, delta, "c"), sampleRate);
        }
    }

    public void count(String aspect, int delta, double sampleRate) {
        send(formatMetric(aspect, delta, "c"), sampleRate);

        if (hostname != null) {
            send(formatMetric("ByHostname." + hostname + "." + aspect, delta, "c"), sampleRate);
        }
    }

    public void gauge(String aspect, double value, double sampleRate) {
        send(formatMetric(aspect, value, "g"), sampleRate);

        if (hostname != null) {
            send(formatMetric("ByHostname." + hostname + "." + aspect, value, "g"), sampleRate);
        }
    }

    public void gauge(String aspect, int value, double sampleRate) {
        send(formatMetric(aspect, value, "g"), sampleRate);

        if (hostname != null) {
            send(formatMetric("ByHostname." + hostname + "." + aspect, value, "g"), sampleRate);
        }
    }

    public void timing(String aspect, double timeInMilliseconds, double sampleRate) {
        send(formatMetric(aspect, timeInMilliseconds, "ms"), sampleRate);

        if (hostname != null) {
            send(formatMetric("ByHostname." + hostname + "." + aspect, timeInMilliseconds, "ms"), sampleRate);
        }
    }

    public void timing(String aspect, int timeInMilliseconds, double sampleRate) {
        send(formatMetric(aspect, timeInMilliseconds, "ms"), sampleRate);

        if (hostname != null) {
            send(formatMetric("ByHostname." + hostname + "." + aspect, timeInMilliseconds, "ms"), sampleRate);
        }
    }

    private String formatMetric(String aspect, double value, String type) {
        return prefix + "." + replaceEscapeCharsInAspect(aspect) + ":" + value + "|" + type;
    }

    private String formatMetric(String aspect, int value, String type) {
        return prefix + "." + replaceEscapeCharsInAspect(aspect) + ":" + value + "|" + type;
    }

    private void send(String message, double sampleRate) {
        final String messageWithSampleRate;
        if (sampleRate >= 1) {
            messageWithSampleRate = message;
        } else {
            messageWithSampleRate = message + "|@" + sampleRate;
        }

        sender.send(messageWithSampleRate);
    }

    private static String replaceEscapeCharsInAspect(String aspect) {
        return aspect.replace(':', '-').replace('|', '-');
    }

    static interface Sender {
        void send(String metric);
    }
}
