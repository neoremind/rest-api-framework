package com.hulu.ap.apiframework.exception.exceptionmapper;

import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RuntimeExceptionMapper extends AbstractExceptionMapper<Throwable> implements ExceptionMapper<Throwable> {

    @Override
    protected Response doResponse(Throwable throwable) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.TEXT_PLAIN)
                .entity(ExceptionUtils.getStackTrace(throwable))
                .build();
    }
}
