package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumPrivacyLevel;
import com.lt.dom.otcenum.EnumStopArrivalStatus;
import com.lt.dom.otcenum.EnumStopStatus;
import com.lt.dom.otcenum.EnumStopType;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
//https://developer.trimblemaps.com/restful-apis/trip-management/api-documentation/get-driver-trips/
@Entity
public class StopPlan extends Base{




    private String name;

    private EnumStopType type;


    private Integer stopSequence;


    private LocalDateTime earliestArrivalTime;
    private LocalDateTime latestArrivalTime;
    private Integer plannedDuration;
    private Integer atRiskThreshold;
    private Integer tooEarlyThreshold;

    @Transient
    private Address location;


    private String meals;
    private String meals_desc;
/*

    @Transient
    private List<String> themes;
    @Transient
    private List<String> tags;

    @Transient
    private List<String> regions;
*/

    private long supplier;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
