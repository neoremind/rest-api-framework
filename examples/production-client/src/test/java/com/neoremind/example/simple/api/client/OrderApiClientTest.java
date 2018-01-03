package com.neoremind.example.simple.api.client;

import com.google.common.collect.Lists;
import com.hulu.ap.example.production.server.client.ApiException;
import com.hulu.ap.example.production.server.client.Order;
import com.hulu.ap.example.production.server.client.OrderApi;
import com.hulu.ap.example.production.server.client.OrderDtoList;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author xu.zhang
 */
@Ignore
public class OrderApiClientTest {

    @Test
    public void test_query_all_orders() {
        OrderApi api = new OrderApi();
        OrderDtoList list = null;
        try {
            list = api.queryAllOrders();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(list.getOrderList());
    }

    @Test
    public void test_query_by_orderid() {
        OrderApi api = new OrderApi();
        try {
            Order orderDto = api.queryByOrderId(100);
            System.out.println(orderDto);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_create_order() {
        OrderApi api = new OrderApi();
        try {
            Order o = new Order();
            o.setProductIds(Lists.newArrayList(1L, 2L));
            o.setCreatedBy("John");
            o.setOrderPlacedDate("20170101");
            Order orderDto = api.create(o);
            System.out.println(orderDto);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

}
