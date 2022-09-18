package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumCardStatus;
import com.lt.dom.otcenum.EnumCardType;
import com.lt.dom.otcenum.EnumPassDorationUnit;
import com.lt.dom.otcenum.EnumPassShippingStatus;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
@Entity
public class Pass {

        @Version
        private Integer version;

        @GeneratedValue(strategy = GenerationType.IDENTITY)

        @Id
        private long id;

    private EnumCardType type ;//digital tickets or PDF tickets

    @NotEmpty
    @Column(updatable = false)
    private String code ;//digital tickets or PDF tickets
    private EnumPassShippingStatus shipping_statis;

    public String getCode() {
        return code;
    }
    private long user ;//digital tickets or PDF tickets

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public void setCode(String code) {
        this.code = code;
    }



    public EnumCardType getType() {
        return type;
    }

    public void setType(EnumCardType type) {
        this.type = type;
    }



    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    private Integer duration ;//digital tickets or PDF tickets

    @NotNull
    private EnumPassDorationUnit durationUnit ;//digital tickets or PDF tickets
    private LocalDateTime maxActivationDate ;//digital tickets or PDF tickets

    private LocalDateTime expiringDate ;//digital tickets or PDF tickets

    public LocalDateTime getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(LocalDateTime expiringDate) {
        this.expiringDate = expiringDate;
    }

    private LocalDateTime emissionDate ;//digital tickets or PDF tickets


    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public EnumPassDorationUnit getDurationUnit() {
        return durationUnit;
    }

    public void setDurationUnit(EnumPassDorationUnit durationUnit) {
        this.durationUnit = durationUnit;
    }

    public LocalDateTime getMaxActivationDate() {
        return maxActivationDate;
    }

    public void setMaxActivationDate(LocalDateTime maxActivationDate) {
        this.maxActivationDate = maxActivationDate;
    }

    private Integer balance ;//digital tickets or PDF tickets
    private Integer remaining ;//digital tickets or PDF tickets



    private boolean active ;//digital tickets or PDF tickets
    private EnumCardStatus status ;//digital tickets or PDF tickets

    private EnumPassShippingStatus shipping_status ;//digital tickets or PDF tickets


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public EnumCardStatus getStatus() {
        return status;
    }

    public void setStatus(EnumCardStatus status) {
        this.status = status;
    }

    private LocalDateTime deliver_date ;//digital tickets or PDF tickets



    @Transient
    List activities;

    public void setShipping_statis(EnumPassShippingStatus shipping_statis) {
        this.shipping_statis = shipping_statis;
    }

    public EnumPassShippingStatus getShipping_statis() {
        return shipping_statis;
    }


    // https://www.giftrocket.com/docs
}
