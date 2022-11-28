package com.lt.dom.oct;

import com.lt.dom.otcenum.EnumAssetQtStatus;
import com.lt.dom.otcenum.EnumAssetType;
import com.lt.dom.otcenum.EnumAssetType_dynamic_static;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "usage_")
public class Usage extends Base{



    @NotNull
    @Column(name = "qty_")
    private Long qty;

    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private Long subscription;

    @NotNull
    private Long ratePlan;
    private long componentRight;

    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getSubscription() {
        return subscription;
    }

    public void setSubscription(Long subscription) {
        this.subscription = subscription;
    }

    public Long getRatePlan() {
        return ratePlan;
    }

    public void setRatePlan(Long ratePlan) {
        this.ratePlan = ratePlan;
    }

    public void setComponentRight(long componentRight) {
        this.componentRight = componentRight;
    }

    public long getComponentRight() {
        return componentRight;
    }
}
