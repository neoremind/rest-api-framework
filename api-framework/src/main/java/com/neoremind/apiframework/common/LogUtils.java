package com.neoremind.apiframework.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.neoremind.apiframework.util.AddressUtils;
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

//TODO implement it to send failures to ElasticSearch
public final class LogUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger("Logger");

    private static final DateTimeFormatter FORMATTER = ISODateTimeFormat.dateTime();

    private static final ObjectWriter objectWriter = new ObjectMapper().writer();

    public static void debug(ObjectNode jsonObject) {
        insertMdc(jsonObject);
        try {
            LOGGER.debug(objectWriter.writeValueAsString(jsonObject));
        } catch (JsonProcessingException e) {
        }
    }

    public static void error(ObjectNode jsonObject) {
        insertMdc(jsonObject);
        try {
            LOGGER.error(objectWriter.writeValueAsString(jsonObject));
        } catch (JsonProcessingException e) {
        }
    }

    public static void info(ObjectNode jsonObject) {
        insertMdc(jsonObject);
        try {
            LOGGER.info(objectWriter.writeValueAsString(jsonObject));
        } catch (JsonProcessingException e) {
        }
    }

    public static void trace(ObjectNode jsonObject) {
        insertMdc(jsonObject);
        try {
            LOGGER.trace(objectWriter.writeValueAsString(jsonObject));
        } catch (JsonProcessingException e) {
        }
    }

    public static void warn(ObjectNode jsonObject) {
        insertMdc(jsonObject);
        try {
            LOGGER.warn(objectWriter.writeValueAsString(jsonObject));
        } catch (JsonProcessingException e) {
        }
    }

    public static void debug(String msg) {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.put("hostname", AddressUtils.getShortHostnameDefaultUnknown());
        objectNode.put("message", msg);
        objectNode.put("log_level", "debug");
        debug(objectNode);
    }

    public static void error(String msg) {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.put("hostname", AddressUtils.getShortHostnameDefaultUnknown());
        objectNode.put("message", msg);
        objectNode.put("log_level", "error");
        error(objectNode);
    }

    public static void info(String msg) {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.put("hostname", AddressUtils.getShortHostnameDefaultUnknown());
        objectNode.put("message", msg);
        objectNode.put("log_level", "info");
        info(objectNode);
    }

    public static void trace(String msg) {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.put("hostname", AddressUtils.getShortHostnameDefaultUnknown());
        objectNode.put("message", msg);
        objectNode.put("log_level", "trace");
        trace(objectNode);
    }

    public static void warn(String msg) {
        ObjectNode objectNode = JsonNodeFactory.instance.objectNode();
        objectNode.put("hostname", AddressUtils.getShortHostnameDefaultUnknown());
        objectNode.put("message", msg);
        objectNode.put("log_level", "warn");
        warn(objectNode);
    }

    private static void insertMdc(ObjectNode jsonObject) {
        String uuid = MDC.get(Constants.UUID_KEY);
        jsonObject.put(Constants.UUID_KEY, uuid);
        jsonObject.put("timestamp", FORMATTER.print(Instant.now().getMillis()));
    }
}
