package com.hulu.ap.apiframework.util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import org.eclipse.jetty.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * API framework global configuration utility.
 * <p>
 * Loading configuration example shows as below which means assign a value to <code>port</code> variable.
 * <pre>
 *     int port = ConfigUtil.getInt("port", 8080);
 * </pre>
 * <p>
 * Loading priority from the highest to the lowest shows as below.
 * <ul>
 * <li>1) <code>-D</code> in VM arguments</li>
 * <li>2) Environment variable in shell</li>
 * <li>3) Defined in ${@link ConfigUtil#CONF_FILE_NAME} with k-v pair</li>
 * <li>4) Default value, for example of the above is 8080</li>
 * </ul>
 *
 * @author xu.zhang
 */
public class ConfigUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtil.class);

    /**
     * Global configuration file name for api framework
     */
    public static final String CONF_FILE_NAME = "api-server.conf";

    /**
     * Config instance
     */
    private static volatile Config CONF = null;

    /**
     * Helper object for double checked locking on singleton class
     */
    private static final Object LOCK = new Object();

    /**
     * Get conf thread-safely
     *
     * @return Config instance
     */
    public static Config getConf() {
        if (CONF == null) {
            synchronized (LOCK) {
                if (CONF == null) {
                    CONF = ConfigFactory.load(CONF_FILE_NAME);
                }
            }
        }
        return CONF;
    }

    /**
     * Get long value
     *
     * @param key          key
     * @param defaultValue default value
     * @return value
     */
    public static Long getLong(String key, long defaultValue) {
        return templateGet(key, new AbstractCallback<Long>() {
            @Override
            public Long parseString(String strValue) {
                return Long.parseLong(strValue);
            }

            @Override
            public Long getValueFromConf(String key) {
                return getConf().getLong(key);
            }
        }, defaultValue);
    }

    /**
     * Get int value
     *
     * @param key          key
     * @param defaultValue default value
     * @return value
     */
    public static Integer getInt(String key, int defaultValue) {
        return templateGet(key, new AbstractCallback<Integer>() {
            @Override
            public Integer parseString(String strValue) {
                return Integer.parseInt(strValue);
            }

            @Override
            public Integer getValueFromConf(String key) {
                return getConf().getInt(key);
            }
        }, defaultValue);
    }

    /**
     * Get boolean value
     *
     * @param key          key
     * @param defaultValue default value
     * @return value
     */
    public static Boolean getBoolean(String key, Boolean defaultValue) {
        return templateGet(key, new AbstractCallback<Boolean>() {
            @Override
            public Boolean parseString(String strValue) {
                return Boolean.parseBoolean(strValue);
            }

            @Override
            public Boolean getValueFromConf(String key) {
                return getConf().getBoolean(key);
            }
        }, defaultValue);
    }

    /**
     * Get string value
     *
     * @param key          key
     * @param defaultValue default value
     * @return value
     */
    public static String getString(String key, String defaultValue) {
        return templateGet(key, new AbstractCallback<String>() {
            @Override
            public String parseString(String strValue) {
                return strValue;
            }

            @Override
            public String getValueFromConf(String key) {
                return getConf().getString(key);
            }
        }, defaultValue);
    }

    /**
     * Template for getting value.
     * <p>
     * The following methods leverage this template:
     * <ul>
     * <li>{@link #getInt(String, int)}</li>
     * <li>{@link #getLong(String, long)}</li>
     * <li>{@link #getBoolean(String, Boolean)}</li>
     * <li>{@link #getString(String, String)}</li>
     * </ul>
     *
     * @param key          key
     * @param defaultValue default value
     * @return value
     */
    public static <T> T templateGet(String key, Callback<T> callback, T defaultValue) throws NumberFormatException {
        String strValue = SystemPropUtil.getSystemProperty(key, null);
        if (!StringUtil.isBlank(strValue)) {
            try {
                T t = callback.parseString(strValue);
                callback.beforeReturn(key, t, defaultValue);
                return t;
            } catch (RuntimeException e) {
                callback.onParseStringException(e);
            }
        }
        try {
            T t = callback.getValueFromConf(key);
            callback.beforeReturn(key, t, defaultValue);
            return t;
        } catch (ConfigException.Missing e) {
            LOGGER.debug(e.getMessage());
            return defaultValue;
        } catch (ConfigException e) {
            callback.beforeReturn(key, defaultValue, defaultValue);
            callback.onConfigException(e);
            return defaultValue;
        }
    }

    /**
     * Get template callback
     *
     * @param <T> Return type
     */
    interface Callback<T> {

        /**
         * Parse value from <code>-D</code> in VM arguments</li>
         * or Environment variable in shell</li>
         *
         * @param strValue value got from VM arguments or env in shell
         * @return Parsed value
         */
        T parseString(String strValue);

        /**
         * If {@link #parseString(String)} fails with exception, how to deal with the case.
         * Usually logging and alerting is a proper way.
         *
         * @param e exception
         */
        void onParseStringException(RuntimeException e);

        /**
         * Get value from {@link ConfigUtil#CONF_FILE_NAME} of k-v pairs
         *
         * @param key key
         * @return Value
         */
        T getValueFromConf(String key);

        /**
         * If {@link #getValueFromConf(String)} fails with exception, how to deal with the case.
         * Usually logging and alerting is a proper way.
         *
         * @param e exception
         */
        void onConfigException(ConfigException e);

        /**
         * Before returning do some auditing work
         *
         * @param key          key
         * @param value        value
         * @param defaultValue default value
         */
        void beforeReturn(String key, T value, T defaultValue);
    }

    /**
     * Abstract common callback for {@link Callback}
     *
     * @param <T> Return type
     */
    static abstract class AbstractCallback<T> implements Callback<T> {

        @Override
        public void onParseStringException(RuntimeException e) {
            LOGGER.error(e.getMessage());
        }

        @Override
        public void onConfigException(ConfigException e) {
            LOGGER.error(e.getMessage());
        }

        @Override
        public void beforeReturn(String key, T value, T defaultValue) {
            if (defaultValue.equals(value)) {
                LOGGER.info("Using default value {} for {}", defaultValue, key);
            } else {
                LOGGER.info("Overriding default value {} to {} for {}", defaultValue, value, key);
            }
        }
    }

}
