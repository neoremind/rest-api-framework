package com.hulu.ap.example.production.service;

import com.hulu.ap.example.production.vo.Order;

import java.util.List;

/**
 * @author xu.zhang
 */
public interface OrderService {

    Order getById(int orderId);

    List<Order> getAll();

    Order create(Order order);

}
