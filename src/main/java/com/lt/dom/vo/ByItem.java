package com.lt.dom.vo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ByItem {

    private String lable;
    private int price;

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
