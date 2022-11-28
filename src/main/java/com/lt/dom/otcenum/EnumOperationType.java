package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumOperationType {
    show_clear_card_details("show_clear_card_details"),
    get_pin("get_pin"),
    activate_card("activate_card"),
    change_pin("change_pin")



    ;



    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumOperationType(String name) {

        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.pass.status."
                + this.name());
        return displayStatusString;
    }

}
