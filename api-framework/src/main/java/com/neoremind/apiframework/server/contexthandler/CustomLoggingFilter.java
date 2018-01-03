package com.neoremind.apiframework.server.contexthandler;

import com.neoremind.apiframework.common.Constants;
import com.neoremind.apiframework.util.ThreadContext;
import jersey.repackaged.com.google.common.base.Throwables;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.message.internal.ReaderWriter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Custom logging filter to take down some important info
 *
 * @author xu.zhang
 */
public class CustomLoggingFilter extends LoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        ThreadContext.putContext(Constants.REQ, getEntityBody(requestContext));
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        ThreadContext.putContext(Constants.RES, responseContext.getEntity());
    }

    /**
     * Get request entity body
     *
     * @param requestContext request context
     * @return request entity body
     */
    private String getEntityBody(ContainerRequestContext requestContext) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = requestContext.getEntityStream();
        final StringBuilder b = new StringBuilder();
        try {
            ReaderWriter.writeTo(in, out);
            byte[] requestEntity = out.toByteArray();
            if (requestEntity.length == 0) {
                b.append("");
            } else {
                b.append(new String(requestEntity));
            }
            requestContext.setEntityStream(new ByteArrayInputStream(requestEntity));
        } catch (IOException ex) {
            Throwables.propagate(ex);
        }
        return b.toString();
    }
}