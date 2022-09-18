package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumSupplierType {


    A级景区("A级景区"),
    TravelAgent("旅行社"),
    乡村旅游示范村("乡村旅游示范村"),
    文化产业_十百千_工程("文化产业“十百千”工程"),
    剧院("剧院"),
    电影院("电影院"),


/*

    VisitorsInformationCentre("Visitors Information Centre"),
    OnlineTravelAgency("Online Travel Agency"),

    Concierge_Hotel("Concierge / Hotel"),

    DailyDeals("Daily Deals"),
    InboundTourOperator("Inbound Tour Operator"),
    InformationWebsite("Information Website"),
    Airline("Airline"),
    Affiliate("Affiliate"),
    Developer("Developer"),
    Supplier("Supplier"),*/
    Other("Other"),
    Bank("Bank"),
    ;

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumSupplierType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.supplier.type."
                + this.name());
        return displayStatusString;
    }
}
