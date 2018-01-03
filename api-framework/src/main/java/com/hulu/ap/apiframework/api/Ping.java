package com.hulu.ap.apiframework.api;

import com.hulu.ap.apiframework.common.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Example code for GET endpoint.
 * Remove this class after familiar with Jersey.
 */
@Slf4j
@Path("/ping")
@Api(value = "/ping", description = "Ping the server")
public class Ping {

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation("Ping")
    public Response ping(
            @ApiParam(value = "Limit")
            @DefaultValue("0") @QueryParam("limit") int limit) {
        MDC.put(Constants.MDC_ENDPOINT_KEY, "ping");

        return Response.status(Response.Status.OK)
                .entity("+Pong \n" + "limit=" + limit)
                .type(MediaType.TEXT_PLAIN_TYPE)
                .build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation("Vote")
    public Response vote(
            @ApiParam(value = "Name")
            @FormParam("name") String name) {
        return Response.status(Response.Status.OK)
                .entity("+OK")
                .type(MediaType.TEXT_PLAIN_TYPE)
                .build();
    }
}
