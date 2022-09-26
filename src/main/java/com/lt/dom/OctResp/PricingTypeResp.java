package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.PricingType;
import com.lt.dom.otcenum.EnumProductPricingType;
import com.lt.dom.otcenum.EnumProductPricingTypeByPerson;

import javax.persistence.*;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class PricingTypeResp {

    private Long productId;

    private EnumProductPricingType type;
    private EnumProductPricingTypeByPerson by;
    private Integer min;
    private Integer max;
    private String unit;
    private Integer price;
    private String lable;
    private String type_Text;
    private String by_text;
    private int original;
    private int retail;
    private int net;
    private String name;

/*
    public static PricingTypeResp from(PricingType e) {
        PricingTypeResp pricingTypeResp = new PricingTypeResp();



        pricingTypeResp.setType(e.getType());
        pricingTypeResp.setType_Text(e.getType().toString());
        pricingTypeResp.setLable(e.getLable());


        pricingTypeResp.setOriginal(e.getPrice());
        pricingTypeResp.setRetail(e.getPrice());
        pricingTypeResp.setNet(e.getPrice());

        if(e.getType().equals(EnumProductPricingType.ByPerson)){
            pricingTypeResp.setPrice(e.getPrice());
            pricingTypeResp.setBy(e.getBy());
            pricingTypeResp.setBy_text(e.getBy().toString());
        }
        if(e.getType().equals(EnumProductPricingType.ByItem)){
            pricingTypeResp.setUnit(e.getUnit());
        }
        if(e.getType().equals(EnumProductPricingType.Fixed)){
            pricingTypeResp.setPrice(e.getPrice());
        }

        if(e.getType().equals(EnumProductPricingType.ByDay) || e.getType().equals(EnumProductPricingType.ByHour) || e.getType().equals(EnumProductPricingType.ByMinute)  ){
            pricingTypeResp.setMin(e.getMin());
            pricingTypeResp.setMax(e.getMax());
        }
        pricingTypeResp.setName(pricingTypeResp.getBy_text());

        return pricingTypeResp;
    }*/


    public static PricingTypeResp from(PricingType e) {
        PricingTypeResp pricingTypeResp = new PricingTypeResp();



        pricingTypeResp.setType(e.getType());
        pricingTypeResp.setType(e.getType());
        pricingTypeResp.setType_Text(e.getType().toString());
        pricingTypeResp.setLable(e.getLable());
        pricingTypeResp.setOriginal(e.getPrice());
        pricingTypeResp.setRetail(e.getPrice());
        pricingTypeResp.setNet(e.getPrice());


        if(e.getType().equals(EnumProductPricingType.ByPerson)){
            pricingTypeResp.setPrice(e.getPrice());
            pricingTypeResp.setBy(e.getBy());
            pricingTypeResp.setBy_text(e.getBy().toString());
        }
        if(e.getType().equals(EnumProductPricingType.ByItem)){
            pricingTypeResp.setUnit(e.getUnit());
        }
        if(e.getType().equals(EnumProductPricingType.Fixed)){
            pricingTypeResp.setPrice(e.getPrice());
        }

        if(e.getType().equals(EnumProductPricingType.ByDay) || e.getType().equals(EnumProductPricingType.ByHour) || e.getType().equals(EnumProductPricingType.ByMinute)  ){
            pricingTypeResp.setMin(e.getMin());
            pricingTypeResp.setMax(e.getMax());
        }
        pricingTypeResp.setName(e.getNick_name());
       // pricingTypeResp.setName(pricingTypeResp.getBy_text());
        return pricingTypeResp;
    }


    public EnumProductPricingType getType() {
        return type;
    }

    public void setType(EnumProductPricingType type) {
        this.type = type;
    }

    public EnumProductPricingTypeByPerson getBy() {
        return by;
    }

    public void setBy(EnumProductPricingTypeByPerson by) {
        this.by = by;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setType_Text(String type_text) {
        this.type_Text = type_text;
    }

    public String getType_Text() {
        return type_Text;
    }

    public void setBy_text(String by_text) {
        this.by_text = by_text;
    }

    public String getBy_text() {
        return by_text;
    }

    public void setOriginal(int original) {
        this.original = original;
    }

    public int getOriginal() {
        return original;
    }

    public void setRetail(int retail) {
        this.retail = retail;
    }

    public int getRetail() {
        return retail;
    }

    public void setNet(int net) {
        this.net = net;
    }

    public int getNet() {
        return net;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
