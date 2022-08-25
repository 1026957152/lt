package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumVoucherStatus {
    Created("Created"),
    Published("Published"),
    Issued("Issued"),
    Redeemed("Redeemed"),
    Available("Available"),
    Allocated("Allocated"),
    Unavailable("Unavailable"),

   // Available("Available"),
    Expired("Expired"),
  //  Reedeemed("Reedeemed"),

    ;


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumVoucherStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.voucher.status."
                + this.name());
        return displayStatusString;
    }

}
