package com.lt.dom.OctResp;

import com.lt.dom.oct.Base;
import com.lt.dom.otcenum.EnumProductOptionType;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;


public class ProductOptionResp {

    private String display_name;



    private EnumProductOptionType type;

    @Transient
    private List option_values;
    private String key;
    private String tip;


    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }


    public EnumProductOptionType getType() {
        return type;
    }

    public void setType(EnumProductOptionType type) {
        this.type = type;
    }

    public <R> void setOption_values(List option_values) {
        this.option_values = option_values;
    }

    public List getOption_values() {
        return option_values;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getTip() {
        return tip;
    }
}
