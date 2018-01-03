package com.neoremind.example.production.dao;


import com.neoremind.example.production.vo.Product;

/**
 * @author xu.zhang
 */
public interface ProductDao {

    Product getById(long productId);

}
