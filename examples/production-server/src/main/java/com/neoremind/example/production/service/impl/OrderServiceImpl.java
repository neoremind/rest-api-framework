package com.neoremind.example.production.service.impl;

import com.google.inject.Inject;
import com.neoremind.example.production.dao.OrderDao;
import com.neoremind.example.production.exception.NotFoundException;
import com.neoremind.example.production.exception.OrderFailException;
import com.neoremind.example.production.misc.ObjectCache;
import com.neoremind.example.production.service.InventoryService;
import com.neoremind.example.production.service.OrderService;
import com.neoremind.example.production.vo.Order;

import java.util.List;
import java.util.Map;

/**
 * @author xu.zhang
 */
public class OrderServiceImpl implements OrderService {

    @Inject
    private OrderDao orderDao;

    @Inject
    private InventoryService inventoryService;

    @Override
    public Order getById(int orderId) throws NotFoundException {
        Order order = orderDao.getById(orderId);
        if (order == null) {
            throw new NotFoundException("Order not exist");
        }
        return order;
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    /**
     * Create order.
     * <p>
     * <ul>
     * <li>Step1. Check inventory is enough or not, if not throwing OrderFailException</li>
     * <li>Step2. Create order</li>
     * <li>Step3. Decrease inventory number</li>
     * </ul>
     *
     * @param order
     * @return
     * @throws OrderFailException
     */
    @Override
    public Order create(Order order) throws OrderFailException {
        Map<Long, Integer> productId2AvailNum = inventoryService.getAvailableProductNumbers(order.getProductIdList());
        if (productId2AvailNum.entrySet().stream().anyMatch(e -> e.getValue() < 1)) {
            throw new OrderFailException("Inventory not enough");
        }
        int orderId = ObjectCache.GLOBAL_ID.incrementAndGet();
        order.setOrderId(orderId);
        Order o = orderDao.create(order);
        inventoryService.decreaseProductNumbers(order.getProductIdList());
        return o;
    }
}
