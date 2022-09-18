package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumPassShippingStatus {
    shipped("shipped"),
    delivered("delivered"),
    returned("returned"),
    failure("failure"),
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

    EnumPassShippingStatus(String name) {

        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.asset.type."
                + this.name());
        return displayStatusString;
    }

}
