package com.lt.dom.oct;


import javax.persistence.Entity;

@Entity
public class Address extends Base{

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

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityUrlSuffixText() {
        return cityUrlSuffixText;
    }

    public void setCityUrlSuffixText(String cityUrlSuffixText) {
        this.cityUrlSuffixText = cityUrlSuffixText;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateUrlSuffixText() {
        return stateUrlSuffixText;
    }

    public void setStateUrlSuffixText(String stateUrlSuffixText) {
        this.stateUrlSuffixText = stateUrlSuffixText;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getDirectionsUrl() {
        return directionsUrl;
    }

    public void setDirectionsUrl(String directionsUrl) {
        this.directionsUrl = directionsUrl;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getMarketUrlSuffixText() {
        return marketUrlSuffixText;
    }

    public void setMarketUrlSuffixText(String marketUrlSuffixText) {
        this.marketUrlSuffixText = marketUrlSuffixText;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }
}
