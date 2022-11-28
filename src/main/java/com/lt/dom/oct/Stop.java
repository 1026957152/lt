package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumPrivacyLevel;
import com.lt.dom.otcenum.EnumStopArrivalStatus;
import com.lt.dom.otcenum.EnumStopStatus;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Stop extends Base{




    private String name;
    private Boolean is_deleted;//Trip was soft deleted and should not be displayed.


    private EnumStopStatus starts;

    private EnumStopArrivalStatus arrivalStatus;



    private LocalDateTime earliestArrivalTime;
    private LocalDateTime latestArrivalTime;
    private Integer plannedDuration;
    private Integer atRiskThreshold;
    private Integer tooEarlyThreshold;




    private String meals;
    private String meals_desc;

    @Transient
    private List<String> themes;
    @Transient
    private List<String> tags;

    @Transient
    private List<String> regions;

    private long supplier;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
    }


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
}
