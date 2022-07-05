package com.lt.dom.oct.语音导游;

public class ObservationLocations {
    private String sensorID;
    private String location;


    private String hasMap; //	Map  or	A URL to a map of the place. Supersedes map, maps.
           // URL
           private Boolean isAccessibleForFree;//	Boolean	A flag to signal that the item, event, or place is accessible for free. Supersedes free.
    //isicV4	Text	The International Standard of Industrial Classification of All Economic Activities (ISIC), Revision 4 code for a particular organization, business person, or place.
    private String keywords;//	DefinedTerm  or	Keywords or tags used to describe some item. Multiple textual entries in a keywords list are typically delimited by commas, or by repeating the property.
   // Text  or
    //        URL
    private float latitude;//	Number  or	The latitude of a location. For example 37.42242 (WGS 84).
   // Text
    private String logo;//	ImageObject  or	An associated logo.
   // URL
   private float longitude;//	Number  or	The longitude of a location. For example -122.08585 (WGS 84).
    //Text
   private int  maximumAttendeeCapacity;//	Integer	The total number of individuals that may attend an event or venue.
    private int openingHoursSpecification;//	OpeningHoursSpecification	The opening hours of a certain place.
   private String photo;//	ImageObject  or	A photograph of this place. Supersedes photos.
           // Photograph
           private Boolean publicAccess;//	Boolean	A flag to signal that the Place is open to public visitors. If this property is omitted there is no assumed default boolean value
    private String review;//	Review	A review of the item. Supersedes reviews.
    private String slogan;//	Text	A slogan or motto associated with the item.
    private Boolean smokingAllowed;//	Boolean	Indicates whether it is allowed to smoke in the place, e.g. in the restaurant, hotel or hotel room.
    private Boolean specialOpeningHoursSpecification;//	OpeningHoursSpecification	The special opening hours of a certain place.

    //Use this to explicitly override general opening hours brought in scope by openingHoursSpecification or openingHours.

}
