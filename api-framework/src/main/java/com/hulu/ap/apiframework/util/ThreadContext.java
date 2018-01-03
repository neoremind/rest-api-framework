package com.hulu.ap.apiframework.util;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: ThreadHolder
 *
 * @author xu.zhang
 */
public class ThreadContext {

    private static final ThreadLocal<Map<String, Object>> CTX_HOLDER = new ThreadLocal<Map<String, Object>>();

    public static final void putContext(String key, Object value) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx == null) {
            ctx = new HashMap<String, Object>();
            CTX_HOLDER.set(ctx);
        }
        ctx.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static final <T extends Object> T getContext(String key) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx == null) {
            return null;
        }
        return (T) ctx.get(key);
    }

    public static final Map<String, Object> getContext() {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx == null) {
            return null;
        }
        return ctx;
    }

    public static final void remove(String key) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx != null) {
            ctx.remove(key);
        }
    }

    public static final boolean contains(String key) {
        Map<String, Object> ctx = CTX_HOLDER.get();
        if (ctx != null) {
            return ctx.containsKey(key);
        }
        return false;
    }

    public static final void clean() {
        CTX_HOLDER.set(null);
    }

    public static final void init() {
        CTX_HOLDER.set(new HashMap<String, Object>());
    }

}
