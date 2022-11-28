package com.lt.dom.vo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class AgeRang_bigData {

    private String lable;
    private int price;
    private int from;
    private int to;

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

    public void setFrom(int from) {
        this.from = from;
    }

    public int getFrom() {
        return from;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getTo() {
        return to;
    }
}
