package com.hulu.ap.example.production.dao.impl;

import com.hulu.ap.example.production.dao.OrderDao;
import com.hulu.ap.example.production.misc.ObjectCache;
import com.hulu.ap.example.production.vo.Order;

import java.util.List;
import java.util.Optional;

/**
 * @author xu.zhang
 */
public class OrderDaoImpl implements OrderDao {

    @Override
    public List<Order> getAll() {
        return ObjectCache.ORDER_LIST;
    }

    @Override
    public Order getById(int orderId) {
        Optional<Order> order = ObjectCache.ORDER_LIST.stream().filter(o -> o.getOrderId() == orderId).findFirst();
        if (order.isPresent()) {
            return order.get();
        } else {
            return null;
        }
    }

    @Override
    public Order create(Order order) {
        ObjectCache.ORDER_LIST.add(order);
        return order;
    }

    @Override
    public Order update(Order order) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteById(int orderId) {
        throw new UnsupportedOperationException();
    }
}
