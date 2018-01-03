package com.neoremind.example.production.dao.impl;

import com.neoremind.example.production.dao.ProductDao;
import com.neoremind.example.production.misc.ObjectCache;
import com.neoremind.example.production.vo.Product;

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
