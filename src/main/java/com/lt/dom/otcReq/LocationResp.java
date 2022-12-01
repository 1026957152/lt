package com.lt.dom.otcReq;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.lt.dom.OctResp.ProductResp;
import com.lt.dom.oct.*;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationResp {



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
    private Float   lat;//        "latitude": 38.652248,
    private Float  lng;//        "longitude": -90.562027,

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public void setLng(Float lng) {
        this.lng = lng;
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

    public String getAddress() {
        return address;
    }

    public static LocationResp from(Address location) {

        LocationResp productResp = new LocationResp();
        if(location != null){
            productResp.setAddress(location.getAddressLine1()== null?"":location.getAddressLine1());
            productResp.setLatitude(location.getLatitude());
            productResp.setLongitude(location.getLongitude());

        }


        return productResp;

    }

    public static LocationResp latLngFrom(Address location) {

        LocationResp productResp = new LocationResp();
        if(location != null){
            productResp.setAddress(location.getAddressLine1());
            productResp.setLatitude(location.getLatitude());
            productResp.setLongitude(location.getLongitude());

        }


        return productResp;

    }
}
