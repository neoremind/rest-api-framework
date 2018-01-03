package com.neoremind.apiframework.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Exception handling utility
 *
 * @author xu.zhang
 */
public abstract class ExceptionUtil {

    /**
     * Check whether the throwable was caused by a specific tpe
     *
     * @param throwable To be checked throwable
     * @param causeType Specific type
     * @return If the throwable type matchs, the return true, else false
     */
    public static boolean causedBy(Throwable throwable, Class<? extends Throwable> causeType) {
        Set<Throwable> causes = new HashSet<>();
        for (; throwable != null && !causeType.isInstance(throwable) && !causes.contains(throwable); throwable =
                throwable.getCause()) {
            causes.add(throwable);
        }

        return throwable != null && causeType.isInstance(throwable);
    }

    /**
     * Get root cause of a throwable
     *
     * @param throwable Throwable
     * @return Root cause
     */
    public static Throwable getRootCause(Throwable throwable) {
        List<Throwable> causes = getCauses(throwable, true);

        return causes.isEmpty() ? null : causes.get(0);
    }

    /**
     * Get all the causes of a throwable
     *
     * @param throwable Throwable
     * @return All causes including the throwable itself, order by occurrence
     */
    public static List<Throwable> getCauses(Throwable throwable) {
        return getCauses(throwable, false);
    }

    /**
     * Get all the causes of a throwable
     *
     * @param throwable Throwable
     * @param reversed  Is ordered reversely
     * @return All causes including the throwable itself, order by occurrence
     */
    public static List<Throwable> getCauses(Throwable throwable, boolean reversed) {
        LinkedList<Throwable> causes = new LinkedList<>();

        for (; throwable != null && !causes.contains(throwable); throwable = throwable.getCause()) {
            if (reversed) {
                causes.addFirst(throwable);
            } else {
                causes.addLast(throwable);
            }
        }

        return causes;
    }

    /**
     * Transform checked exception to <code>RuntimeException</code>
     *
     * @param exception Checked exception
     * @return to <code>RuntimeException</code
     */
    public static RuntimeException toRuntimeException(Throwable exception) {
        return toRuntimeException(exception, null);
    }

    /**
     * Construct error messages to <code>RuntimeException</code>。
     *
     * @param message Error messages
     * @return to <code>RuntimeException</code
     */
    public static RuntimeException toRuntimeException(String message) {
        return new RuntimeException(message);
    }

    /**
     * Transform exception to<code>RuntimeException</code>
     *
     * @param exception             Exception
     * @param runtimeExceptionClass Target exception type
     * @return to <code>RuntimeException</code
     */
    public static RuntimeException toRuntimeException(Throwable exception,
                                                      Class<? extends RuntimeException> runtimeExceptionClass) {
        if (exception == null) {
            return null;
        }

        if (exception instanceof RuntimeException) {
            return (RuntimeException) exception;
        }
        if (runtimeExceptionClass == null) {
            return new RuntimeException(exception);
        }

        RuntimeException runtimeException;

        try {
            runtimeException = runtimeExceptionClass.newInstance();
        } catch (Exception ee) {
            return new RuntimeException(exception);
        }

        runtimeException.initCause(exception);
        return runtimeException;

    }

    /**
     * Throw throwable, but no need to declare <code>throws Throwable</code>, or distinguish <code>Exception</code> or</code>Error</code>
     *
     * @param throwable Throwable
     * @throws Exception
     */
    public static void throwExceptionOrError(Throwable throwable) throws Exception {
        if (throwable instanceof Exception) {
            throw (Exception) throwable;
        }

        if (throwable instanceof Error) {
            throw (Error) throwable;
        }

        throw new RuntimeException(throwable); // unreachable code
    }

    /**
     * Throw <code>RuntimeException</code>, but no need to declare <code>throws Throwable</code>, or distinguish <code>Exception</code> or</code>Error</code>
     *
     * @param throwable Throwable
     */
    public static void throwRuntimeExceptionOrError(Throwable throwable) {
        if (throwable instanceof Error) {
            throw (Error) throwable;
        }

        if (throwable instanceof RuntimeException) {
            throw (RuntimeException) throwable;
        }

        throw new RuntimeException(throwable);
    }

    /**
     * Get stack trace string of a throwable
     *
     * @param throwable Throwable
     * @return Stacktrace string
     */
    public static String getStackTrace(Throwable throwable) {
        StringWriter buffer = new StringWriter();
        PrintWriter out = new PrintWriter(buffer);

        throwable.printStackTrace(out);
        out.flush();

        return buffer.toString();
    }

}
