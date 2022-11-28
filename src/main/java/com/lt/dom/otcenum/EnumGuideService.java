package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

//https://www.travelchinaguide.com/faq/when/guide.htm
public enum EnumGuideService {
    Overseas_Escort("海外领队"),
    Full_accompanying_Tour_Guide("全程陪同导游"),
    Local_Tour_Guide("地方陪同"),
    Tour_Guides_in_Scenic_Areas("景区景点导游"),

    ;



    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumGuideService(String name) {

        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.makeplan.guideservice."
                + this.name());
        return displayStatusString;
    }
}
