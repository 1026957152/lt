package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumAvailableActionType {
    Campaigns("Campaigns","Read Campaigns."),
    Vouchers("Vouchers","Read Vouchers."),
    Distributions("Distributions","Read and export Distributions and Publications."),
    Products("Products","Read Products"),
    Customers("Customers","Read Customers"),
    Redemptions("Redemptions","Read Customers");








    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;
    public String type;
    @Autowired
    private MessageSource messageSource;

    EnumAvailableActionType(String ob, String name) {

        this.type = ob;
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.role.action.type."
                + this.name());
        return displayStatusString;
    }




}
