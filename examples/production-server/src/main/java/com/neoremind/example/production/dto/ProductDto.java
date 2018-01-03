package com.neoremind.example.production.dto;

import com.neoremind.apiframework.commons.ResponseWithCodeAndMsg;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author xu.zhang
 */
@ApiModel(value = "ProductDto", description = "This is a product")
public class ProductDto extends ResponseWithCodeAndMsg {

    @ApiModelProperty(value = "product id")
    private long productId;

    @ApiModelProperty(value = "product name")
    private String productName;

    @ApiModelProperty(value = "price")
    private double price;

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
}
