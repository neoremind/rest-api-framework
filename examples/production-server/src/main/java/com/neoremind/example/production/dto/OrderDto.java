package com.neoremind.example.production.dto;

import com.neoremind.apiframework.commons.ResponseWithCodeAndMsg;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @author xu.zhang
 */
@ApiModel(value = "Order", description = "This is a order")
public class OrderDto extends ResponseWithCodeAndMsg {

    @ApiModelProperty(value = "order id")
    private int orderId;

    @ApiModelProperty(value = "product id list")
    @NotEmpty(message = "product ids should not be empty")
    private List<Long> productIds;

    @ApiModelProperty(value = "user who placed the order")
    private String createdBy;

    @ApiModelProperty(value = "order placed date")
    private String orderPlacedDate;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getOrderPlacedDate() {
        return orderPlacedDate;
    }

    public void setOrderPlacedDate(String orderPlacedDate) {
        this.orderPlacedDate = orderPlacedDate;
    }
}
