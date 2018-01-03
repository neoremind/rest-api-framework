package com.neoremind.apiframework.exception.exceptionmapper;

import com.neoremind.apiframework.exception.AuthenticationException;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException> {

    @Override
    public Response toResponse(AuthenticationException e) {
        return Response.status(Response.Status.FORBIDDEN)
            .type(MediaType.TEXT_PLAIN)
            .entity(ExceptionUtils.getStackTrace(e))
            .build();
    }
}
