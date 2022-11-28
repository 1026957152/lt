package com.lt.dom.OctResp;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Attribute;
import org.checkerframework.checker.units.qual.A;
import org.springframework.hateoas.EntityModel;

import javax.persistence.Entity;
import javax.persistence.Id;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttributeResp {


    private String code;

    private String name;
    private String description;
    private String before;
    private FeatureTagResp type;

    public static AttributeResp from(Attribute e) {

        AttributeResp attributeResp = new AttributeResp();
        attributeResp.setDescription(e.getDescription());
        attributeResp.setName(e.getName());
        FeatureTagResp featureTagResp = FeatureTagResp.from(e.getFeatureType());
        attributeResp.setType(featureTagResp);

        return attributeResp;
    }

    public static AttributeResp fromWithOutFeature(Attribute e) {

        AttributeResp attributeResp = new AttributeResp();
        attributeResp.setDescription(e.getDescription());
        attributeResp.setName(e.getName());
      //  FeatureTagResp featureTagResp = FeatureTagResp.from(e.getFeatureType());
       // attributeResp.setType(featureTagResp);

        return attributeResp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getBefore() {
        return before;
    }

    public void setType(FeatureTagResp type) {
        this.type = type;
    }

    public FeatureTagResp getType() {
        return type;
    }
/*                "code": "digitalprojection",
                        "name": "Digital Projection",
                        "description": "This theatre features digital projection in all auditoriums."*/
}
