package com.lt.dom.otcReq;


import com.lt.dom.oct.CarFeature;
import com.lt.dom.otcenum.EnumCarFeatureTag;
import com.lt.dom.otcenum.EnumCarTag;


//https://metmuseum.github.io/


public class CarFeatureReq {

    public EnumCarTag getTag() {
        return tag;
    }

    public void setTag(EnumCarTag tag) {
        this.tag = tag;
    }

    private EnumCarTag tag;

    private EnumCarFeatureTag feature;
    private String text;

    public static CarFeatureReq from(CarFeature e) {
        CarFeatureReq carFeatureResp = new CarFeatureReq();
        carFeatureResp.setFeature(e.getType());
        carFeatureResp.setText(e.getText());
        return carFeatureResp;
    }

    public EnumCarFeatureTag getFeature() {
        return feature;
    }

    public void setFeature(EnumCarFeatureTag feature) {
        this.feature = feature;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

//https://allmyles.docs.apiary.io/#reference/rental-cars/rental-cars-collection/search-cars?console=1