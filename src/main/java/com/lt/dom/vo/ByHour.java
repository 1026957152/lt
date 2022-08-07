package com.lt.dom.vo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ByHour {
    private int min;
    private int max;
    private String unit; // per or total
    private int price;
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
