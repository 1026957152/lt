package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumCardStatus {
    active("physical"),
    inactive("virtual"),
    canceled("virtual"),

/*    active
    The card can approve authorizations.

            inactive
    The card will decline authorizations with the card_inactive reason.

            canceled
    The card will decline authorization, and no authorization object will be recorded. This status is permanent.
   */

    ;



    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumCardStatus(String name) {

        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.pass.status."
                + this.name());
        return displayStatusString;
    }

}
