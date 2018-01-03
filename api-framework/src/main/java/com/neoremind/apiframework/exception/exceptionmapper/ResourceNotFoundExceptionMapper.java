package com.neoremind.apiframework.exception.exceptionmapper;

import com.neoremind.apiframework.exception.ResourceNotFoundException;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {

    @Override
    public Response toResponse(ResourceNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND)
            .type(MediaType.TEXT_PLAIN)
            .entity(ExceptionUtils.getStackTrace(e))
            .build();
    }
}
