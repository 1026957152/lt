package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumDuration {
    once("once"),
    repeating("repeating"),
    forever("forever"),



    ;

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String value;

    @Autowired
    private MessageSource messageSource;

    EnumDuration(String name) {
        this.value = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.componentright.duration."
                + this.name());
        return displayStatusString;
    }
}
