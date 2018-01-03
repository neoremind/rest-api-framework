package com.neoremind.apiframework.metric;

public interface MetricClient extends AutoCloseable {
    /**
     * Adjusts the specified counter by a given delta. This method is non-blocking and is guaranteed not to throw an
     * exception.
     *
     * @param aspect the name of the counter to adjust
     * @param delta  the amount to adjust the counter by
     */
    void count(String aspect, double delta);

    void count(String aspect, double delta, double sampleRate);

    void count(String aspect, int delta);

    void count(String aspect, int delta, double sampleRate);

    /**
     * Records the latest fixed value for the specified named gauge. This method is non-blocking and is guaranteed not
     * to throw an exception.
     *
     * @param aspect the name of the gauge
     * @param value  the new reading of the gauge
     */
    void gauge(String aspect, double value);

    void gauge(String aspect, double value, double sampleRate);

    void gauge(String aspect, int value);

    void gauge(String aspect, int value, double sampleRate);

    /**
     * Records an execution time in milliseconds for the specified named operation. This method is non-blocking and is
     * guaranteed not to throw an exception.
     *
     * @param aspect             the name of the timed operation
     * @param timeInMilliseconds the time in milliseconds
     */
    void timing(String aspect, double timeInMilliseconds);

    void timing(String aspect, double timeInMilliseconds, double sampleRate);

    void timing(String aspect, int timeInMilliseconds);

    void timing(String aspect, int timeInMilliseconds, double sampleRate);

    /**
     * Return a context to measure a block execution time in milliseconds.
     *
     * @param aspect the name of the timed block.
     * @return TimeMeasureContext
     */
    TimeMeasureContext measureTime(String aspect);

    TimeMeasureContext measureTime(String aspect, double sampleRate);

    public static interface TimeMeasureContext extends AutoCloseable {
        void close();
    }
}
