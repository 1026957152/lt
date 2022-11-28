package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.otcenum.EnumFeatureTag;

import javax.validation.constraints.NotNull;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FeatureTagResp {


    private String icon;

    @NotNull
    private String text;
    private EnumFeatureTag type;

    public static FeatureTagResp from(EnumFeatureTag featureType) {
        FeatureTagResp featureTagResp = new FeatureTagResp();
        featureTagResp.setIcon(featureType.getIcon());
        featureTagResp.setText(featureType.getText());
        return featureTagResp;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(EnumFeatureTag type) {
        this.type = type;
    }

    public EnumFeatureTag getType() {
        return type;
    }
}
