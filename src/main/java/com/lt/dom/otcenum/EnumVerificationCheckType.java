package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;

public enum EnumVerificationCheckType {
    document("Retail travel agent"),
    id_number("Trusted travel agent"),


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

    EnumVerificationCheckType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.supplier.status."
                + this.name());
        return displayStatusString;
    }
}
