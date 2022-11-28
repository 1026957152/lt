package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumActivityType;
import com.lt.dom.otcenum.EnumAssetQtStatus;
import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumAssetType_dynamic_static;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
public class Activity extends Base{


    private LocalDate date;
    private LocalTime startingTime;


    @NotNull
    private EnumActivityType type;


    private LocalDateTime activityDate;



    public EnumActivityType getType() {
        return type;
    }

    public void setType(EnumActivityType type) {
        this.type = type;
    }

    public LocalDateTime getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDateTime activityDate) {
        this.activityDate = activityDate;
    }
}
