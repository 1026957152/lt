package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumValueListType {
    Vendor_groups("Vendor_groups"),
    High_Quality_Product_recommendation("High_Quality_Product_recommendation"),
    city_pass_right_recommendation("city_pass_right_recommendation"),
    home_page_attraction_recommendation("home_page_attraction_recommendation"),


    ;


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumValueListType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.valuelist.type."
                + this.name());
        return displayStatusString;
    }
}
