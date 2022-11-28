package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumObjectType {
    Vendor_groups("Vendor_groups"),
    High_Quality_Product_recommendation("High_Quality_Product_recommendation"),
    city_pass_right_recommendation("city_pass_right_recommendation"),
    home_page_attraction_recommendation("home_page_attraction_recommendation"),


    hero_pass("city_pass_inspired"),
    hero_pass_inspired("city_pass_inspired"),
    hero_pass_travelers_reviews("city_pass_travelers_reviews"),

    city_pass("city_pass_inspired"),


    mini_app_carousel("mini_app_carousel"),


    high_value_customer("high_value_customer"),

    ;


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumObjectType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.valuelist.type."
                + this.name());


        return displayStatusString;
    }
}
