package com.lt.dom.OctResp;

import com.lt.dom.otcenum.EnumPayChannel;
import com.lt.dom.otcenum.EnumPaymentOption;

import javax.persistence.*;

public class PaymentMethodResp {


    private String name;

    public static PaymentMethodResp of(EnumPayChannel e) {
        PaymentMethodResp resp = new PaymentMethodResp();

        resp.setName(e.name());
        return resp;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
