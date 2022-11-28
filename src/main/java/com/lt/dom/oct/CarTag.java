package com.lt.dom.oct;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lt.dom.otcenum.EnumCarTag;

import javax.persistence.*;


//https://metmuseum.github.io/
@Entity

public class CarTag extends Base {

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="car", nullable=false)
    private Car car;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Enumerated(EnumType.STRING)
    private EnumCarTag type;
    private String text;

    public EnumCarTag getType() {
        return type;
    }

    public void setType(EnumCarTag feature) {
        this.type = feature;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

//https://allmyles.docs.apiary.io/#reference/rental-cars/rental-cars-collection/search-cars?console=1