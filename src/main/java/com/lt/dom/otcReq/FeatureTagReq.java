package com.lt.dom.otcReq;

import com.lt.dom.otcenum.EnumFeatureTag;
import com.lt.dom.otcenum.EnumTagType;

import javax.validation.constraints.NotNull;

public class FeatureTagReq {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;
    @NotNull
    private EnumFeatureTag type;
    @NotNull
    private String text;
    private String name;

    public EnumFeatureTag getType() {
        return type;
    }

    public void setType(EnumFeatureTag type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
