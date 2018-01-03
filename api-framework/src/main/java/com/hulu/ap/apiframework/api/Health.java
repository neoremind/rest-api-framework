package com.hulu.ap.apiframework.api;

import com.hulu.ap.apiframework.common.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.MDC;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Responsible for load balance health check
 */
@Path("/health_check")
@Api(value = "/health_check", description = "Health Check")
public class Health {
    private final WarmUp warmup;

    public Health(WarmUp warmup) {
        this.warmup = warmup;
    }

    public Health() {
        this.warmup = new WarmUp();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Health Check")
    public final Response health() {
        MDC.put(Constants.MDC_ENDPOINT_KEY, "get_health_check");

        if (warmup.getWarmUpResult()) {
            return Response.status(Response.Status.OK)
                    .entity("I'm UP")
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .build();
        } else {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE)
                    .entity("Warmup failed")
                    .type(MediaType.TEXT_PLAIN_TYPE)
                    .build();
        }
    }
}
