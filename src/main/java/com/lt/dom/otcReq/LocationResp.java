package com.lt.dom.otcReq;

public class LocationResp {



    private String addressLine1;//"addressLine1";//: "3000 Chesterfield Mall",
    private String  city;//       "city": "CHESTERFIELD",
    private String  cityUrlSuffixText;//       "cityUrlSuffixText": "chesterfield",
    private String  postalCode;//       "postalCode": "63017",
    private String  state;//       "state": "MO",
    private String  stateName;//       "stateName": "MISSOURI",
    private String  stateUrlSuffixText;//       "stateUrlSuffixText": "missouri",
    private String  country;//       "country": "United States",
    private float   latitude;//        "latitude": 38.652248,
    private float  longitude;//        "longitude": -90.562027,
    private String  directionsUrl;//        "directionsUrl": "http://bing.com/maps/default.aspx?v=2&lvl=9&style=r&mode=D&rtop=0~0~0~&cp=38.6522480000~-90.5620270000&rtp=adr.~pos.38.6522480000_-90.5620270000_AMC+CLASSIC+Chesterfield+14+3000+Chesterfield+Mall+CHESTERFIELD+MO+63017",
    private String  marketName;//        "marketName": "St. Louis",
    private String  marketUrlSuffixText;//        "marketUrlSuffixText": "st-louis",
    private String  marketId;//        "marketId": 107
    private String address;

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }
}
