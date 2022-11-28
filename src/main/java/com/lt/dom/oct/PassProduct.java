package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumCardStatus;
import com.lt.dom.otcenum.EnumCardType;
import com.lt.dom.otcenum.EnumPassDorationUnit;
import com.lt.dom.otcenum.EnumPassShippingStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class PassProduct extends Base{

    LocalDateTime card_soft_expiry;

    private long product;



    private Integer duration ;//digital tickets or PDF tickets


    private EnumPassDorationUnit durationUnit ;//digital tickets or PDF tickets
    private String label;
    private String uuid;
    private Integer activeExpiryDuration;
    private EnumPassDorationUnit activeExpiryDurationUnit;


    public void setProduct(long product) {
        this.product = product;
    }

    public long getProduct() {
        return product;
    }

    public LocalDateTime getCard_soft_expiry() {
        return card_soft_expiry;
    }

    public void setCard_soft_expiry(LocalDateTime card_soft_expiry) {
        this.card_soft_expiry = card_soft_expiry;
    }

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setActiveExpiryDuration(Integer activeExpiryDuration) {
        this.activeExpiryDuration = activeExpiryDuration;
    }

    public Integer getActiveExpiryDuration() {
        return activeExpiryDuration;
    }

    public void setActiveExpiryDurationUnit(EnumPassDorationUnit activeExpiryDurationUnit) {
        this.activeExpiryDurationUnit = activeExpiryDurationUnit;
    }

    public EnumPassDorationUnit getActiveExpiryDurationUnit() {
        return activeExpiryDurationUnit;
    }


// https://www.giftrocket.com/docs
}
