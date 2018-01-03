package com.neoremind.apiframework.metric;

public class DefaultMetricClient implements MetricClient {

    @Override
    public void count(String s, double v) {

    }

    @Override
    public void count(String s, double v, double v1) {

    }

    @Override
    public void count(String s, int i) {

    }

    @Override
    public void count(String s, int i, double v) {

    }

    @Override
    public void gauge(String s, double v) {

    }

    @Override
    public void gauge(String s, double v, double v1) {

    }

    @Override
    public void gauge(String s, int i) {

    }

    @Override
    public void gauge(String s, int i, double v) {

    }

    @Override
    public void timing(String s, double v) {

    }

    @Override
    public void timing(String s, double v, double v1) {

    }

    @Override
    public void timing(String s, int i) {

    }

    @Override
    public void timing(String s, int i, double v) {

    }

    @Override
    public TimeMeasureContext measureTime(String s) {
        return new DefaultTimeMeasureContext();
    }

    @Override
    public TimeMeasureContext measureTime(String s, double v) {
        return new DefaultTimeMeasureContext();
    }

    @Override
    public void close() throws Exception {

    }

    private class DefaultTimeMeasureContext implements MetricClient.TimeMeasureContext {
        @Override
        public void close() {

        }
    }
}
