package com.lt.dom.OctResp;

import com.google.gson.Gson;
import com.lt.dom.oct.Theatre;
import com.lt.dom.otcenum.EnumRedemptionMethod;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class TheatreReq {



 private String name;//	Name of the theater
 private String longName;//        "longName": "AMC Streets Of St Charles 8",

 private String shortdesc;//        "longName": "AMC Streets Of St Charles 8",
 private String longdesc;//        "longName": "AMC Streets Of St Charles 8",
 private String slug;//         "slug": "amc-streets-of-st-charles-8",
 private Boolean closed;//          "utcOffset": "-05:00",
 private String guestServicesPhoneNumber;//          "guestServicesPhoneNumber": "636-352-0123",
 private String outageDescription;//          "outageDescription": "This theatre is temporarily closed in accordance with local, state and federal guidelines. It will re-open when those guidelines allow. Please continue to check back here for updates.",

 private String redemptionMethods_json;//          "outageDescription": "This theatre is temporarily closed in accordance with local, state and federal guidelines. It will re-open when those guidelines allow. Please continue to check back here for updates.",


    private List<EnumRedemptionMethod> redemptionMethods;

    @Valid
    private List<AttributeResp> attributes;


    @Valid
    private LocationReq location;//


/*
 @Valid
    private MediaReq media;

*/


    private PhotoResp cover;
    private String code;
    private Integer seatsAvailable;
    private Map parameterList;
    private List movies;

    public static TheatreReq from(Theatre e) {

        TheatreReq theatreResp = new TheatreReq();

        theatreResp.setCode(e.getCode());
        theatreResp.setName(e.getName());
        theatreResp.setLongName(e.getLongName());
        theatreResp.setShortdesc(e.getShortdesc());
        theatreResp.setLongdesc(e.getLongdesc());
        theatreResp.setGuestServicesPhoneNumber(e.getGuestServicesPhoneNumber());
        theatreResp.setSeatsAvailable(e.getSeatsAvailable());
        theatreResp.setSlug(e.getSlug());
        theatreResp.setGuestServicesPhoneNumber(e.getGuestServicesPhoneNumber());
        theatreResp.setClosed(e.getClosed());
        theatreResp.setOutageDescription(e.getOutageDescription());


        theatreResp.setClosed(e.getClosed());
        if(e.getRedemptionMethods_json() != null){
            theatreResp.setRedemptionMethods(Arrays.stream(new Gson().fromJson(e.getRedemptionMethods_json(),EnumRedemptionMethod[].class)).toList());

        }
        return theatreResp;
    }

    public PhotoResp getCover() {
        return cover;
    }

    public void setCover(PhotoResp cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public String getLongdesc() {
        return longdesc;
    }

    public void setLongdesc(String longdesc) {
        this.longdesc = longdesc;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public String getGuestServicesPhoneNumber() {
        return guestServicesPhoneNumber;
    }

    public void setGuestServicesPhoneNumber(String guestServicesPhoneNumber) {
        this.guestServicesPhoneNumber = guestServicesPhoneNumber;
    }

    public String getOutageDescription() {
        return outageDescription;
    }

    public void setOutageDescription(String outageDescription) {
        this.outageDescription = outageDescription;
    }

    public String getRedemptionMethods_json() {
        return redemptionMethods_json;
    }

    public void setRedemptionMethods_json(String redemptionMethods_json) {
        this.redemptionMethods_json = redemptionMethods_json;
    }

    public List<EnumRedemptionMethod> getRedemptionMethods() {
        return redemptionMethods;
    }

    public void setRedemptionMethods(List<EnumRedemptionMethod> redemptionMethods) {
        this.redemptionMethods = redemptionMethods;
    }


    public List<AttributeResp> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeResp> attributes) {
        this.attributes = attributes;
    }

    public LocationReq getLocation() {
        return location;
    }

    public void setLocation(LocationReq location) {
        this.location = location;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setSeatsAvailable(Integer seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public Integer getSeatsAvailable() {
        return seatsAvailable;
    }

    public <V, K> void setParameterList(Map parameterList) {
        this.parameterList = parameterList;
    }

    public Map getParameterList() {
        return parameterList;
    }

    public <R> void setMovies(List movies) {
        this.movies = movies;
    }

    public List getMovies() {
        return movies;
    }

/*    public MediaReq getMedia() {
        return media;
    }

    public void setMedia(MediaReq media) {
        this.media = media;
    }*/
}
