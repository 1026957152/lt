package com.lt.dom.OctResp;

import com.lt.dom.otcenum.EnumPayChannel;

public class PaymentMethodResp {


    private String id;
    private String text;
    public static PaymentMethodResp of(EnumPayChannel e) {
        PaymentMethodResp resp = new PaymentMethodResp();
        resp.setText(e.toString());
        resp.setId(e.name());
        return resp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
