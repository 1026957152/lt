package com.lt.dom;


import com.lt.dom.oct.Base;

import javax.persistence.Entity;


//https://metmuseum.github.io/
@Entity
public class CollectionAchive extends Base {


    private String name;
    private String description;
    private Integer  numObjects;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumObjects() {
        return numObjects;
    }

    public void setNumObjects(Integer numObjects) {
        this.numObjects = numObjects;
    }
}
