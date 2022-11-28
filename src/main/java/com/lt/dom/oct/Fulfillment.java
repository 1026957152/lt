package com.lt.dom.oct;

import com.lt.dom.otcenum.*;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Fulfillment extends Base{


    private LocalDate date;
    private LocalTime startingTime;


    @Enumerated(EnumType.STRING)
    @NotNull
    private EnumFulfillmentType type;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EnumFulfillmentStatus status;


    @Transient
    private List<Fulfilled_item> fulfilled_items;

    private String tracking_number;

    public EnumFulfillmentType getType() {
        return type;
    }

    public void setType(EnumFulfillmentType type) {
        this.type = type;
    }

    private Integer QuantityRemainingforDelivery;
    private Integer FulfillmentQuantity;
    private LocalDateTime fulfillmentDate;
    private String carrier;

    private String description;








    private LocalDateTime activityDate;
    private EnumFulfillmentInstructionsType enumFulfillmentInstructionType;


    public LocalDateTime getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDateTime activityDate) {
        this.activityDate = activityDate;
    }

    public void setEnumFulfillmentInstructionType(EnumFulfillmentInstructionsType enumFulfillmentInstructionType) {
        this.enumFulfillmentInstructionType = enumFulfillmentInstructionType;
    }

    public EnumFulfillmentInstructionsType getEnumFulfillmentInstructionType() {
        return enumFulfillmentInstructionType;
    }

    public void setTracking_number(String tracking_number) {
        this.tracking_number = tracking_number;
    }

    public String getTracking_number() {
        return tracking_number;
    }
}
