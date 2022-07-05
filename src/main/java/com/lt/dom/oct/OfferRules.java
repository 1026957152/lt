package com.lt.dom.oct;

public class OfferRules {


    private int min_people;//	integer	The minimum number of people required in a booking for this offer. null if the rule has not been set.
    private int max_people;//	integer	The maximum number of people allowed in a booking for this offer. null if the rule has not been set.
    private DayRule monday;//	See Day Rules	Rules specific to bookings on a Monday
    private DayRule tuesday;//	See Day Rules	Rules specific to bookings on a Tuesday
    private DayRule wednesday;//	See Day Rules	Rules specific to bookings on a Wednesday
    private DayRule thursday;//	See Day Rules	Rules specific to bookings on a Thursday
    private DayRule friday;//	See Day Rules	Rules specific to bookings on a Friday
    private DayRule saturday;//	See Day Rules	Rules specific to bookings on a Saturday
    private DayRule sunday;//	See Day Rules	Rules specific to bookings on a Sunday


}
