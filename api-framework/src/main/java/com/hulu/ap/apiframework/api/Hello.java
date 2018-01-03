package com.hulu.ap.apiframework.api;

import com.hulu.ap.apiframework.common.Constants;
import com.hulu.ap.apiframework.util.AddressUtils;
import com.hulu.ap.apiframework.util.ConfigUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Index page
 */
@Slf4j
@Path("/")
@Api(value = "/", description = "Hello World")
public class Hello {

    @GET
    @Produces({MediaType.TEXT_PLAIN})
    @ApiOperation("")
    public Response hello() {
        MDC.put(Constants.MDC_ENDPOINT_KEY, "index");
        //TODO refine by template engine
        return Response.status(Response.Status.OK)
                .entity(getEntity())
                .type(MediaType.TEXT_HTML)
                .build();
    }

    /**
     * Get HTML page
     *
     * @return HTML page snippet
     */
    private String getEntity() {
        StringBuilder sb = new StringBuilder();
        sb.append("<a href=\"swagger.json\">Swagger JSON</a>");
        sb.append("<br></br>");
        sb.append("<a href=\"http://petstore.swagger.io?url=http://");
        try {
            sb.append(AddressUtils.getLocalIPv4Address());
        } catch (SocketException e) {
            try {
                sb.append(AddressUtils.getShortHostname());
            } catch (UnknownHostException e1) {
                sb.append("localhost");
            }
        }
        sb.append(":");
        sb.append(ConfigUtil.getInt(Constants.ServerDefaults.PORT.getKey(), Constants.ServerDefaults.PORT.getDefaultValue()));
        sb.append("/swagger.json\">Swagger UI - API protocol</a>");
        sb.append("<br></br>");
        sb.append("<a href=\"http://swagger.prod.hulu.com/?url=http://");
        try {
            sb.append(AddressUtils.getLocalIPv4Address());
        } catch (SocketException e) {
            try {
                sb.append(AddressUtils.getShortHostname());
            } catch (UnknownHostException e1) {
                sb.append("localhost");
            }
        }
        sb.append(":");
        sb.append(ConfigUtil.getInt(Constants.ServerDefaults.PORT.getKey(), Constants.ServerDefaults.PORT.getDefaultValue()));
        sb.append("/swagger.json\">Swagger UI - Call</a>");
        return sb.toString();
    }
}
