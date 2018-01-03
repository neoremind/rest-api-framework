package com.neoremind.apiframework.exception.exceptionmapper;

import com.neoremind.apiframework.util.ExceptionUtil;
import org.glassfish.jersey.server.ParamException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JerseyBadRequestExceptionMapper implements ExceptionMapper<ParamException> {

    @Override
    public Response toResponse(ParamException exception) {
        String message = "Failed to parse param: " + exception.getParameterName() + "\n";
        message += ExceptionUtil.getStackTrace(exception);

        return Response.status(Response.Status.BAD_REQUEST)
            .type(MediaType.TEXT_PLAIN)
            .entity(message)
            .build();
    }
}
