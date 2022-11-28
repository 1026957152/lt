package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumActivityType;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Amendments extends Base{


    private long user;



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
