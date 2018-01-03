package com.hulu.ap.apiframework.util;

/**
 * System property utility.
 * Retrieve values through the following methods, ordered by priority.
 * <ul>
 * <li>1) <code>-D</code> in VM arguments</li>
 * <li>2) Environment variable in shell</li>
 * </ul>
 *
 * @author xu.zhang
 */
public class SystemPropUtil {

    /**
     * Get system property
     *
     * @param key         -D parameter or shell defined system environment property
     * @param defautValue Default value if not exist
     * @return System property
     */
    public static String getSystemProperty(String key, String defautValue) {
        String value = System.getProperty(key);
        if (value == null || value.length() == 0) {
            value = System.getenv(key);
            if (value == null || value.length() == 0) {
                value = defautValue;
            }
        }
        return value;
    }

    /**
     * Get system property
     *
     * @param dKey        -D parameter
     * @param shellKey    shell defined system environment property
     * @param defautValue Default value if not exist
     * @return System property
     */
    public static String getSystemProperty(String dKey, String shellKey, String defautValue) {
        String value = System.getProperty(dKey);
        if (value == null || value.length() == 0) {
            value = System.getenv(shellKey);
            if (value == null || value.length() == 0) {
                value = defautValue;
            }
        }
        return value;
    }

    /**
     * Is VM debug enabled
     *
     * @return True if VM debug enabled
     */
    public static boolean isDebug() {
        return java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments()
                .toString().indexOf("-agentlib:jdwp") > 0;
    }

}