package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumTagType;

import javax.validation.constraints.NotNull;

public class TagReq {

    private String color;


    @NotNull
    private EnumTagType type;
    @NotNull
    private String text;
    private long supplier;

    private String name;
    private String icon;


    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public long getSupplier() {
        return supplier;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public EnumTagType getType() {
        return type;
    }

    public void setType(EnumTagType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
