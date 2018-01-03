package com.hulu.ap.apiframework.api;

import com.hulu.ap.apiframework.common.Constants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Responsible for load balance health check
 */
//@Slf4j
@Path("/test_fail")
@Api(value = "/test_fail", description = "Test fail endpoint")
public class TestFail {

    @GET
    @Produces("application/json; charset=utf-8")
    @ApiOperation(value = "test fail endpoint")
    public final Response sendFail() {
        MDC.put(Constants.MDC_ENDPOINT_KEY, "get_test_fail");

        //log.error("pixboxtest test fail logged!");
        throw new RuntimeException("Kalas test fail thrown!");
    }
}
