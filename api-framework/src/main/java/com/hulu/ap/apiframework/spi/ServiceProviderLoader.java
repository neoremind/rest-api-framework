package com.hulu.ap.apiframework.spi;

import com.hulu.ap.apiframework.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Load Service provider interface (SPI) implementations from META-INF/services.
 * <p>
 * SPI enables users to get a set of public interfaces and abstract classes that a service defines.
 * The SPI file defines the classes available to the SPI interface.
 *
 * @author xu.zhang
 */
public class ServiceProviderLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceProviderLoader.class);

    /**
     * Cache instances by provider name
     */

    private static ConcurrentMap<Class<?>, List<?>> EXTENSION_LOCATOR = new ConcurrentHashMap<>(4);

    /**
     * For thread-safe reason
     */
    private static Lock lock = new ReentrantLock();

    /**
     * Get all instances instances dynamically of a class type
     * <p>
     * //TODO Multi-thread loading to be done
     *
     * @param type Target class type
     * @return All instances of the specific class type
     */
    public static <T> List<T> getAllInstances(Class<T> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type should not be empty");
        }

        List<T> loader = (List<T>) EXTENSION_LOCATOR.get(type);
        if (loader == null) {
            try {
                lock.lock();
                List<T> instanceList = new ArrayList<T>(4);
                ServiceLoader<T> serviceLoader = ServiceLoader.load(type);
                for (T service : serviceLoader) {
                    LOGGER.info("Loading " + service.getClass().getName());
                    instanceList.add(service);
                }
                if (CollectionUtil.isEmpty(instanceList)) {
                    LOGGER.warn("Find no SPI instance for " + type.getName());
                }
                loader = Collections.unmodifiableList(instanceList);
                if (EXTENSION_LOCATOR.putIfAbsent(type, instanceList) != instanceList) {
                    loader = (List<T>) EXTENSION_LOCATOR.get(type);
                }
            } catch (Exception e) {
                LOGGER.error("Error to load SPI implementation from META-INF/services/" + type, e);
            } finally {
                lock.unlock();
            }
        }
        return loader;
    }

    /**
     * Get first instance dynamically of a class type
     *
     * @param type Target class type
     * @return Instance of the specific class type
     */
    public static <T> T getFirstInstances(Class<T> type) {
        List<T> instances = getAllInstances(type);
        if (CollectionUtil.isEmpty(instances)) {
            return null;
        }
        return instances.get(0);
    }

}
