package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.*;

import java.time.LocalTime;
import java.util.List;


//https://developer.makeplans.com/#slots


public class BookingResourceReq  {


    private String slug;
    private String desc_long;
    private String desc_short;
    private EnumPrivacyLevel privacyLevel;

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDesc_long() {
        return desc_long;
    }

    public void setDesc_long(String desc_long) {
        this.desc_long = desc_long;
    }

    public String getDesc_short() {
        return desc_short;
    }

    public void setDesc_short(String desc_short) {
        this.desc_short = desc_short;
    }



    @JsonProperty("guide")
    private Guide guide;

    public Guide getGuide() {
        return guide;
    }

    public void setGuide(Guide guide) {
        this.guide = guide;
    }

    public EnumPrivacyLevel getPrivacyLevel() {
        return privacyLevel;
    }

    public void setPrivacyLevel(EnumPrivacyLevel privacyLevel) {
        this.privacyLevel = privacyLevel;
    }

    public static class Guide {

        private EnumGuideLevel guideLevel;


        private EnumGuideService service;

        public EnumGuideLevel getGuideLevel() {
            return guideLevel;
        }

        public void setGuideLevel(EnumGuideLevel guideLevel) {
            this.guideLevel = guideLevel;
        }

        public EnumGuideService getService() {
            return service;
        }

        public void setService(EnumGuideService service) {
            this.service = service;
        }
    }


    @JsonProperty("capacity")
    private Integer capacity;


    @JsonFormat(pattern = "HH:mm")
    @JsonProperty("opening_hours_mon")
    private List<LocalTime> openingHoursMon;

    @JsonFormat(pattern = "HH:mm")
    @JsonProperty("opening_hours_tue")
    private List<LocalTime> openingHoursTue;
    @JsonFormat(pattern = "HH:mm")
    @JsonProperty("opening_hours_wed")
    private List<LocalTime> openingHoursWed;
    @JsonFormat(pattern = "HH:mm")
    @JsonProperty("opening_hours_thu")
    private List<LocalTime> openingHoursThu;
    @JsonFormat(pattern = "HH:mm")
    @JsonProperty("opening_hours_fri")
    private List<LocalTime> openingHoursFri;
    @JsonFormat(pattern = "HH:mm")
    @JsonProperty("opening_hours_sat")
    private List<LocalTime> openingHoursSat;
    @JsonFormat(pattern = "HH:mm")
    @JsonProperty("opening_hours_sun")
    private List<LocalTime> openingHoursSun;
    @JsonProperty("title")
    private String title;
    private String description;
    private EnumResourceType type;

    private List<MuseumReq.MediaReq> medias;
    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public List<LocalTime> getOpeningHoursMon() {
        return openingHoursMon;
    }

    public void setOpeningHoursMon(List<LocalTime> openingHoursMon) {
        this.openingHoursMon = openingHoursMon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public List<MuseumReq.MediaReq> getMedias() {
        return medias;
    }

    public void setMedias(List<MuseumReq.MediaReq> medias) {
        this.medias = medias;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EnumResourceType getType() {
        return type;
    }

    public void setType(EnumResourceType type) {
        this.type = type;
    }

    public List<LocalTime> getOpeningHoursTue() {
        return openingHoursTue;
    }

    public void setOpeningHoursTue(List<LocalTime> openingHoursTue) {
        this.openingHoursTue = openingHoursTue;
    }

    public List<LocalTime> getOpeningHoursWed() {
        return openingHoursWed;
    }

    public void setOpeningHoursWed(List<LocalTime> openingHoursWed) {
        this.openingHoursWed = openingHoursWed;
    }

    public List<LocalTime> getOpeningHoursThu() {
        return openingHoursThu;
    }

    public void setOpeningHoursThu(List<LocalTime> openingHoursThu) {
        this.openingHoursThu = openingHoursThu;
    }

    public List<LocalTime> getOpeningHoursFri() {
        return openingHoursFri;
    }

    public void setOpeningHoursFri(List<LocalTime> openingHoursFri) {
        this.openingHoursFri = openingHoursFri;
    }

    public List<LocalTime> getOpeningHoursSat() {
        return openingHoursSat;
    }

    public void setOpeningHoursSat(List<LocalTime> openingHoursSat) {
        this.openingHoursSat = openingHoursSat;
    }

    public List<LocalTime> getOpeningHoursSun() {
        return openingHoursSun;
    }

    public void setOpeningHoursSun(List<LocalTime> openingHoursSun) {
        this.openingHoursSun = openingHoursSun;
    }
}
