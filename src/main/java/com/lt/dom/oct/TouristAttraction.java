package com.lt.dom.oct;

import javax.persistence.*;
import java.util.List;


@Entity
//@Table(name = "TouristAttraction")
public class TouristAttraction {

    @Version
    private Integer version;
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long id;
  //  private EnumAttractionCategoryCode attractionCategoryCode ;

    private String name ="Orient" ;


    private String kinds;
    private String wikidata;
    private String voyage;
    private float longitude;
    private float latitude;
    //private List<String> images;

    private String phone;
    private String fax;


    private Boolean OfferBreakfast= true;
    private Boolean OfferBrunch= true;
    private Boolean OfferDinner= true;
    private Boolean OfferLunch= true;

  //  List<Policy> policies;


   // private FacilityInfo facilityInfo;

   // List<ContactInfo> contactInfos;


    //List<Attraction> attractions;


   // private Supplier supplier;



    //Container for:
    // StandardPhrases,
    // GuestInformation,
    // PropertyTaxInfo,
    // SellMealsThroughBooking,
    // PreventLikelyToBeCancelledBookings,
    // CancellationGracePeriod,
    // TotalNumberOfFloors,
    // PricingType.
}
