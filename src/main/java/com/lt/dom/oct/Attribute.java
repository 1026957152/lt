package com.lt.dom.oct;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class Attribute {
    @Version
    private Integer version;
    @Id
    private long id;


    
//##@Column(unique=true)
private String code;
    private String name;
    private String description;
    private long objectId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
/*                "code": "digitalprojection",
                        "name": "Digital Projection",
                        "description": "This theatre features digital projection in all auditoriums."*/
}
