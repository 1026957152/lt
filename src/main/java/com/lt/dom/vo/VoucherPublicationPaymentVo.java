package com.lt.dom.vo;

import com.lt.dom.oct.Charge;

public class VoucherPublicationPaymentVo {


    private String default_product_tax_code;

    private String default_shipping_tax_code;
    private boolean free;
    private Charge charge;
    private boolean paied;

    public String getDefault_product_tax_code() {
        return default_product_tax_code;
    }

    public void setDefault_product_tax_code(String default_product_tax_code) {
        this.default_product_tax_code = default_product_tax_code;
    }

    public String getDefault_shipping_tax_code() {
        return default_shipping_tax_code;
    }

    public void setDefault_shipping_tax_code(String default_shipping_tax_code) {
        this.default_shipping_tax_code = default_shipping_tax_code;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public boolean getFree() {
        return free;
    }

    public void setCharge(Charge charge) {
        this.charge = charge;
    }

    public Charge getCharge() {
        return charge;
    }

    public void setPaied(boolean paied) {
        this.paied = paied;
    }

    public boolean getPaied() {
        return paied;
    }
}
