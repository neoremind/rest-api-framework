package com.hulu.ap.apiframework.client;

import net.neoremind.dynamicproxy.Interceptor;
import net.neoremind.dynamicproxy.Invocation;

import java.lang.reflect.Field;
import java.util.WeakHashMap;

/**
 * Leverage <a href="https://github.com/neoremind/dynamic-proxy"></a> to
 * 1) re-throw exception, or
 * 2) just log.
 * if {@link com.hulu.ap.apiframework.commons.ResponseWithCodeAndMsg#code} is not OK.
 *
 * @author xu.zhang
 */
public abstract class AbstractApiStatusNotOkInterceptor implements Interceptor {

    /**
     * Cache class and its field for better performance
     */
    private static final WeakHashMap<Class<?>, Pair> CACHED = new WeakHashMap<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object ret = invocation.proceed();
        if (ret == null) {
            return ret;
        }
        Class<?> clazz = ret.getClass();

        if (CACHED.get(clazz) == null) {
            Field codeField = ReflectionUtil.getField(ret.getClass(), "code");
            if (codeField != null) {
                codeField.setAccessible(true);
            }

            Field msgField = ReflectionUtil.getField(ret.getClass(), "msg");
            if (msgField != null) {
                msgField.setAccessible(true);
            }

            if (codeField != null && msgField != null) {
                CACHED.put(clazz, new Pair(true, codeField, msgField));
            } else {
                CACHED.put(clazz, DUMMY_PAIR);
            }
        }

        Pair pair = CACHED.get(clazz);
        if (pair != null && pair.isStatusAndMsgSupported) {
            int code = (int) pair.code.get(ret);
            if (code != Defaults.RESPONSE_STATUS_CODE_OK) {
                String msg = (String) pair.msg.get(ret);
                StringBuilder errorMsg = new StringBuilder();
                errorMsg.append("code:");
                errorMsg.append(code);
                errorMsg.append(", msg:");
                errorMsg.append(msg);
                handle(errorMsg.toString());
            }
        }
        return ret;
    }

    /**
     * Abstract method for subclass to override how to deal with error message
     *
     * @param errorMsg error message
     */
    public abstract void handle(String errorMsg);

    /**
     * Cached value type
     */
    static class Pair {
        public boolean isStatusAndMsgSupported = false;
        public Field code;
        public Field msg;

        public Pair(boolean isStatusAndMsgSupported, Field code, Field msg) {
            this.isStatusAndMsgSupported = isStatusAndMsgSupported;
            this.code = code;
            this.msg = msg;
        }
    }

    /**
     * For not support {@link com.hulu.ap.apiframework.commons.ResponseWithCodeAndMsg}, by default see it as dummy one
     */
    private static final Pair DUMMY_PAIR = new Pair(false, null, null);

}
