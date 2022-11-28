package com.lt.dom.otcenum;
//https://bokun.dev/booking-api-restful/vU6sCfxwYdJWd1QAcLt12i/introduction-to-the-data-model-of-products/mGtiogVmyzywvDaZFK29b5
import com.lt.dom.OctResp.EnumResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public enum EnumMeetingype {

 //   Showtime ("Allocates costs across your targets based on the proportional weighted cost of each target."),

//    Movie("Allocates costs across your targets based on your defined allocation percentage."),

    MEET_ON_LOCATION("split - Allocates costs evenly across all targets."),

   // RightVoucher("split - Allocates costs evenly across all targets."),




  //  Activity("Activity  Short or specific activities such as skydive jump, balloon flight, scuba dive and other sporting activities."),
  PICK_UP("Day tour   Any type of short tour, for example walking city tour, bus tours, guided bike ride, sightseeing cruise, guided kayak tour, etc."),

 //   GiftCard("Gift Card  A gift card that can be purchased for your other products. You can define a fixed amount and/or bookable products, and an expiry date."),
 //   Multiday("Multi-day tourLonger tours which last for multiple days. For example hiking expeditions and excursions."),
  //  Privatetour("Private tour  Tours that only accept a single booking for each available time, for example private walking tour."),
  //  Rental("Rental  Equipment or vehicle hire with a pricing per duration, such as bike hire, kayak hire or car rental."),
  //  Ticket("Ticket  Best used for museums entries and attractions such as animal parks (Zoo, Aquarium, etc.)"),
  //  TransferShuttleFlight("Transfer, Shuttle or Flight    Tickets which takes the holder from one place to another and give return options. For example a boat transfer, airport shuttle or flight ticket for airlines."),
  //  Lesson("Lesson  For example surf classes or cooking lessons."),
  //  PrivateCharter("Private Charter  For example fishing boat or house boat hire. Private charters accept a single booking for each available time, and make resources private for each booking."),
  //  Event("Event  Any generic event like a sports game, a festival, etc."),
  //  Merchandise(" Merchandise  Products that don't have availability like t-shirts, souvenirs, etc."),
 MEET_ON_LOCATION_OR_PICK_UP(" Merchandise  Products that don't have availability like t-shirts, souvenirs, etc."),
 //   Customproduct("Custom product    This lets you create a product completely from scratch, with all options available.")
    //

    ;





    public static List<EnumResp> from() {
        return Arrays.stream(EnumMeetingype.values()).map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());

            return enumResp;
        }).collect(Collectors.toList());
    }


    public static List<EnumResp> from(List<EnumMeetingype> supportProductType) {
        return supportProductType.stream().map(x->{
            EnumResp enumResp = new EnumResp();
            enumResp.setId(x.name());
            //  enumResp.setName(x.name());
            enumResp.setText(x.toString());

            return enumResp;
        }).collect(Collectors.toList());
    }

    private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages",
            Locale.getDefault());

    public String name;

    @Autowired
    private MessageSource messageSource;

    EnumMeetingype(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        String displayStatusString = resourceBundle.getString("page.systemadministration.product.type."
                + this.name());
        return displayStatusString;
    }


}
