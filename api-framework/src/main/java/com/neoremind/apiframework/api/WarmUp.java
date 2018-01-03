package com.neoremind.apiframework.api;

/**
 * Do stuff before enabling this instance on load balance.
 * Be invoked in first health check call after JVM starts.
 */
public class WarmUp {

    private Boolean warmUpResult = null;

    boolean getWarmUpResult() {
        if (warmUpResult != null) {
            return warmUpResult;
        } else {
            warmUpResult = doGetWarmUpResult();
            return warmUpResult;
        }
    }

    private synchronized boolean doGetWarmUpResult() {
        if (warmUpResult != null) {
            return warmUpResult;
        }
        return true;
    }
}
