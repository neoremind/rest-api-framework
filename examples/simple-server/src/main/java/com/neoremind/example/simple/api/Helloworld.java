package com.neoremind.example.simple.api;

import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helle world example
 */
@Path("/helloworld")
@Api(value = "helloworld", description = "hello world")
public class Helloworld {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Simply echo message by specifying path query param
     *
     * @param message Path param
     * @return Standard text/plain result
     */
    @GET
    @Path("/1.0/echo/{message}")
    @ApiOperation(value = "echo message", response = Response.class)
    public Response echoBlackholeMessage(
            @ApiParam(value = "message") @DefaultValue("hi") @PathParam("message") String message) {
        logger.info("got " + message);
        return Response.ok(message).type(MediaType.TEXT_PLAIN).build();
    }

    /**
     * Simply echo message back with type of <code>String</code> by specifying path query param
     *
     * @param message Path param
     * @return String
     */
    @GET
    @Path("/1.0/echo2/{message}")
    @ApiOperation(value = "echo message", response = String.class)
    public String echoMessage(
            @ApiParam(value = "message") @DefaultValue("hi") @PathParam("message") String message) {
        return message;
    }

    /**
     * Echo number back in JSON format
     *
     * @param number Query param
     * @return Result
     */
    @POST
    @Path("/1.0/echoNumberInJSONFormat")
    @ApiOperation(value = "echo number in JSON format", response = Result.class)
    @Produces(MediaType.APPLICATION_JSON)
    public Result echoNumberInJSONFormat(
            @ApiParam(value = "number") @DefaultValue("100") @QueryParam("number") int number)
            throws HelloworldException {
        logger.info("got " + number);
        if (number == 0) {
            throw new HelloworldException("number is valid with " + number);
        }
        return new Result("number", number);
    }

    /**
     * Accept list request then echo back list response
     *
     * @param requests Request list
     * @return Result list
     */
    @POST
    @Path("/1.0/echoListBack")
    @ApiOperation(value = "echo list back", response = List.class)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Result> echoListBack(
            @ApiParam(value = "request") List<Request> requests) {
        logger.info("got {} requests", requests.size());
        return Lists.transform(requests, r -> new Result(r.getName(), r.getId()));
    }

    /**
     * Accept list request then echo back list response
     *
     * @param requests Request list
     * @return Result list with wrapper
     */
    @POST
    @Path("/1.0/echoListBackWithWrapper")
    @ApiOperation(value = "echo list back", response = ResultWrapper.class)
    @Produces(MediaType.APPLICATION_JSON)
    public ResultWrapper echoListBackWithWrapper(
            @ApiParam(value = "request") List<Request> requests) {
        logger.info("got {} requests", requests.size());
        ResultWrapper wrapper = new ResultWrapper();
        wrapper.setResultList(requests.stream().map(r -> new Result(r.getName(), r.getId())).collect(Collectors.toList()));
        return wrapper;
    }

    /**
     * Echo date back
     *
     * @param dateDto date dto
     * @return DateDto
     */
    @POST
    @Path("/1.0/echoDate")
    @ApiOperation(value = "echoDate", response = DateDto.class)
    @Produces(MediaType.APPLICATION_JSON)
    public DateDto echoDate(
            @ApiParam(value = "request") DateDto dateDto) {
        logger.info("got {} requests", dateDto);
        return dateDto;
    }

    /**
     * Simple request
     */
    static class Request {
        private String name;

        private int id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    /**
     * Simple result
     */
    static class Result {
        private String key;
        private int value;

        public Result() {
        }

        public Result(String key, int value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    /**
     * Simple result to wrap multiple result
     */
    static class ResultWrapper {
        private List<Result> resultList;

        public List<Result> getResultList() {
            return resultList;
        }

        public void setResultList(List<Result> resultList) {
            this.resultList = resultList;
        }
    }

    class HelloworldException extends RuntimeException {

        public HelloworldException(String msg) {
            super(msg);
        }
    }

    static class DateDto {

        //@ApiModelProperty(dataType = "date")
        @ApiModelProperty(dataType = "dateTime")
        private Date date;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }
}
