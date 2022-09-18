package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumSplitBearerType {
    all("all"),
    all_proportional("all-proportional"),
    account("all-account"),
    subaccount("subaccount")
    ;



    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumSplitBearerType(String name) {

        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.asset.type."
                + this.name());
        return displayStatusString;
    }

}
