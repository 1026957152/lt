package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.otcenum.EnumFeatureTag;

import javax.validation.constraints.NotNull;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttributeEditReq {
    private String key;

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

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
