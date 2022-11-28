package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumRestock_type {
    no_restock("Created"),
    cancel("Ordered"),

    return_("Printed"),

/*    no_restock: Refunding these items won't affect inventory.
    cancel: The items have not yet been fulfilled. The canceled quantity will be added back to the available count. The number of fulfillable units for this line item will decrease.
            return:*/
    ;
    public String name;

    EnumRestock_type(String name) {

        this.name = name;
    }


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());


    @Autowired
    private MessageSource messageSource;


    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.pass.fulfillment.status."
                + this.name());
        return displayStatusString;
    }

}
