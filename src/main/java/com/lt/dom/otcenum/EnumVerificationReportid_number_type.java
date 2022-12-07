package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumVerificationReportid_number_type {
    br_cpf("Retail travel agent"),
    sg_nric("Trusted travel agent"),
    us_ssn("Trusted travel agent"),


    ;

/*    br_cpf
    An individual CPF number from Brazil.

    sg_nric
    A national registration identity card number from Singapore.

    us_ssn*/
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumVerificationReportid_number_type(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.supplier.status."
                + this.name());
        return displayStatusString;
    }
}
