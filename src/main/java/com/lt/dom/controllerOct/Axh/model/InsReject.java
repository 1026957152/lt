package com.lt.dom.controllerOct.Axh.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InsReject {

    @JsonProperty("orderId")
    private Integer orderId_申请id;  //申请id
    @JsonProperty("msg")
    private String msg;  //申请id

    public Integer getOrderId_申请id() {
        return orderId_申请id;
    }

    public void setOrderId_申请id(Integer orderId_申请id) {
        this.orderId_申请id = orderId_申请id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
