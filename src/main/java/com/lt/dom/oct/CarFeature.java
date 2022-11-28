package com.lt.dom.oct;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lt.dom.otcenum.EnumCarFeatureTag;

import javax.persistence.*;


//https://metmuseum.github.io/
@Entity

public class CarFeature extends Base {

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
    private EnumCarFeatureTag type;
    private String text;

    public EnumCarFeatureTag getType() {
        return type;
    }

    public void setType(EnumCarFeatureTag feature) {
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