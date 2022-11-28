package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
//https://apidocs.geoapify.com/docs/geocoding/forward-geocoding/#about
public enum EnumLocationType {
    country("/pages/ownercard/show"),
    state("pages/user/about"),
            city("pages/user/address/list"),
            postcode("pages/user/address/post"),
    street("pages/user/address/post"),
    amenity("pages/user/address/post"),
    locality("pages/user/address/post"),


    unknown("pages/user/address/post"),
    building("pages/user/address/post"),
    suburb("pages/user/address/post"),
    district("pages/user/address/post"),
    county("pages/user/address/post"),




    ;

    public static List<EnumResp> from() {
        return Arrays.asList(EnumLocationType.values()).stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());

            return enumResp;
        }).collect(Collectors.toList());
    }

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String path;

    @Autowired
    private MessageSource messageSource;

    EnumLocationType(String name) {

        this.path = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    /*
    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.pass.type."
                + this.name());
        return displayStatusString;
    }*/

}
