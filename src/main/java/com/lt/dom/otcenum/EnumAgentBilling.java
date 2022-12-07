package com.lt.dom.otcenum;

import com.lt.dom.OctResp.EnumResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public enum EnumAgentBilling {
    Retail_travel_agent("Retail travel agent"),
    Trusted_travel_agent("Trusted travel agent"),
    Lead_customer("Lead customer"),
    Lead_customer_Advertising_tracking("Blockedp"),

    ;

 //   2 = Retail travel agent (Trade sale - the travel agent responsible for paying for the booking) If the booking is value of 100 and the travel agent is on 10% commission the travel agent will pay 90 Ideal for retail travel agents who must pay at the point of booking (like customers do)

//-1 = Trusted travel agent (Trade sale - the travel agent responsible for paying for the booking) If the booking is value of 100 and the travel agent is on 10% commission the travel agent will pay 90 Web bookings will be immediately confirmed EVEN without payment

//1 = Lead customer (Customer will pay - but net of any commission due on the booking) If the booking is value of 100 and the travel agent is on 10% commission the customer will pay 90

      //      0 = Lead customer (Advertising tracking or web affiliate style agent - customer will pay the full value) If the booking is value of 100 and the travel agent is on 10% commission the customer will pay 100 and the travel agent will subsequently be paid 10. Ideal for web affiliates and hotel concierges

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumAgentBilling(String name) {
        this.name = name;
    }

    public static List List() {
        return  Arrays.stream(EnumAgentBilling.values()).map(x->{

            EnumResp enumResp = new EnumResp();

            enumResp.setId(x.name());
            enumResp.setText(x.toString());
            return enumResp;
        }).collect(Collectors.toList());

    }
/*    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.supplier.status."
                + this.name());
        return displayStatusString;
    }*/
}
