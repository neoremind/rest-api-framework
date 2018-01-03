package com.neoremind.example.production.service;

import com.neoremind.example.production.vo.Product;

/**
 * Created by xu.zhang on 12/21/16.
 */
public interface ProductService {

    Product getById(long productId);

}
