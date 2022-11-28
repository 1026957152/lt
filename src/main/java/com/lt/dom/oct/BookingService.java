package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumBookingServiceType;
import com.lt.dom.otcenum.EnumPrivacyLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;


//https://developer.makeplans.com/#slots
@Entity
public class BookingService extends Base{

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

    @Column(name = "interval_")
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
    private long supplier;
    private EnumPrivacyLevel privacyLevel;
    private String slug;
    private String desc_short;

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

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public long getSupplier() {

        return supplier;
    }

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public void setPrivacyLevel(EnumPrivacyLevel privacyLevel) {
        this.privacyLevel = privacyLevel;
    }

    public EnumPrivacyLevel getPrivacyLevel() {
        return privacyLevel;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSlug() {
        return slug;
    }

    public void setDesc_short(String desc_short) {
        this.desc_short = desc_short;
    }

    public String getDesc_short() {
        return desc_short;
    }
}
