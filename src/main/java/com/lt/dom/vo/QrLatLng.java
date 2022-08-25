package com.lt.dom.vo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class QrLatLng {
    private String c;
    private float a;
    private float n; // per or total

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public float getA() {
        return a;
    }

    public void setA(float a) {
        this.a = a;
    }

    public float getN() {
        return n;
    }

    public void setN(float n) {
        this.n = n;
    }
}
