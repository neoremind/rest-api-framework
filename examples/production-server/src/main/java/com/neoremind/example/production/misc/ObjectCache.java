package com.neoremind.example.production.misc;

import com.neoremind.example.production.util.Object2Xml;
import com.neoremind.example.production.vo.Inventory;
import com.neoremind.example.production.vo.Order;
import com.neoremind.example.production.vo.Product;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Because prototype of concept demo, there is no underlying real database.
 * <p>
 * So <code>DAO</code>s rely on this in memeory cache to query objects.
 * <p>
 * Objects are loaded from XML files.
 *
 * @author xu.zhang
 */
public class ObjectCache {

    /**
     * Mock order id uuid generator
     */
    public static AtomicInteger GLOBAL_ID;

    /**
     * order list
     */
    public static List<Order> ORDER_LIST;

    /**
     * product list
     */
    public static List<Product> PRODUCT_LIST;

    /**
     * inventory list
     */
    public static List<Inventory> INVENTORY_LIST;

    /**
     * XML file path
     */
    public static final String FILE_PATH_PREFIX = "examples/production-server/target/classes/";

    static {
        GLOBAL_ID = new AtomicInteger(1000);
        try {
            ORDER_LIST = Object2Xml.xml2Object(FILE_PATH_PREFIX + "com/neoremind/example/production/misc/order.xml");
            PRODUCT_LIST = Object2Xml.xml2Object(FILE_PATH_PREFIX + "com/neoremind/example/production/misc/product.xml");
            INVENTORY_LIST = Object2Xml.xml2Object(FILE_PATH_PREFIX + "com/neoremind/example/production/misc/inventory.xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
