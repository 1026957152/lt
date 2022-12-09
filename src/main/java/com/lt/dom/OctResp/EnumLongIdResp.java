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
    private String composite_text;
    private String composite_id;


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

    public void setComposite_text(String composite_text) {
        this.composite_text = composite_text;
    }

    public String getComposite_text() {
        return composite_text;
    }

    public void setComposite_id(String composite_id) {
        this.composite_id = composite_id;
    }

    public String getComposite_id() {
        return composite_id;
    }
}
