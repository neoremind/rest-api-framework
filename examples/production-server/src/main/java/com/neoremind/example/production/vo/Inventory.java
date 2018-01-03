package com.neoremind.example.production.vo;


import com.google.common.base.MoreObjects;

/**
 * @author xu.zhang
 */
public class Inventory {

    private long productId;

    private int availableNumber;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("productId", productId).add("availableNumber", availableNumber).toString();
    }


    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getAvailableNumber() {
        return availableNumber;
    }

    public void setAvailableNumber(int availableNumber) {
        this.availableNumber = availableNumber;
    }
}
