package com.hulu.ap.example.production.dao;


import com.hulu.ap.example.production.vo.Product;

/**
 * @author xu.zhang
 */
public interface ProductDao {

    Product getById(long productId);

}
