package com.lt.dom.vo;


import com.lt.dom.oct.User;
import com.lt.dom.otcenum.EnumPlatform;

import javax.validation.constraints.NotEmpty;


public class PlatRefundVo {


    private String orders_id;
    private String serial_no;
    private EnumPlatform platform;


    public void setOrders_id(String orders_id) {
        this.orders_id = orders_id;
    }

    public String getOrders_id() {
        return orders_id;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setPlatform(EnumPlatform platform) {
        this.platform = platform;
    }

    public EnumPlatform getPlatform() {
        return platform;
    }
}
