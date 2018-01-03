package com.neoremind.apiframework.common;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

    public static String getUrl(HttpServletRequest request) {
        if (request == null) {
            return "UNKNOWN";
        }

        String url;
        if (StringUtils.isBlank(request.getQueryString())) {
            url = request.getRequestURI();
        } else {
            url = request.getRequestURI() + "?" + request.getQueryString();
        }

        return url;
    }

    public static String getEndpointName(HttpServletRequest servletRequest) {
        String uri = servletRequest.getRequestURI();
        if (StringUtils.isEmpty(uri)) {
            return "";
        }
        int slashIndex = uri.indexOf('/');
        if (slashIndex == -1) {
            return uri;
        } else {
            if (uri.startsWith("/")) {
                uri = uri.substring(1, uri.length());
            }
            slashIndex = uri.indexOf('/');
            if (slashIndex == -1) {
                return uri;
            }
            String requestURI = uri.substring(0, slashIndex);
            String method = servletRequest.getMethod();
            String endpointName = method.toUpperCase() + "_" + requestURI;
            return endpointName;
        }
    }
}
