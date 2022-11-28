package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumAssetQtStatus;
import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumAssetType_dynamic_static;
import com.lt.dom.otcenum.EnumTagType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Tag  extends Base{

    private String color;

    private EnumTagType type;

    private String value;
    private long supplier;
    private String code;
    private String name;
   // private String key;
    private String icon;
    private String text;



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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }
}
