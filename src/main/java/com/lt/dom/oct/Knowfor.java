package com.lt.dom.oct;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.lt.dom.otcenum.EnumCarFeatureTag;
import com.lt.dom.otcenum.EnumKnownfor;

import javax.persistence.*;


//https://metmuseum.github.io/
@Entity

public class Knowfor extends Base {

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="attraction", nullable=false)
    private Attraction attraction;

    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }

    @Enumerated(EnumType.STRING)
    private EnumKnownfor knownfor;

    private String text;

    public EnumKnownfor getKnownfor() {
        return knownfor;
    }

    public void setKnownfor(EnumKnownfor knownfor) {
        this.knownfor = knownfor;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
