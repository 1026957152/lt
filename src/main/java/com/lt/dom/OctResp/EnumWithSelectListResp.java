package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.otcenum.EnumSupplierType;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnumWithSelectListResp {





    private String id;
    private String name;
    private String text;
    private Boolean selected;
    private List subitems
            ;
    private EnumSupplierType type;
    private List<Long> selectedItems
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

    public void setSubitems(List subitems) {
        this.subitems = subitems;
    }

    public List getSubitems() {
        return subitems;
    }

    public void setType(EnumSupplierType type) {
        this.type = type;
    }

    public EnumSupplierType getType() {
        return type;
    }

    public void setSelectedItems(List<Long> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public List<Long> getSelectedItems() {
        return selectedItems;
    }
}
