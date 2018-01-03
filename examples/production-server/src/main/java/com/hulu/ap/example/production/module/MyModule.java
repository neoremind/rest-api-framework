package com.hulu.ap.example.production.module;

import com.google.inject.Binder;
import com.hulu.ap.apiframework.guice.JettyModule;
import com.hulu.ap.example.production.api.OrderApi;
import com.hulu.ap.example.production.dao.InventoryDao;
import com.hulu.ap.example.production.dao.OrderDao;
import com.hulu.ap.example.production.dao.ProductDao;
import com.hulu.ap.example.production.dao.impl.InventoryDaoImpl;
import com.hulu.ap.example.production.dao.impl.OrderDaoImpl;
import com.hulu.ap.example.production.dao.impl.ProductDaoImpl;
import com.hulu.ap.example.production.service.InventoryService;
import com.hulu.ap.example.production.service.OrderService;
import com.hulu.ap.example.production.service.ProductService;
import com.hulu.ap.example.production.service.impl.InventoryServiceImpl;
import com.hulu.ap.example.production.service.impl.OrderServiceImpl;
import com.hulu.ap.example.production.service.impl.ProductServiceImpl;

import static com.google.inject.Scopes.SINGLETON;

/**
 * Guice module configuration
 *
 * @author xu.zhang
 */
public class MyModule implements JettyModule {

    @Override
    public void configure(Binder binder) {
        binder.bind(OrderApi.class).in(SINGLETON);
        binder.bind(ProductService.class).to(ProductServiceImpl.class).in(SINGLETON);
        binder.bind(OrderService.class).to(OrderServiceImpl.class).in(SINGLETON);
        binder.bind(InventoryService.class).to(InventoryServiceImpl.class).in(SINGLETON);
        binder.bind(OrderDao.class).to(OrderDaoImpl.class).in(SINGLETON);
        binder.bind(ProductDao.class).to(ProductDaoImpl.class).in(SINGLETON);
        binder.bind(InventoryDao.class).to(InventoryDaoImpl.class).in(SINGLETON);
    }

}
