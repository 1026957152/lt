package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumInvitateRequestType {
    Invitation("partner"),
    Request("employee"),
    ;


    EnumInvitateRequestType(String name) {
        this.name = name;

    }


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;


    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.invitation.type."
                + this.name());
        return displayStatusString;
    }
}
