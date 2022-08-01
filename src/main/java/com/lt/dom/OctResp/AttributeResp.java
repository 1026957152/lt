package com.lt.dom.OctResp;


import javax.persistence.Entity;
import javax.persistence.Id;


public class AttributeResp {



    private String name;
    private String description;

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
/*                "code": "digitalprojection",
                        "name": "Digital Projection",
                        "description": "This theatre features digital projection in all auditoriums."*/
}
