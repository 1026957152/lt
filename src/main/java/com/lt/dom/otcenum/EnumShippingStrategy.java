package com.lt.dom.otcenum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.ResourceBundle;
//https://deliverr.com/blog/shopify-fulfillment/
//
public enum EnumShippingStrategy {
    Handle_shipping_yourself("Handle shipping yourself"),
    Outsource_your_fulfillment("Outsource your fulfillment"),
    Dropship("Dropship"),



  //  Handle shipping yourself – You can choose to ship from your home, and either handle labels yourself or automate the process. You can choose between taking your items to the post office, or having them picked up by a courier.
  //  Outsource your fulfillment – Work with a fulfillment partner to store, ship, and track your packages. They will get a notice when an order has been placed and will take care of the fulfillment process from there.
   //         Dropship – After an order comes in, it goes directly to your supplier or dropship partner, and they send it directly to the customer. That means you don’t have to worry about inventory at all.

    ;
    public String name;

    EnumShippingStrategy(String name) {

        this.name = name;
    }


    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());


    @Autowired
    private MessageSource messageSource;


/*    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.pass.fulfillment.status."
                + this.name());
        return displayStatusString;
    }*/

}
