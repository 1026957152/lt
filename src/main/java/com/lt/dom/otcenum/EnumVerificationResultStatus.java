package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumVerificationResultStatus {
    verified("Retail travel agent"),
    unverified("Trusted travel agent"),



    ;
/*/requires_input
    Requires user input before processing can continue.

    processing
    The session has been submitted and is being processed. Most verification checks are processed in less than 1 minute.

            verified
    Processing of all the verification checks are complete and successfully verified.

            canceled
    The VerificationSession has been invalidated for future submission attempts.*/
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumVerificationResultStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.supplier.status."
                + this.name());
        return displayStatusString;
    }
}
