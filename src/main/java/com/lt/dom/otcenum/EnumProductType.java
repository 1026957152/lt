package com.lt.dom.otcenum;

public enum EnumProductType {

    Showtime ("Allocates costs across your targets based on the proportional weighted cost of each target."),

    Movie("Allocates costs across your targets based on your defined allocation percentage."),

    Attraction("split - Allocates costs evenly across all targets."),

    RightVoucher("split - Allocates costs evenly across all targets."),




    Activity("Activity  Short or specific activities such as skydive jump, balloon flight, scuba dive and other sporting activities."),
    Daytour("Day tour   Any type of short tour, for example walking city tour, bus tours, guided bike ride, sightseeing cruise, guided kayak tour, etc."),
    GiftCard("Gift Card  A gift card that can be purchased for your other products. You can define a fixed amount and/or bookable products, and an expiry date."),
    Multiday("Multi-day tourLonger tours which last for multiple days. For example hiking expeditions and excursions."),
    Privatetour("Private tour  Tours that only accept a single booking for each available time, for example private walking tour."),
    Rental("Rental  Equipment or vehicle hire with a pricing per duration, such as bike hire, kayak hire or car rental."),
    Ticket("Ticket  Best used for museums entries and attractions such as animal parks (Zoo, Aquarium, etc.)"),
    TransferShuttleFlight("Transfer, Shuttle or Flight    Tickets which takes the holder from one place to another and give return options. For example a boat transfer, airport shuttle or flight ticket for airlines."),
    Lesson("Lesson  For example surf classes or cooking lessons."),
    PrivateCharter("Private Charter  For example fishing boat or house boat hire. Private charters accept a single booking for each available time, and make resources private for each booking."),
    Event("Event  Any generic event like a sports game, a festival, etc."),
    Merchandise(" Merchandise  Products that don't have availability like t-shirts, souvenirs, etc."),
    Customproduct("Custom product    This lets you create a product completely from scratch, with all options available.");


    EnumProductType(String desc) {

    }
}
