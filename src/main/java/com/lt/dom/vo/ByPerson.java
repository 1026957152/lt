package com.lt.dom.vo;

import com.lt.dom.otcenum.EnumProductPricingTypeByPersonGroupType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ByPerson {

    private EnumProductPricingTypeByPersonGroupType groupType;

    public EnumProductPricingTypeByPersonGroupType getGroupType() {
        return groupType;
    }

    public void setGroupType(EnumProductPricingTypeByPersonGroupType groupType) {
        this.groupType = groupType;
    }

    private int price;
    private int original;
    private int retail;
    private int net;

    public void setPrice(int price) {
        this.price = price;
    }

    public int getOriginal() {
        return original;
    }

    public void setOriginal(int original) {
        this.original = original;
    }

    public int getRetail() {
        return retail;
    }

    public void setRetail(int retail) {
        this.retail = retail;
    }

    public int getNet() {
        return net;
    }

    public void setNet(int net) {
        this.net = net;
    }

    private Integer max;
    private Integer min;


    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }
}
