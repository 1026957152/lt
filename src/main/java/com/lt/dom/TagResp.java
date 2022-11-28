package com.lt.dom;

import com.lt.dom.oct.CarTag;

import javax.validation.constraints.NotNull;

public class TagResp {

    private String color;


    @NotNull
    private Object type;
    @NotNull
    private String text;
    private long supplier;

    private String name;
    private String icon;

    public static TagResp from(CarTag e) {
        TagResp tagResp = new TagResp();
        tagResp.setText(e.getText());
        tagResp.setType(e.getType());

        return tagResp;
    }


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

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
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
