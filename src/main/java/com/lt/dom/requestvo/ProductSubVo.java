package com.lt.dom.requestvo;

import com.lt.dom.oct.*;
import com.lt.dom.otcenum.EnumProductType;
import com.lt.dom.otcenum.EnumPublicationObjectType;

import javax.persistence.Transient;

public class ProductSubVo {


    private EnumProductType type;
    private Tour tour;
    private Attraction attraction;

    private ProductTheatre theatre;
    private  Voucher voucher;

    private long toWho;

    public long getToWho() {
        return toWho;
    }

    public void setToWho(long toWho) {
        this.toWho = toWho;
    }

    public EnumProductType getType() {
        return type;
    }

    public void setType(EnumProductType type) {
        this.type = type;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }
}
