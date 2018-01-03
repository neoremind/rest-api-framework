package com.neoremind.example.production.api;

import com.baidu.unbiz.easymapper.MapperFactory;
import com.baidu.unbiz.fluentvalidator.*;
import com.baidu.unbiz.fluentvalidator.jsr303.HibernateSupportedValidator;
import com.baidu.unbiz.fluentvalidator.validator.element.ValidatorElementList;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.neoremind.example.production.common.Constants;
import com.neoremind.example.production.dto.OrderDto;
import com.neoremind.example.production.dto.OrderDtoList;
import com.neoremind.example.production.exception.NotFoundException;
import com.neoremind.example.production.exception.OrderFailException;
import com.neoremind.example.production.exception.ParamException;
import com.neoremind.example.production.resultcode.MyResultCode;
import com.neoremind.example.production.service.OrderService;
import com.neoremind.example.production.util.DateUtil;
import com.neoremind.example.production.vo.Order;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

import static com.baidu.unbiz.fluentvalidator.ResultCollectors.toSimple;

/**
 * Order API
 *
 * @author xu.zhang
 */
@Path("/order")
@Api(value = "order", description = "order operation")
@Produces(MediaType.APPLICATION_JSON)
public class OrderApi {

    /**
     * Injected order service bean
     */
    @Inject
    OrderService orderService;

    /**
     * Validate callback leveraging fluent-validator
     */
    ValidateCallback validateCallback = new DefaultValidateCallback() {
        @Override
        public void onFail(ValidatorElementList validatorElementList, List<ValidationError> list) {
            throw new ParamException(list.get(0).getErrorMsg());
        }

        @Override
        public void onUncaughtException(Validator validator, Exception e, Object o) throws Exception {
            throw e;
        }
    };

    /**
     * Get order by id
     *
     * @param orderId orderId
     * @return OrderDto
     */
    @GET
    @Path("/" + Constants.VERSION + "/{orderId}")
    @ApiOperation(value = "get by order id", response = OrderDto.class)
    public OrderDto queryByOrderId(
            @ApiParam(value = "order id") @DefaultValue("0") @PathParam("orderId") int orderId) {
        FluentValidator.checkAll()
                .on(orderId, new ValidatorHandler<Integer>() {
                    @Override
                    public boolean validate(ValidatorContext context, Integer orderId) {
                        if (orderId <= 0) {
                            context.addErrorMsg("Order Id is invalid");
                            return false;
                        }
                        return true;
                    }
                })
                .doValidate(validateCallback)
                .result(toSimple());

        OrderDto result = new OrderDto();
        try {
            Order order = orderService.getById(orderId);
            MapperFactory.getCopyByRefMapper().mapClass(Order.class, OrderDto.class)
                    .field("orderPlacedDate", "orderPlacedDate", (Date d) -> DateUtil.formatDate(d))
                    .field("productIdList", "productIds")
                    .register()
                    .map(order, result);
        } catch (NotFoundException e) {
            result.setCode(MyResultCode.ORDER_NOT_FOUND.getCode());
            result.setMsg(MyResultCode.ORDER_NOT_FOUND.getMessage().getMessage());
        }
        return result;
    }

    /**
     * Get all orders
     *
     * @return OrderDtoList
     */
    @GET
    @Path("/" + Constants.VERSION + "/getAllOrders")
    @ApiOperation(value = "get all orders", response = OrderDtoList.class)
    public OrderDtoList queryAllOrders() {
        List<Order> orderList = orderService.getAll();
        OrderDtoList result = new OrderDtoList();
        result.setOrderList(Lists.transform(orderList,
                o -> MapperFactory.getCopyByRefMapper().mapClass(Order.class, OrderDto.class)
                        .field("orderPlacedDate", "orderPlacedDate", (Date d) -> DateUtil.formatDate(d))
                        .field("productIdList", "productIds")
                        .register()
                        .map(o, OrderDto.class)));
        return result;
    }

    /**
     * Create/place new order
     *
     * @param order order
     * @return OrderDto
     */
    @POST
    @Path("/" + Constants.VERSION + "/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "place order", response = OrderDto.class)
    public OrderDto create(@ApiParam("Order in json format") OrderDto order) {
        FluentValidator.checkAll()
                .on(order, new HibernateSupportedValidator<OrderDto>().setHiberanteValidator(hValidator))
                .doValidate().result(toSimple());

        Order o = MapperFactory.getCopyByRefMapper().mapClass(OrderDto.class, Order.class)
                .field("orderPlacedDate", "orderPlacedDate", (String d) -> DateUtil.parseDate(d))
                .field("productIds", "productIdList")
                .register()
                .map(order, Order.class);

        order.setOrderId(o.getOrderId());
        OrderDto result = order;
        try {
            orderService.create(o);
        } catch (OrderFailException e) {
            result.setCode(MyResultCode.INVENTORY_NOT_ENOUGH.getCode());
            result.setMsg(MyResultCode.INVENTORY_NOT_ENOUGH.getMessage().getMessage());
        }
        return result;
    }

    /**
     * Hibernate validation properties
     */
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    javax.validation.Validator hValidator = factory.getValidator();

}
