package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.OctResp.BaseResp;
import com.lt.dom.OctResp.MediaResp;
import com.lt.dom.oct.Base;
import com.lt.dom.oct.BookingService;
import com.lt.dom.otcenum.EnumBookingServiceType;
import com.lt.dom.otcenum.EnumPrivacyLevel;

import javax.persistence.Entity;
import java.util.List;


//https://developer.makeplans.com/#slots
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingServiceResp extends BaseResp {

    @JsonProperty("active")
    private Boolean active;
    @JsonProperty("booking_capacity")
    private Integer bookingCapacity;
    @JsonProperty("booking_type")
    private EnumBookingServiceType type;
    @JsonProperty("description")
    private String description;

    @JsonProperty("custom_data")
    private String customData;
    @JsonProperty("day_booking_specify_time")
    private Boolean dayBookingSpecifyTime;
    @JsonProperty("has_day_booking")
    private Boolean hasDayBooking;

    @JsonProperty("interval")
    private Integer interval;
    @JsonProperty("max_slots")
    private Integer maxSlots;
    @JsonProperty("price")
    private Float price;
    @JsonProperty("same_day")
    private Boolean sameDay;
    @JsonProperty("template")
    private String template;
    @JsonProperty("title")
    private String title;
    private String code;
    private String desc_long;
    private String desc_shot;
    private String slug;
    private Integer duration;
    private EnumPrivacyLevel privacyLevel;
    private String privacyLevel_text;
    private MediaResp media;

    public static BookingServiceResp from(BookingService bookingService) {
        BookingServiceResp bookingServiceResp = new BookingServiceResp();
        bookingServiceResp.setTitle(bookingService.getTitle());
        bookingServiceResp.setCode(bookingService.getCode());
        bookingServiceResp.setDesc_long(bookingService.getDescription());
        bookingServiceResp.setDesc_shot(bookingService.getDesc_short());
        bookingServiceResp.setSlug(bookingService.getSlug());
        bookingServiceResp.setType(bookingService.getType());
        bookingServiceResp.setDuration(bookingService.getInterval());
        bookingServiceResp.setActive(bookingService.getActive());
        bookingServiceResp.setBookingCapacity(bookingService.getBookingCapacity());
        bookingServiceResp.setPrivacyLevel(bookingService.getPrivacyLevel());
        bookingServiceResp.setPrivacyLevel_text(bookingService.getPrivacyLevel().toString());

        bookingServiceResp.setCreatedDate(bookingService.getCreatedDate());
        bookingServiceResp.setModifiedDate(bookingService.getModifiedDate());


       // bookingServiceResp.setStatus(bookingService.getStatus().toString());
        bookingServiceResp.setPrivacyLevel_text(bookingService.getPrivacyLevel().toString());

        return bookingServiceResp;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getBookingCapacity() {
        return bookingCapacity;
    }

    public void setBookingCapacity(Integer bookingCapacity) {
        this.bookingCapacity = bookingCapacity;
    }

    public EnumBookingServiceType getType() {
        return type;
    }

    public void setType(EnumBookingServiceType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomData() {
        return customData;
    }

    public void setCustomData(String customData) {
        this.customData = customData;
    }

    public Boolean getDayBookingSpecifyTime() {
        return dayBookingSpecifyTime;
    }

    public void setDayBookingSpecifyTime(Boolean dayBookingSpecifyTime) {
        this.dayBookingSpecifyTime = dayBookingSpecifyTime;
    }

    public Boolean getHasDayBooking() {
        return hasDayBooking;
    }

    public void setHasDayBooking(Boolean hasDayBooking) {
        this.hasDayBooking = hasDayBooking;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Integer getMaxSlots() {
        return maxSlots;
    }

    public void setMaxSlots(Integer maxSlots) {
        this.maxSlots = maxSlots;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Boolean getSameDay() {
        return sameDay;
    }

    public void setSameDay(Boolean sameDay) {
        this.sameDay = sameDay;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    private List<MuseumReq.MediaReq> medias;

    public List<MuseumReq.MediaReq> getMedias() {
        return medias;
    }

    public void setMedias(List<MuseumReq.MediaReq> medias) {
        this.medias = medias;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setDesc_long(String desc_long) {
        this.desc_long = desc_long;
    }

    public String getDesc_long() {
        return desc_long;
    }

    public void setDesc_shot(String desc_shot) {
        this.desc_shot = desc_shot;
    }

    public String getDesc_shot() {
        return desc_shot;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSlug() {
        return slug;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setPrivacyLevel(EnumPrivacyLevel privacyLevel) {
        this.privacyLevel = privacyLevel;
    }

    public EnumPrivacyLevel getPrivacyLevel() {
        return privacyLevel;
    }

    public void setPrivacyLevel_text(String privacyLevel_text) {
        this.privacyLevel_text = privacyLevel_text;
    }

    public String getPrivacyLevel_text() {
        return privacyLevel_text;
    }

    public void setMedia(MediaResp media) {
        this.media = media;
    }

    public MediaResp getMedia() {
        return media;
    }
}
