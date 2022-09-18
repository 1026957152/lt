package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumQuotaType {
    NominatedSupplier("nominated supplier","指定供应商"),
    Scenario("Scenario","应用场景"),
    all_customer("all_customer","所有的职工"),
    all_supplier("all_supplier","所有的企业"),
    supplier_groups("supplier_groups","所有的企业"),



    ;


    EnumQuotaType(String name, String 应用场景) {

    }

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumQuotaType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.quota.type."
                + this.name());
        return displayStatusString;
    }

}
