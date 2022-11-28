package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.oct.Base;

import javax.persistence.Entity;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductOptionValueResp {




    private String name;


    @JsonProperty("is_default")
    private Boolean isDefault;
    @JsonProperty("label")
    private String label;
    @JsonProperty("sort_order")
    private Integer sortOrder;
    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }





    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
