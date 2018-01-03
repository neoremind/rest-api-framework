package com.neoremind.apiframework.exception.exceptionmapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Abstraction class of exception mapper, aiming to act as a global exception handler.
 * <p>
 * For unexpected exceptions, usually caused from business layer, could result in call termination. This mapper is a default
 * handler for such cases, by default all exceptions should be logged properly for tracking.
 *
 * @author xu.zhang
 */
public abstract class AbstractExceptionMapper<E extends Throwable> implements ExceptionMapper<E> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractExceptionMapper.class);

    @Override
    public Response toResponse(E exception) {
        LOGGER.error(exception.getMessage(), exception);
        return doResponse(exception);
    }

    protected abstract Response doResponse(E exception);

}
