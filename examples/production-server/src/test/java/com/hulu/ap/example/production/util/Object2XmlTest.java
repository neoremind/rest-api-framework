package com.hulu.ap.example.production.util;

import com.google.common.collect.Lists;
import com.hulu.ap.example.production.vo.Inventory;
import com.hulu.ap.example.production.vo.Order;
import com.hulu.ap.example.production.vo.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * @author xu.zhang
 */
@Ignore
public class Object2XmlTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testOrder() throws Exception {
        List<Order> orderList = Lists.newArrayList(
                makeOrder(100, "Jason", "20161012", Lists.newArrayList(1L, 2L, 4L, 6L)),
                makeOrder(101, "Tom", "20161111", Lists.newArrayList(3L, 7L)),
                makeOrder(102, "Brown", "20161215", Lists.newArrayList(8L))
        );
        String outFileName = "order.xml";
        String outFilePath = Object2Xml.object2XML(orderList, outFileName);
        Object assertobj = Object2Xml.xml2Object(outFilePath);
    }

    @Test
    public void testProduct() throws Exception {
        List<Product> productList = Lists.newArrayList(
                makeProduct(1L, "Book", "Zero to one", 19.99),
                makeProduct(2L, "Book", "Hadoop in action", 29.99),
                makeProduct(3L, "Tool", "Mining of Massive Datasets", 39.99),
                makeProduct(4L, "Book", "Java8", 5.99),
                makeProduct(5L, "TV", "Sumsung TV", 999.0),
                makeProduct(6L, "Phone", "Iphone7 Plus", 499.0),
                makeProduct(7L, "Shoes", "Zara", 15.15),
                makeProduct(8L, "Car", "Ford", 10.0)
        );
        String outFileName = "product.xml";
        String outFilePath = Object2Xml.object2XML(productList, outFileName);
        Object assertobj = Object2Xml.xml2Object(outFilePath);
    }

    @Test
    public void testInventory() throws Exception {
        List<Inventory> inventoryList = Lists.newArrayList(
                makeInventory(1L, 100),
                makeInventory(2L, 5),
                makeInventory(3L, 0),
                makeInventory(4L, 1),
                makeInventory(5L, 2),
                makeInventory(6L, 30),
                makeInventory(7L, 60),
                makeInventory(8L, 80)
        );
        String outFileName = "inventory.xml";
        String outFilePath = Object2Xml.object2XML(inventoryList, outFileName);
        Object assertobj = Object2Xml.xml2Object(outFilePath);
    }

    public Order makeOrder(int orderId, String createdBy, String orderPlacedDate, List<Long> productIdList) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setCreatedBy(createdBy);
        order.setOrderPlacedDate(DateUtil.parseDate(orderPlacedDate));
        order.setProductIdList(productIdList);
        return order;
    }

    public Product makeProduct(long productId, String category, String productName, double price) {
        Product product = new Product();
        product.setProductId(productId);
        product.setCategory(category);
        product.setProductName(productName);
        product.setPrice(price);
        return product;
    }

    public Inventory makeInventory(long productId, int availableNumber) {
        Inventory inventory = new Inventory();
        inventory.setProductId(productId);
        inventory.setAvailableNumber(100);
        return inventory;
    }
}
