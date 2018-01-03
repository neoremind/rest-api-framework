package com.neoremind.apiframework.server.requestfilter;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.neoremind.apiframework.common.AccessLog;
import com.neoremind.apiframework.common.AccessLogSupport;
import com.neoremind.apiframework.common.Constants;
import com.neoremind.apiframework.common.RequestUtils;
import com.neoremind.apiframework.metric.MetricClient;
import com.neoremind.apiframework.metric.MetricClientFactory;
import com.neoremind.apiframework.util.AddressUtils;
import com.neoremind.apiframework.util.IPUtils;
import com.neoremind.apiframework.util.ThreadContext;
import jersey.repackaged.com.google.common.base.Throwables;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * RecordRequestServletFilter
 *
 * @author xu.zhang
 */
public class RecordRequestServletFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordRequestServletFilter.class);

    private static final String X_REQUEST_ID = "X-Request-Id";

    private static final MetricClient METRIC_CLIENT = MetricClientFactory.getMetricClient();

    private static final String[] ingoreUrlPaths = {"/health_check"};

    private static final AccessLogSupport accessLogger = new AccessLogSupport();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        ThreadContext.init();
        long startTime = System.nanoTime();

        String url = RequestUtils.getUrl(request);
        Enumeration<String> headerNames = request.getHeaderNames();

        String requestId = UUID.randomUUID().toString();
        for (String headerName : Collections.list(headerNames)) {
            if (StringUtils.equalsIgnoreCase(headerName, X_REQUEST_ID)) {
                requestId = request.getHeader(headerName);
                break;
            }
        }

        ThreadContext.putContext(Constants.REQ_IP, IPUtils.getIpAddr(request));
        ThreadContext.putContext(Constants.REQ_URL, url);
        MDC.put(Constants.MDC_ENDPOINT_KEY, RequestUtils.getEndpointName(request));
        MDC.put(Constants.UUID_KEY, requestId);
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader(X_REQUEST_ID, requestId);

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            String method = ((HttpServletRequest) servletRequest).getMethod();
            String endpointName = getEndpointName();

            try {
                long elapsedInMilliseconds = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
                if (response.getStatus() == HttpServletResponse.SC_NOT_FOUND) {
                    endpointName = "UNKNOWN";
                }

                METRIC_CLIENT.count("QPS", 1);
                METRIC_CLIENT.count(String.format("ByEndpoint.%s.QPS", endpointName), 1);

                METRIC_CLIENT.count("RPS", 1);
                METRIC_CLIENT.timing("ResponseTime", (int) elapsedInMilliseconds);
                METRIC_CLIENT.count(String.format("StatusCode.%s_%s.RPS", method, response.getStatus()), 1);

                METRIC_CLIENT.count(String.format("ByEndpoint.%s.RPS", endpointName), 1);
                METRIC_CLIENT.timing(String.format("ByEndpoint.%s.ResponseTime", endpointName), (int) elapsedInMilliseconds);
                METRIC_CLIENT.timing(String.format("ByEndpoint2.%s.ResponseTime", endpointName), (int) elapsedInMilliseconds);
                METRIC_CLIENT.count(
                        String.format("ByEndpoint.%s.StatusCode.%s_%s.RPS", endpointName, method, response.getStatus()),
                        1);

                Map<String, String> headers = new HashMap<>(8);

                for (String headerName : Collections.list(headerNames)) {
                    String headerValue = request.getHeader(headerName);
                    headers.put(headerName, headerValue);
                }

                ObjectNode logObject = JsonNodeFactory.instance.objectNode();
                logObject.put("method", method);
                logObject.put("query", url);
                logObject.put("headers", headers.toString());
                logObject.put("endpoint", endpointName);
                logObject.put("hostname", AddressUtils.getShortHostname());
                logObject.put("status_code", response.getStatus());
                logObject.put("time_used_in_milliseconds", elapsedInMilliseconds);

                if (!ArrayUtils.contains(ingoreUrlPaths, url)) {
                    LOGGER.info("{}", logObject);
                    accessLogger.log(new AccessLog()
                            .setFromIp((String) ThreadContext.getContext(Constants.REQ_IP))
                            .setQueryUrl(url)
                            .setRequest((String) ThreadContext.getContext(Constants.REQ))
                            .setResponse(ThreadContext.getContext(Constants.RES))
                            .setElapsedTime(elapsedInMilliseconds));
                }
            } catch (Exception e) {
                //TODO should report to ElasticSearch
                Throwables.propagate(e);
            } finally {
                MDC.put(Constants.MDC_USER_ID, null);
                MDC.put(Constants.MDC_ENDPOINT_KEY, null);
                MDC.put(Constants.UUID_KEY, null);
                ThreadContext.clean();
            }
        }
    }

    public static String getEndpointName() {
        String endpointName = MDC.get(Constants.MDC_ENDPOINT_KEY);
        if (StringUtils.isBlank(endpointName)) {
            endpointName = "UNKNOWN";
        }
        return endpointName;
    }

    @Override
    public void destroy() {
    }
}
