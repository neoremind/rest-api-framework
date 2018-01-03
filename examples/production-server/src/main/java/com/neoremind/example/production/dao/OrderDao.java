package com.neoremind.example.production.dao;


import com.neoremind.example.production.vo.Order;

import java.util.List;

/**
 * @author xu.zhang
 */
public interface OrderDao {

    List<Order> getAll();

    Order getById(int orderId);

    Order create(Order order);

    Order update(Order order);

    boolean deleteById(int orderId);
}
