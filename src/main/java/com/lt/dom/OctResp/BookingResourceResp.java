package com.lt.dom.OctResp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lt.dom.oct.BookingResource;
import com.lt.dom.otcenum.EnumGuideLevel;
import com.lt.dom.otcenum.EnumGuideService;
import com.lt.dom.otcenum.EnumPrivacyLevel;
import com.lt.dom.otcenum.EnumResourceType;
import com.lt.dom.serviceOtc.JsonParse;
import com.lt.dom.vo.AvailabilityCalendarVO;

import javax.persistence.Transient;
import java.time.LocalTime;
import java.util.List;


//https://developer.makeplans.com/#slots

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingResourceResp {


    private String desc_short;
    private String desc_long;
    private EnumPrivacyLevel privacyLevel;

    public String getDesc_short() {
        return desc_short;
    }

    public void setDesc_short(String desc_short) {
        this.desc_short = desc_short;
    }

    public String getDesc_long() {
        return desc_long;
    }

    public void setDesc_long(String desc_long) {
        this.desc_long = desc_long;
    }

    public EnumPrivacyLevel getPrivacyLevel() {
        return privacyLevel;
    }

    public void setPrivacyLevel(EnumPrivacyLevel privacyLevel) {
        this.privacyLevel = privacyLevel;
    }

    @JsonProperty("capacity")
    private Integer capacity;

    @Transient
    @JsonProperty("opening_hours_mon")
    private LocalTime[] openingHoursMon;

    @Transient
    @JsonProperty("opening_hours_tue")
    private LocalTime[] openingHoursTue;
    @Transient
    @JsonProperty("opening_hours_wed")
    private LocalTime[] openingHoursWed;
    @Transient
    @JsonProperty("opening_hours_thu")
    private LocalTime[] openingHoursThu;
    @Transient
    @JsonProperty("opening_hours_fri")
    private LocalTime[] openingHoursFri;
    @Transient
    @JsonProperty("opening_hours_sat")
    private LocalTime[] openingHoursSat;
    @Transient
    @JsonProperty("opening_hours_sun")
    private LocalTime[] openingHoursSun;
    @JsonProperty("title")
    private String title;
    private String code;
    private EnumResourceType type;
    private String type_text;
    private EnumGuideService service;
    private EnumGuideLevel guideLevel;
    private String guideLevel_text;
    private String service_text;
    private List<AvailabilityCalendarVO> availability;
    private MediaResp media;


    public static BookingResourceResp fromwithAvailability(BookingResource e, List<AvailabilityCalendarVO> availabilityCalendarVOS) {
        BookingResourceResp bookingResourceResp= fromSimple(e);
        bookingResourceResp.setAvailability(availabilityCalendarVOS);

        return bookingResourceResp;
    }

    public static BookingResourceResp fromSimple(BookingResource e) {
        BookingResourceResp bookingResourceResp = new BookingResourceResp();
        bookingResourceResp.setCapacity(e.getCapacity());
        bookingResourceResp.setCode(e.getCode());
        bookingResourceResp.setTitle(e.getTitle());

        bookingResourceResp.setType(e.getType());
        bookingResourceResp.setType_text(e.getType().toString());
        if(e.getType().equals(EnumResourceType.guide)){
            bookingResourceResp.setGuideLevel(e.getGuideLevel());
            bookingResourceResp.setGuideLevel_text(e.getGuideLevel().toString());
            bookingResourceResp.setService(e.getService());
            bookingResourceResp.setService_text(e.getService().toString());
        }

        bookingResourceResp.setTitle(e.getTitle());
        bookingResourceResp.setPrivacyLevel(e.getPrivacyLevel());
        bookingResourceResp.setDesc_long(e.getDesc_long());
        bookingResourceResp.setDesc_short(e.getDesc_short());

        return bookingResourceResp;
    }

    public static BookingResourceResp from(BookingResource e) {
        BookingResourceResp bookingResourceResp= fromSimple(e);
        Gson gson = new GsonBuilder()
                //    .registerTypeAdapter(LocalDate.class, new JsonParse.LocalDateAdapter())
                //         .registerTypeAdapter(LocalDateTime.class, new JsonParse.LocalDateTimeAdapter())
                .registerTypeAdapter(LocalTime.class, new JsonParse.LocalTimeAdapter())

                .create();

        bookingResourceResp.setOpeningHoursMon(gson.fromJson(e.getOpeningHoursMon(),LocalTime[].class));
        //  showtime.setOpeningHoursMon(bookingResourceReq.getOpeningHoursMon());

        bookingResourceResp.setOpeningHoursMon(gson.fromJson(e.getOpeningHoursMon(),LocalTime[].class));
        bookingResourceResp.setOpeningHoursTue(gson.fromJson(e.getOpeningHoursTue(),LocalTime[].class));
        bookingResourceResp.setOpeningHoursThu(gson.fromJson(e.getOpeningHoursThu(),LocalTime[].class));
        bookingResourceResp.setOpeningHoursWed(gson.fromJson(e.getOpeningHoursWed(),LocalTime[].class));
        bookingResourceResp.setOpeningHoursFri(gson.fromJson(e.getOpeningHoursFri(),LocalTime[].class));
        bookingResourceResp.setOpeningHoursSat(gson.fromJson(e.getOpeningHoursSat(),LocalTime[].class));
        bookingResourceResp.setOpeningHoursSun(gson.fromJson(e.getOpeningHoursSun(),LocalTime[].class));
        return bookingResourceResp;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public LocalTime[] getOpeningHoursMon() {
        return openingHoursMon;
    }

    public void setOpeningHoursMon(LocalTime[] openingHoursMon) {
        this.openingHoursMon = openingHoursMon;
    }

    public LocalTime[] getOpeningHoursTue() {
        return openingHoursTue;
    }

    public void setOpeningHoursTue(LocalTime[] openingHoursTue) {
        this.openingHoursTue = openingHoursTue;
    }

    public LocalTime[] getOpeningHoursWed() {
        return openingHoursWed;
    }

    public void setOpeningHoursWed(LocalTime[] openingHoursWed) {
        this.openingHoursWed = openingHoursWed;
    }

    public LocalTime[] getOpeningHoursThu() {
        return openingHoursThu;
    }

    public void setOpeningHoursThu(LocalTime[] openingHoursThu) {
        this.openingHoursThu = openingHoursThu;
    }

    public LocalTime[] getOpeningHoursFri() {
        return openingHoursFri;
    }

    public void setOpeningHoursFri(LocalTime[] openingHoursFri) {
        this.openingHoursFri = openingHoursFri;
    }

    public LocalTime[] getOpeningHoursSat() {
        return openingHoursSat;
    }

    public void setOpeningHoursSat(LocalTime[] openingHoursSat) {
        this.openingHoursSat = openingHoursSat;
    }

    public LocalTime[] getOpeningHoursSun() {
        return openingHoursSun;
    }

    public void setOpeningHoursSun(LocalTime[] openingHoursSun) {
        this.openingHoursSun = openingHoursSun;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setType(EnumResourceType type) {
        this.type = type;
    }

    public EnumResourceType getType() {
        return type;
    }

    public void setType_text(String type_text) {

        this.type_text = type_text;
    }

    public String getType_text() {
        return type_text;
    }

    public void setService(EnumGuideService service) {
        this.service = service;
    }

    public EnumGuideService getService() {
        return service;
    }

    public void setGuideLevel(EnumGuideLevel guideLevel) {
        this.guideLevel = guideLevel;
    }

    public EnumGuideLevel getGuideLevel() {
        return guideLevel;
    }

    public void setGuideLevel_text(String guideLevel_text) {
        this.guideLevel_text = guideLevel_text;
    }

    public String getGuideLevel_text() {
        return guideLevel_text;
    }

    public void setService_text(String service_text) {
        this.service_text = service_text;
    }

    public String getService_text() {
        return service_text;
    }

    public void setAvailability(List<AvailabilityCalendarVO> availability) {
        this.availability = availability;
    }

    public List<AvailabilityCalendarVO> getAvailability() {
        return availability;
    }

    public void setMedia(MediaResp media) {
        this.media = media;
    }

    public MediaResp getMedia() {
        return media;
    }
}
