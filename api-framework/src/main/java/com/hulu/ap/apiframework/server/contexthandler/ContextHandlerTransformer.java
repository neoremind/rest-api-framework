package com.hulu.ap.apiframework.server.contexthandler;

import com.hulu.ap.apiframework.able.Transformer;
import com.hulu.ap.apiframework.spi.ContextHandlerBuilder;
import org.eclipse.jetty.server.handler.HandlerList;

import java.util.List;

/**
 * Context handler transformer to transform
 * a list of {@link ContextHandlerBuilder} to
 * {@link org.eclipse.jetty.server.handler.HandlerList}.
 * <p>
 * The {@link ContextHandlerBuilder} is an implementation of {@link com.hulu.ap.apiframework.spi.Supplier}.
 *
 * @author xu.zhang
 */
public class ContextHandlerTransformer implements Transformer<List<ContextHandlerBuilder>, HandlerList> {

    @Override
    public HandlerList transform(List<ContextHandlerBuilder> contextHandlerBuilders) {
        HandlerList handlerList = new HandlerList();
        for (ContextHandlerBuilder contextHandlerBuilder : contextHandlerBuilders) {
            handlerList.addHandler(contextHandlerBuilder.get());
        }
        return handlerList;
    }

}
