package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumReasonCode {
    A0("First time activation: when activated from INACTIVE to ACTIVE status."),
    A1("Resumed from Suspended status: when card is moved back to ACTIVE status."),
    S0("Temporary suspension: initiated by the user or system user or the system."),

    S1("PIN retries exhausted: when bad PIN retries are exhausted."),
    T0("Card is lost."),

    T1("Card is stolen."),
    T2("Card is expired."),
    T3("Card is damaged."),
    T4("Card is closed by user."),
    T5("Card is closed by issuer."),


    ;



    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumReasonCode(String name) {

        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.pass.status."
                + this.name());
        return displayStatusString;
    }

}
