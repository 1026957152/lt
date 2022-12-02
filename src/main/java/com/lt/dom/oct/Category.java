package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumCatetory;

import javax.persistence.*;

@Entity
public class Category extends Base {


    @Enumerated(EnumType.STRING)
    private EnumCatetory category;
    
//##@Column(unique=true) 
private String code;
    private String name;
    private Long parent_category_id;


    public EnumCatetory getCategory() {
        return category;
    }

    public void setCategory(EnumCatetory category) {
        this.category = category;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParent_category_id() {
        return parent_category_id;
    }

    public void setParent_category_id(Long parent_category_id) {
        this.parent_category_id = parent_category_id;
    }
}
