package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumQuotaClaimOrRedeem {
    claim("claim"),
    redeem("redeem"),

    ;

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumQuotaClaimOrRedeem(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.quota.activity."
                + this.name());
        return displayStatusString;
    }
}
