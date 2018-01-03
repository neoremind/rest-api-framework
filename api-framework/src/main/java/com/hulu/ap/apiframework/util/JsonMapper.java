package com.hulu.ap.apiframework.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonMapper {

    private static final Logger LOG = LoggerFactory.getLogger(JsonMapper.class);

    /**
     * mapper
     */
    private ObjectMapper mapper;

    /**
     * Creates a new instance of JsonMapper.
     *
     * @param inclusion
     */
    public JsonMapper(JsonSerialize.Inclusion inclusion) {
        mapper = new ObjectMapper();
    }

    /**
     * contains all fields.
     */
    public static JsonMapper buildNormalMapper() {
        return new JsonMapper(JsonSerialize.Inclusion.ALWAYS);
    }

    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            LOG.warn("parse json string error:" + jsonString, e);
            return null;
        }
    }

    public <T> T fromJsonBytes(byte[] bytes, Class<T> clazz) {
        if (bytes == null || bytes.length == 0)
            return null;
        else {
            try {
                return mapper.readValue(bytes, clazz);
            } catch (Exception e) {
                LOG.warn("parse json bytes error:", e);
                return null;
            }
        }
    }

    public JsonNode fromJson(byte[] bytes) {
        if (bytes == null || bytes.length == 0)
            return null;
        else {
            try {
                return mapper.readTree(bytes);
            } catch (Exception e) {
                LOG.warn("parse json bytes error:", e);
                return null;
            }
        }
    }

    public String toJson(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            LOG.warn("write to json string error:" + object, e);
            return null;
        }
    }

}
