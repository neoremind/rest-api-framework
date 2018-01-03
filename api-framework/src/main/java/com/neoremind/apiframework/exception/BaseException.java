package com.neoremind.apiframework.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;


/**
 * This is the basic exception the whole system use.
 * Every module can use module-specific exception extended from this class.
 */
@Getter
@NoArgsConstructor
public class BaseException extends RuntimeException {

    private static final long serialVersionUID = -8803029367265948136L;

    public static final String UNKNOWN_EXCEPTION_TYPE = "Unknown";

    private String exceptionType = UNKNOWN_EXCEPTION_TYPE;

    private ExceptionLevel exceptionLevel = ExceptionLevel.NOTIFY;

    private final Map<String, Object> exceptionContext = new HashMap<>();

    public BaseException(String errorMessage) {
        super(errorMessage);
    }

    public BaseException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException withExceptionLevel(ExceptionLevel exceptionLevel) {
        this.exceptionLevel = exceptionLevel;
        return this;
    }

    public BaseException withExceptionType(String exceptionType) {
        this.exceptionType = exceptionType;
        return this;
    }

    public BaseException withContext(String propertyName, Object propertyValue) {
        exceptionContext.put(propertyName, propertyValue);
        return this;
    }

    public void propagate() {
        throw this;
    }

    public enum ExceptionLevel {
        FATAL,
        ERROR,
        WARNING,
        NOTIFY
    }
}
