package com.hulu.ap.apiframework.server.filter;

import com.hulu.ap.apiframework.server.requestfilter.RecordRequestServletFilter;
import com.hulu.ap.apiframework.spi.FilterHolderBuilder;
import org.eclipse.jetty.servlet.FilterHolder;

/**
 * Record request servlet filter.
 * The main purpose of introducing this filter is to take down every request and response information
 * for logging. Server handling information can be recorded in local log files or remote log aggregation
 * infrastructure like <code>logstash</code>, etc.
 *
 * @author xu.zhang
 */
public class RecordRequestServletFilterHolder implements FilterHolderBuilder {

    @Override
    public FilterHolder get() {
        FilterHolder recordRequestFilterHolder = new FilterHolder();
        recordRequestFilterHolder.setFilter(new RecordRequestServletFilter());
        return recordRequestFilterHolder;
    }
}
