package com.hulu.ap.example.production.resultcode;

import net.neoremind.resultutil.result.ResultCode;
import net.neoremind.resultutil.result.ResultCodeMessage;
import net.neoremind.resultutil.result.ResultCodeUtil;

/**
 * MyResultCode
 *
 * @author zhangxu
 */
public enum MyResultCode implements ResultCode {

    /**
     * Param is invalid
     */
    PARAM_INVALID,

    /**
     * Order not found
     */
    ORDER_NOT_FOUND,

    /**
     * Inventory not enough
     */
    INVENTORY_NOT_ENOUGH;

    private final ResultCodeUtil util = new ResultCodeUtil(this);

    public String getName() {
        return util.getName();
    }

    public ResultCodeMessage getMessage() {
        return util.getMessage();
    }

    public ResultCodeMessage getMessage(String args) {
        return util.getMessage(args);
    }

    public int getCode() {
        return util.getCode();
    }

}
