package com.neoremind.apiframework.common;

import com.neoremind.apiframework.util.JsonMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Log access log
 *
 * @author xu.zhang
 */
public class AccessLogSupport implements AutoCloseable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessLogSupport.class);

    private ExecutorService executorService;

    private static final int MAX_RESPONSE_WIDTH = 2000;

    private JsonMapper jsonMapper = JsonMapper.buildNormalMapper();

    public AccessLogSupport() {
        this.executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    }

    public void log(AccessLog log) {
        if (log == null) {
            return;
        }
        executorService.submit(new LogTask(log));
    }

    @Override
    public void close() throws Exception {
        executorService.shutdownNow();
    }

    private class LogTask implements Runnable {

        private AccessLog log;

        public LogTask(AccessLog log) {
            this.log = log;
        }

        @Override
        public void run() {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("from_ip=");
                sb.append(log.getFromIp());
                sb.append("\t");
                sb.append("path=");
                sb.append(log.getQueryUrl());
                sb.append("\t");
                sb.append("req=");
                if (log.getRequest() != null) {
                    sb.append(log.getRequest()).append('\t');
                } else {
                    sb.append('\t');
                }
                sb.append("res=");
                if (log.getResponse() != null) {
                    sb.append(StringUtils.abbreviate(jsonMapper.toJson(log.getResponse()), MAX_RESPONSE_WIDTH)).append('\t');
                } else {
                    sb.append('\t');
                }
                sb.append("costMs=");
                sb.append(log.getElapsedTime());
                LOGGER.info(sb.toString());
            } catch (Exception e) {
                LOGGER.error("Error occurred when log access", e);
            } finally {
                log = null;
            }
        }
    }

}

