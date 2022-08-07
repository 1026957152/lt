package com.lt.dom.oct;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class PickupPoint {


    @Id
    private long id;


    private long pickupId;//	The name of the pickup point. This is a mandatory field.

    private String pickup_name;//	The name of the pickup point. This is a mandatory field.
    private LocalDateTime pickupTime;//	The name of the pickup point. This is a mandatory field.



    private String description;//	Any extra information for the client, e.g. "Meet in the reception lounge"
    private String address1;//	First line of the address
    private String address2;//	Second line of the address
    private String postcode;//	Postcode
    private String lat;//	Latitude - geographic coordinate
    private String long_;//	Longitude - geographic coordinate
    private String staff_only;//	1/0
      //      0 - All - Can be added by customers, travel agents and staff (Default)
//1 - Staff only - Can ONLY be added to a booking by staff only (not customers / travel agents)
    private String rank	;//Numeric - Can be used as an additional ordering value for hotel pickup reports
    private String  viator_name;//	If you configure the hotel name EXACTLY as configured in Viator, TourCMS will map Viator bookings to the correct pickup point



/*        "id": "90e64d28-902f-4630-9932-5f0fb46992aa",
                "name": "Americania Hotel - 121 7th St",
                "directions": "Meet at Americania Hotel - 121 7th St.",
                "latitude": 37.7788005,
                "longitude": -122.4102065,
                "localDateTime": "2020-12-01T05:40:00-08:00",
                "googlePlaceId": "ChIJOTbxc4OAhYARrK82JwxWZFY",
                "street": "121 7th Street",
                "postal_code": "94103",
                "locality": "San Francisco",
                "region": "California",
                "state": "CA",
                "country": "US"*/
}
