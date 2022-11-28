package com.lt.dom.OctResp;

import com.lt.dom.oct.Address;

public class LocationReq {
    private String address;


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

    public static LocationReq from(Address location) {

        if(location != null){
            LocationReq locationReq = new LocationReq();
            locationReq.setAddress(location.getAddressLine1());
            locationReq.setLongitude(location.getLongitude());
            locationReq.setLatitude(location.getLatitude());
            return locationReq;
        }else{
            LocationReq locationReq = new LocationReq();
            return locationReq;
        }


    }

    public String getAddress() {
        return address;
    }

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
}
