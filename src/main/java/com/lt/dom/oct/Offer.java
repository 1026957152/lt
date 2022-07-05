package com.lt.dom.oct;

import java.util.List;
//https://developers.designmynight.com/api/offers-api/#day-rules
public class Offer {


    private String _id;//	string	The ID of the offer
    private String page_id;//	string	The ID of the venue in which the offer applies to
    private String page_status;//	string	The visibility status of the venue
    private String page_can_book;//	boolean	Whether the venue can be booked online
    private String region_id;//	string	The ID of the region this venue is located in
    private String title;//	string	The title of the offer
    private String type_of_offer;//	array	Currently unused, but may be used in the future
    private String type_of_venue;//	array	An array of objects describing the type of venue
    private String availability;//	string	The source where bookings will be eligible to use this offer. Possible values are
/*• "" - All bookings
• partner - Only bookings from your website
• designmynight - Only bookings from DesignMyNight*/
    private String description;//	string	A description of the offer
    private String start_date;//	string	When this offer is valid from, in ISO 8601 format
    private String expiry_date;//	string	When this offer will expire, in ISO 8601 format
    private String visible_from;//	string	When this offer will be visible from, in ISO 8601 format
    private String visible_to;//	string	When this offer will be visible until, in ISO 8601 format
    private List<OfferRules> rules;//	object	An object containing rules for this offer. See Offer Rules
    private boolean restrict_offer_to_types;//	boolean	Whether this offer only applies to specific booking types
    private String booking_types;//	array (only present if restrict_offer_to_types is true)	An array of booking types which this offer is eligible for
}
