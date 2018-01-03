package com.neoremind.example.production.vo;

import com.google.common.base.MoreObjects;

/**
 * @author xu.zhang
 */
public class Product {

    private long productId;

    private String productName;

    private double price;

    private String category;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("productId", productId).add("productName", productName)
                .add("price", price).add("category", category).toString();
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
