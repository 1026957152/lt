package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumRequestStatus {
    APPROVE("barcode"),
    REJECT("reference number"),
   // ABSTAIN("Barcode scan"),
   // RESET("Facial Recognition"),



    Pending("pending"),


/*
    APPROVE, REJECT, ABSTAIN, RESET*/
    ;




    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumRequestStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.request.status."
                + this.name());
        return displayStatusString;
    }


}
