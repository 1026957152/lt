package com.lt.dom.otcReq;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.oct.Address;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationEditResp {



    private String addressLine1;//"addressLine1";//: "3000 Chesterfield Mall",
    private String  city;//       "city": "CHESTERFIELD",
    private String  cityUrlSuffixText;//       "cityUrlSuffixText": "chesterfield",
    private String  postalCode;//       "postalCode": "63017",
    private String  state;//       "state": "MO",
    private String  stateName;//       "stateName": "MISSOURI",
    private String  stateUrlSuffixText;//       "stateUrlSuffixText": "missouri",
    private String  country;//       "country": "United States",
    private Double   latitude;//        "latitude": 38.652248,
    private Double  longitude;//        "longitude": -90.562027,
    private String  directionsUrl;//        "directionsUrl": "http://bing.com/maps/default.aspx?v=2&lvl=9&style=r&mode=D&rtop=0~0~0~&cp=38.6522480000~-90.5620270000&rtp=adr.~pos.38.6522480000_-90.5620270000_AMC+CLASSIC+Chesterfield+14+3000+Chesterfield+Mall+CHESTERFIELD+MO+63017",
    private String  marketName;//        "marketName": "St. Louis",
    private String  marketUrlSuffixText;//        "marketUrlSuffixText": "st-louis",
    private String  marketId;//        "marketId": 107
    private String address;

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public static LocationEditResp from(Address location) {

        LocationEditResp productResp = new LocationEditResp();

        productResp.setAddress(location.getAddressLine1());
        productResp.setLatitude(location.getLatitude());
        productResp.setLongitude(location.getLongitude());


        return productResp;

    }


}
