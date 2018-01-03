package com.hulu.ap.example.production.exception;

import com.hulu.ap.apiframework.commons.ResponseWithCodeAndMsg;
import com.hulu.ap.example.production.resultcode.MyResultCode;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * For jersey global exception handler to deal with {@link ParamException}
 *
 * @author xu.zhang
 */
@Provider
public class ParamExceptionMapper implements ExceptionMapper<ParamException> {

    @Override
    public Response toResponse(ParamException e) {
        ResponseWithCodeAndMsg res = new ResponseWithCodeAndMsg();
        res.setCode(MyResultCode.PARAM_INVALID.getCode());
        res.setMsg(MyResultCode.PARAM_INVALID.getMessage(e.getMessage()).getMessage());
        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON)
                .entity(res)
                .build();
    }
}

