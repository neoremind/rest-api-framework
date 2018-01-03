package com.neoremind.apiframework.common;

/**
 * Add global constants.
 *
 * @author xu.zhang
 */
public final class Constants {

    public static final String UUID_KEY = "request_id";

    public static final String MDC_USER_ID = "user_id";

    public static final String MDC_ENDPOINT_KEY = "endpoint";

    /**
     * Global default configuration for running server
     */
    public static enum ServerDefaults {

        PORT("PORT", 8080),
        JETTY_QUEUE_MAX_CAPACITY("JETTY_QUEUE_MAX_CAPACITY", 65536),
        JETTY_MIN_THREADS("JETTY_MIN_THREADS", 100),
        JETTY_MAX_THREADS("JETTY_MAX_THREADS", 400),
        JETTY_THREAD_IDLE_TIMEOUT_IN_MS("JETTY_THREAD_IDLE_TIMEOUT_IN_MS", 120000);

        private ServerDefaults(final String key, final int defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }


        private String key;

        private int defaultValue;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public int getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(int defaultValue) {
            this.defaultValue = defaultValue;
        }
    }

    /**
     * For access logging
     */
    public static final String REQ = "REQ";
    public static final String RES = "RES";
    public static final String REQ_IP = "RES_IP";
    public static final String REQ_URL = "RES_URL";
}
