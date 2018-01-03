package com.hulu.ap.example.production.dto;

import com.hulu.ap.apiframework.commons.ResponseWithCodeAndMsg;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author xu.zhang
 */
@ApiModel(value = "OrderDtoList", description = "This is a order list")
public class OrderDtoList extends ResponseWithCodeAndMsg {

    @ApiModelProperty(value = "order list")
    List<OrderDto> orderList;

    public List<OrderDto> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderDto> orderList) {
        this.orderList = orderList;
    }
}
