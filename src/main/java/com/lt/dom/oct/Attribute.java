package com.lt.dom.oct;


import com.lt.dom.otcenum.EnumFeatureTag;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
public class Attribute extends Base{


    
//##@Column(unique=true)
private String code;
    private String name;

    @Length(max = 5000)
    private String description;
    private long objectId;
    private String objectCode;

    @Enumerated(EnumType.STRING)
    private EnumFeatureTag featureType;


    @Column(name = "key_")
    private String key;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }

    public String getObjectCode() {
        return objectCode;
    }

    public void setFeatureType(EnumFeatureTag featureType) {
        this.featureType = featureType;
    }

    public EnumFeatureTag getFeatureType() {
        return featureType;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
/*                "code": "digitalprojection",
                        "name": "Digital Projection",
                        "description": "This theatre features digital projection in all auditoriums."*/
}
