package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnumResp {





    private String id;
    private String name;
    private String text;
    private Boolean selected;
    private List<EnumResp> subitems
            ;

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

    public void setSubitems(List<EnumResp> subitems) {
        this.subitems = subitems;
    }

    public List<EnumResp> getSubitems() {
        return subitems;
    }
}
