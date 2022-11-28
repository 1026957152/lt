package com.lt.dom.oct;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lt.dom.otcenum.EnumBookingMakeplanState;
import com.lt.dom.otcenum.EnumBookingMakeplanStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.LocalDateTime;


//https://developer.makeplans.com/#slots


@Entity
public class BookingMakeplan extends Base{


    @JsonProperty("booked_from")
    private LocalDateTime bookedFrom;
    @JsonProperty("booked_to")
    private LocalDateTime bookedTo;

    @JsonProperty("custom_data")
    private Object customData;
    @JsonProperty("count")
    private Integer count;
    @Transient
    @JsonProperty("expires_at")
    private Object expiresAt;
    @Transient
    @JsonProperty("external_id")
    private Object externalId;

    @JsonProperty("notes")
    private String notes;

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
    private long supplier;
    private String code;
    private long provider;
    private boolean active;
    private boolean all_day;

    public void setSupplier(long supplier) {
        this.supplier = supplier;
    }

    public long getSupplier() {
        return supplier;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setProvider(long provider) {
        this.provider = provider;
    }

    public long getProvider() {
        return provider;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
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

    public Object getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Object expiresAt) {
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

    public boolean isAll_day() {
        return all_day;
    }

    public void setAll_day(boolean all_day) {
        this.all_day = all_day;
    }
}
