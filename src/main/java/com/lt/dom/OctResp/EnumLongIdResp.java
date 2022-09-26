package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(collectionRelation = "items", itemRelation = "item")

public class EnumLongIdResp {





    private long id;
    private String text;
    private Boolean selected;
    private Object info;
    private List subitems;



    public void setSubitems(List subitems) {
        this.subitems = subitems;
    }

    public List getSubitems() {
        return subitems;
    }


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
