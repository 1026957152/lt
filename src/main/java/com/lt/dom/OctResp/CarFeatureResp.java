package com.lt.dom.OctResp;


import com.lt.dom.oct.CarFeature;
import com.lt.dom.otcenum.EnumCarFeatureTag;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;


//https://metmuseum.github.io/


public class CarFeatureResp{



    @Enumerated(EnumType.STRING)
    private EnumCarFeatureTag feature;
    private String text;
    private String feature_text;
    private String icon;

    public static CarFeatureResp from(CarFeature e) {
        CarFeatureResp carFeatureResp = new CarFeatureResp();
        carFeatureResp.setFeature(e.getType());
        carFeatureResp.setFeature_text(e.getType().getDisplay_name());

        carFeatureResp.setIcon(e.getType().getFeature_image());
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

    public void setFeature_text(String feature_text) {
        this.feature_text = feature_text;
    }

    public String getFeature_text() {
        return feature_text;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }
}

//https://allmyles.docs.apiary.io/#reference/rental-cars/rental-cars-collection/search-cars?console=1