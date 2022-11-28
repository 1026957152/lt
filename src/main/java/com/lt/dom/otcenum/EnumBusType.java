package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumBusType {
    Bus("company"),


    ;






    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumBusType(String name) {
        this.name = name;
    }
/*
    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.customer.bussiness.type."
                + this.name());
        return displayStatusString;
    }*/

}
