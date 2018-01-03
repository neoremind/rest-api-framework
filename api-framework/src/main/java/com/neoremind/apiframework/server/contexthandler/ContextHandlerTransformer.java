package com.neoremind.apiframework.server.contexthandler;

import com.neoremind.apiframework.able.Transformer;
import com.neoremind.apiframework.spi.ContextHandlerBuilder;
import com.neoremind.apiframework.spi.Supplier;
import org.eclipse.jetty.server.handler.HandlerList;

import java.util.List;

/**
 * Context handler transformer to transform
 * a list of {@link ContextHandlerBuilder} to
 * {@link org.eclipse.jetty.server.handler.HandlerList}.
 * <p>
 * The {@link ContextHandlerBuilder} is an implementation of {@link Supplier}.
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
