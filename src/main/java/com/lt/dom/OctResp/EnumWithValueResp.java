package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnumWithValueResp {





    private String id;
    private String name;
    private String text;
    private Boolean selected;
    private List<EnumWithValueResp> subitems
            ;
    private String value;
    private String type;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSubitems(List<EnumWithValueResp> subitems) {
        this.subitems = subitems;
    }

    public List<EnumWithValueResp> getSubitems() {
        return subitems;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
