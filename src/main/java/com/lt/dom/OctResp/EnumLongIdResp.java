package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnumLongIdResp {





    private long id;
    private String text;
    private Boolean selected;
    private Object info;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public Object getInfo() {
        return info;
    }
}
