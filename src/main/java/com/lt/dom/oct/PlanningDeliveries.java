package com.lt.dom.oct;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.util.List;

//https://developer.here.com/documentation/tour-planning/3.9.0/dev_guide/topics/use-cases/pickup-delivery.html
@Entity
public class PlanningDeliveries {
    @Version
    private Integer version;
    @Id
    private long id;
    @Transient
    private List<String> places;
    private long demand;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}
