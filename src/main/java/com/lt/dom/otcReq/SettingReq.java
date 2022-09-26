package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumValueType;

import javax.validation.constraints.Size;
import java.util.List;

public class SettingReq {


    private String default_product_tax_code;

    private String default_shipping_tax_code;
    private String home_page_high_Quality_Product_recommendation;

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

    public String getHome_page_high_Quality_Product_recommendation() {

        return home_page_high_Quality_Product_recommendation;
    }

    public void setHome_page_high_Quality_Product_recommendation(String home_page_high_quality_product_recommendation) {
        this.home_page_high_Quality_Product_recommendation = home_page_high_quality_product_recommendation;
    }
}
