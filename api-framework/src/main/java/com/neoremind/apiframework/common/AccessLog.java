package com.neoremind.apiframework.common;

/**
 * AccessLog
 *
 * @author xu.zhang
 */
public class AccessLog {

    private String fromIp;

    private String queryUrl;

    private String request;

    private Object response;

    private long elapsedTime;

    public AccessLog() {
        super();
    }

    public String getFromIp() {
        return fromIp;
    }

    public AccessLog setFromIp(String fromIp) {
        this.fromIp = fromIp;
        return this;
    }

    public String getQueryUrl() {
        return queryUrl;
    }

    public AccessLog setQueryUrl(String queryUrl) {
        this.queryUrl = queryUrl;
        return this;
    }

    public String getRequest() {
        return request;
    }

    public AccessLog setRequest(String request) {
        this.request = request;
        return this;
    }

    public Object getResponse() {
        return response;
    }

    public AccessLog setResponse(Object response) {
        this.response = response;
        return this;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public AccessLog setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
        return this;
    }
}
