package com.neoremind.apiframework.guice;

import com.google.inject.Module;

/**
 * Guice enabled module definition extends from {@link Module} interface.
 * <p>
 * User custom defined module can be injected into server container and
 * works with servlet for bean management.
 *
 * @author xu.zhang
 */
public interface JettyModule extends Module {

}
