package com.neoremind.apiframework.exception.exceptionmapper;

import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JerseyNotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
            .type(MediaType.TEXT_PLAIN)
            .entity(ExceptionUtils.getStackTrace(exception))
            .build();
    }
}
