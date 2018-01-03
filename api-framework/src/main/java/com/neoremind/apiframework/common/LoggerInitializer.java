package com.neoremind.apiframework.common;

import com.neoremind.apiframework.util.SystemPropUtil;

public final class LoggerInitializer {
    static {
        String env = SystemPropUtil.getSystemProperty("rest.api.env", "default");
        System.setProperty("logback.configurationFile", "conf/" + env + "/logback.xml");
    }
}
