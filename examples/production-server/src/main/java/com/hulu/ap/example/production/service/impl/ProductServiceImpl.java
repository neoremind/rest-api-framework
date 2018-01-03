package com.hulu.ap.example.production.service.impl;

import com.google.inject.Inject;
import com.hulu.ap.example.production.dao.ProductDao;
import com.hulu.ap.example.production.service.ProductService;
import com.hulu.ap.example.production.vo.Product;

/**
 * Created by xu.zhang on 12/21/16.
 */
public class ProductServiceImpl implements ProductService {

    @Inject
    private ProductDao productDao;

    @Override
    public Product getById(long productId) {
        return productDao.getById(productId);
    }
}
