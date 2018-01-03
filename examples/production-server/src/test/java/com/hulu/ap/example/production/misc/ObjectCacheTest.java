package com.hulu.ap.example.production.misc;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author xu.zhang
 */
@Ignore
public class ObjectCacheTest {

    @Test
    public void test() {
        System.out.println(ObjectCache.ORDER_LIST);
        System.out.println(ObjectCache.PRODUCT_LIST);
        System.out.println(ObjectCache.INVENTORY_LIST);
    }

}
