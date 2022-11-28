package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumKnownfor {


    Charming("NFC tap"),
    Foodie("NFC tap"),
            Nightlife("NFC tap"),
    Architecture("NFC tap"),
            History("NFC tap"),
    Museums("NFC tap"),
    Performing_Arts("Performing Arts"),
    Music("Music"),
            Hipster("Hipster"),
    Hippie("Hippie"),
            Posh("Posh"),
    Family_Friendly("Family Friendly"),
    LGBT_Friendly("LGBT Friendly"),
    Diversity("Diversity"),
    Beach_Town("Beach Town"),
    College_Town("College Town"),
    Ski_Town("Ski Town"),
    Outdoorsy("Outdoorsy"),
            Wineries("Wineries"),
    Shopping("Shopping"),

    ;

    EnumKnownfor(String barcode) {

    }
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

   @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.tag.knowfor."
                + this.name());
        return displayStatusString;
    }
}
