package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumProductStatus {
    active("active"),
    archived("archived"),
    draft("draft"),


    ;


  //  active: The product is ready to sell and is available to customers on the online store, sales channels, and apps. By default, existing products are set to active.
   // archived: The product is no longer being sold and isn't available to customers on sales channels and apps.
   // draft: T


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumProductStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.product.status."
                + this.name());
        return displayStatusString;
    }

}
