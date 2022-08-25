package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumBookingOjbectType {

    Voucher("为个人用户"),
    Product("为企业用户"),


    ;


            ;

    EnumBookingOjbectType(String scenario, String 应用场景) {

    }


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumBookingOjbectType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.broadcastmail.status."
                + this.name);
        return displayStatusString;
    }


}
