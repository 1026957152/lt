package com.lt.dom.vo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ByPerson {
    private int price;

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
