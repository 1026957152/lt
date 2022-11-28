package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumBelongType {
    ComponentVounch("First time activation: when activated from INACTIVE to ACTIVE status."),
    Pass("First time activation: when activated from INACTIVE to ACTIVE status."),



    ;



    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumBelongType(String name) {

        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.pass.status."
                + this.name());
        return displayStatusString;
    }

}
