package com.lt.dom.otcReq;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.oct.Base;
import com.lt.dom.otcenum.EnumBookingMakeplanState;
import com.lt.dom.otcenum.EnumBookingMakeplanStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


//https://developer.makeplans.com/#slots


public class BookingMakeplanReq {


    @NotNull
    @JsonProperty("booked_from")
    private LocalDateTime bookedFrom;
    @NotNull
    @JsonProperty("booked_to")
    private LocalDateTime bookedTo;

    @JsonProperty("custom_data")
    private Object customData;
    @JsonProperty("count")
    private Integer count;
    @Transient
    @JsonProperty("expires_at")
    private LocalDateTime expiresAt;
    @Transient
    @JsonProperty("external_id")
    private Object externalId;


    @JsonProperty("notes")
    private String notes;

    @NotNull
    @JsonProperty("all_day")
    private Boolean all_day;

    @JsonProperty("person_id")
    private Integer personId;


    @JsonProperty("resource_id")
    private Long resourceId;
    @JsonProperty("service_id")
    private Long serviceId;


    @JsonProperty("state")
    private EnumBookingMakeplanState state;
    @JsonProperty("status")
    private EnumBookingMakeplanStatus status;

    public Boolean getAll_day() {
        return all_day;
    }

    public void setAll_day(Boolean all_day) {
        this.all_day = all_day;
    }

    public LocalDateTime getBookedFrom() {
        return bookedFrom;
    }

    public void setBookedFrom(LocalDateTime bookedFrom) {
        this.bookedFrom = bookedFrom;
    }

    public LocalDateTime getBookedTo() {
        return bookedTo;
    }
    public void setBookedTo(LocalDateTime bookedTo) {
        this.bookedTo = bookedTo;
    }

    public Object getCustomData() {
        return customData;
    }

    public void setCustomData(Object customData) {
        this.customData = customData;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Object getExternalId() {
        return externalId;
    }

    public void setExternalId(Object externalId) {
        this.externalId = externalId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public EnumBookingMakeplanState getState() {
        return state;
    }

    public void setState(EnumBookingMakeplanState state) {
        this.state = state;
    }

    public EnumBookingMakeplanStatus getStatus() {
        return status;
    }

    public void setStatus(EnumBookingMakeplanStatus status) {
        this.status = status;
    }
}
