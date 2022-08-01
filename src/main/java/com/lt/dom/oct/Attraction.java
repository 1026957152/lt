package com.lt.dom.oct;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Attraction {

    @Id
    private long id;
    private long touristAttractionId;//
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private String CategoryCodes;
    private String Position;
}
