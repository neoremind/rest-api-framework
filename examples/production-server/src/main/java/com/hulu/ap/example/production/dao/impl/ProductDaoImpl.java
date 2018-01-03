package com.hulu.ap.example.production.dao.impl;

import com.hulu.ap.example.production.dao.ProductDao;
import com.hulu.ap.example.production.misc.ObjectCache;
import com.hulu.ap.example.production.vo.Product;

import java.util.Optional;

/**
 * @author xu.zhang
 */
public class ProductDaoImpl implements ProductDao {

    @Override
    public Product getById(long productId) {
        Optional<Product> product = ObjectCache.PRODUCT_LIST.stream().filter(p -> p.getProductId() == productId).findFirst();
        if (product.isPresent()) {
            return product.get();
        } else {
            return null;
        }
    }
}
