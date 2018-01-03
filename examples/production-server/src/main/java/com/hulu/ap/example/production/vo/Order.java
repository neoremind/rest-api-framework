package com.hulu.ap.example.production.vo;

import com.google.common.base.MoreObjects;

import java.util.Date;
import java.util.List;

/**
 * @author xu.zhang
 */
public class Order {

    private int orderId;

    private List<Long> productIdList;

    private String createdBy;

    private Date orderPlacedDate;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("orderId", orderId).add("productIdList", productIdList)
                .add("createdBy", createdBy).add("orderPlacedDate", orderPlacedDate).toString();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<Long> getProductIdList() {
        return productIdList;
    }

    public void setProductIdList(List<Long> productIdList) {
        this.productIdList = productIdList;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getOrderPlacedDate() {
        return orderPlacedDate;
    }

    public void setOrderPlacedDate(Date orderPlacedDate) {
        this.orderPlacedDate = orderPlacedDate;
    }
}
